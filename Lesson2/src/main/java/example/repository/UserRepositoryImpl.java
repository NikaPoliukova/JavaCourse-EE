package example.repository;

import example.model.User;
import lombok.extern.log4j.Log4j2;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class UserRepositoryImpl implements UserRepository {
  private final Connection connection;

  private static final String SHOW_ALL_USERS = "SELECT user_id , username, password, createdDate FROM users";
  private static final String SHOW_USERS_SEARCH = "SELECT * FROM users WHERE username LIKE ?";
  private static final String ADD_USER = "INSERT INTO users (username, password) VALUES (?,?)";
  private static final String NAME_EXISTENCE_CHECK = "SELECT * FROM users WHERE username=?";
  private static final String CHECK_REGISTERED = "SELECT * FROM users WHERE username=? AND password =?";

  private static final String ADD_FRIEND = "INSERT INTO friends (user_id, friend_id) VALUES (?,?)";
  private static final String ADD_FRIEND_IN_REQUEST = "INSERT INTO invitations (user_id, friend_id) VALUES (?,?)";
  private static final String DELETE_FRIEND = "INSERT INTO friends (friend_id) VALUES (?)";
  private static final String DELETE_FRIEND_IN_REQUEST = "INSERT INTO invitations (friend_id) VALUES (?)";
  private static final String SHOW_ALL_FRIENDS = "SELECT friend_id FROM friends WHERE user_id LIKE ?";
  //private static final String SHOW_OUTGOING_REQUEST = "SELECT * FROM invitations";

  public UserRepositoryImpl(Connection connection) {
    this.connection = connection;
  }

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

  public void addUser(User user) {
    log.info("user addition started Username=[{}]" + user.getUserName());
    try (PreparedStatement statement = connection.prepareStatement(ADD_USER)) {
      statement.setString(1, user.getUserName());
      statement.setString(2, user.getPassword());
      statement.executeUpdate();
      log.info("User=[{}]" + user.getUserName() + " added");
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
              rs.getLong("userId"),
              rs.getString("username"),
              rs.getString("password"),
              rs.getTimestamp("createdDate"));
          log.info("user data Username=[{}] " + user.getUserName() + " received");
        }
      }
    } catch (SQLException se) {
      log.info("Username=[{}] " + user.getUserName() + " not found, error " + se);
      se.printStackTrace();
    }
    return user;
  }

  public boolean isExistsByName(String name) throws SQLException {
    log.info("name uniqueness check started Username=[{}] " + name);
    try (PreparedStatement statement = connection.prepareStatement(NAME_EXISTENCE_CHECK)) {
      statement.setString(1, name);
      try (ResultSet rs = statement.executeQuery()) {
        return rs.next();
      }
    }
  }

  public boolean checkRegistered(String name, String password) throws SQLException {
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

  //метод добавления друга исходящяя заявка
  public void addFriend(User user1, User user2) {
    try (PreparedStatement statement = connection.prepareStatement(ADD_FRIEND)) {
      statement.setLong(1, user1.getUserId());
      statement.setLong(2, user2.getUserId());
      statement.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  //метод удаления друга
  public void deleteFriend(User user) {
    try (PreparedStatement statement = connection.prepareStatement(DELETE_FRIEND)) {
      //нажата кнопка удалить
      statement.setLong(1, user.getUserId());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  //метод добавление в таблицу заявок
  public void addFriendInRequest(User user1, User user2) {
    try (PreparedStatement statement = connection.prepareStatement(ADD_FRIEND_IN_REQUEST)) {
      statement.setLong(1, user1.getUserId());
      statement.setLong(2, user2.getUserId());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  //метод удаления в таблице заявок
  public void deleteFriendInRequest(User user) {
    try (PreparedStatement statement = connection.prepareStatement(DELETE_FRIEND_IN_REQUEST)) {
      statement.setLong(1, user.getUserId());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  //показать всех друзей
  public List<User> findAllFriends(Long userId) {
    try (PreparedStatement statement = connection.prepareStatement(SHOW_ALL_FRIENDS)) {
      final List<User> friendsList = new ArrayList<>();
      statement.setLong(1, userId);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        final User user = new User(rs.getString("username"));
        friendsList.add(user);
      }
      return friendsList;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  //показать исходящие заявки

  /*public List<User> showOutgoingRequests() {
    try (Statement stmt = connection.createStatement()) {
      ResultSet rs = stmt.executeQuery(SHOW_OUTGOING_REQUEST);
      final List<User> friendsList = new ArrayList<User>();
      while (rs.next()) {
        final User user = new User(rs.getLong("friend_id"), rs.getDate("createdDate"));
        friendsList.add(user);
        log.info("users found");
      }
      return friendsList;
    } catch (SQLException e) {
      log.error("error during user found " + e);
      throw new RuntimeException(e);
    }*/
}


