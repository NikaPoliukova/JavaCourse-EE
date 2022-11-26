package example.repository;

import example.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Log4j2
public class UserRepositoryImpl implements UserRepository {
  private final Connection connection;
  private static final String SHOW_ALL_USERS = "SELECT user_id , username, password, createdDate FROM users";
  private static final String GET_USER_PASS = "SELECT password FROM users WHERE username = ?";
  private static final String SHOW_USERS_SEARCH = "SELECT * FROM users WHERE username LIKE ?";
  private static final String ADD_USER = "INSERT INTO users (username, password) VALUES (?,?)";
  private static final String NAME_EXISTENCE_CHECK = "SELECT * FROM users WHERE username=?";
  private static final String CHECK_REGISTERED = "SELECT * FROM users WHERE username=? AND password =?";

  public List<User> findUsers() {
    log.info("started looking for all users");
    try (Statement stmt = connection.createStatement()) {
      ResultSet rs = stmt.executeQuery(SHOW_ALL_USERS);
      final List<User> userList = new ArrayList<User>();
      while (rs.next()) {
        final User user = new User(rs.getLong("user_id"),
            rs.getString("username"), rs.getString("password"), rs.getDate("createdDate"));
        userList.add(user);
        log.info("users found");
      }
      return userList;
    } catch (SQLException e) {
      log.error("error during user found " + e);
      throw new RuntimeException(e);
    }
  }

  public void addUser(String userName, String password) {
    log.info("user addition started Username=[{}]" + userName);
    try (PreparedStatement statement = connection.prepareStatement(ADD_USER)) {
      statement.setString(1, userName);
      statement.setString(2, password);
      statement.executeUpdate();
      log.info("User=[{}]" + userName + " added");
    } catch (SQLException e) {
      log.error("error when adding user" + e);
      throw new RuntimeException(e);
    }
  }

  public User getUser(String name, String password) {
    log.info("started getting all the data about the user Username=[{}] " + name);
    User user = null;
    try (PreparedStatement statement = connection.prepareStatement(CHECK_REGISTERED)) {
      statement.setString(1, name);
      statement.setString(2, password);
      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          user = new User(
              rs.getLong("user_id"),
              rs.getString("username"),
              rs.getString("password"),
              rs.getTimestamp("createdDate"));
          log.info("user data Username=[{}] " + user.getUserName() + " received");
        }
      }
    } catch (SQLException se) {
      se.printStackTrace();
    }
    return user;
  }

  @SneakyThrows
  public String getUserPassword(String userName) {
    String password = null;
    PreparedStatement statement = connection.prepareStatement(GET_USER_PASS);
    statement.setString(1, userName);
    ResultSet rs = statement.executeQuery();
    if (rs.next()) {
      password = rs.getString("password");
    }
    return password;
  }

  @SneakyThrows
  public boolean isExistsByName(String name) {
    log.info("name uniqueness check started Username=[{}] " + name);
    try (PreparedStatement statement = connection.prepareStatement(NAME_EXISTENCE_CHECK)) {
      statement.setString(1, name);
      try (ResultSet rs = statement.executeQuery()) {
        return rs.next();
      }
    }
  }

  @SneakyThrows
  public boolean checkRegistered(String name, String password) {
    log.info("user existence check started Username=[{}]" + name);
    try (PreparedStatement statement = connection.prepareStatement(CHECK_REGISTERED)) {
      statement.setString(1, name);
      statement.setString(2, password);
      try (ResultSet rs = statement.executeQuery()) {
        return rs.next();
      }
    }
  }

  public List<User> findUserWithSearch(String name) {
    log.info("search parameter check started");
    final List<User> userList = new ArrayList<>();
    try (PreparedStatement statement = connection.prepareStatement(SHOW_USERS_SEARCH)) {
      statement.setString(1, name + "%");
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        final User user = new User(rs.getString("username"));
        userList.add(user);
      }
      return userList;
    } catch (SQLException e) {
      log.error("when checking search =" + name + "An error has occurred " + e);
      throw new RuntimeException(e);
    }
  }
}




