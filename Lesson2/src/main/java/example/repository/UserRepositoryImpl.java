package example.repository;

import example.model.User;
import lombok.SneakyThrows;
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
  private static final String GET_USER_PASS = "SELECT password FROM users WHERE username = ?";
  private static final String SHOW_USERS_SEARCH = "SELECT * FROM users WHERE username LIKE ?";
  private static final String ADD_USER = "INSERT INTO users (username, password) VALUES (?,?)";
  private static final String NAME_EXISTENCE_CHECK = "SELECT * FROM users WHERE username=?";
  private static final String CHECK_REGISTERED = "SELECT * FROM users WHERE username=? AND password =?";
  //"(SELECT source_user_id AS friend_id FROM friends " +
//      "WHERE target_user_id = ?) UNION (SELECT target_user_id AS friend_id FROM friends WHERE source_user_id = ?)" +
//      "JOIN users on users.user_id = friend_id"; ТУТ НУЖНО ВСТАВИТЬ В SHOW_ALL_FRIENDS
  private static final String SHOW_ALL_FRIENDS = "SELECT * FROM friends JOIN users on users.user_id = friends.source_user_id " +
      "WHERE target_user_id = ? AND status = 'ok'";
  private static final String FIND_INCOMING_FRIEND_REQUESTS = "SELECT * FROM friends JOIN users " +
      "on users.user_id = source_user_id WHERE target_user_id = ? AND status = 'new'";
  private static final String FIND_OUTGOING_FRIEND_REQUESTS = "SELECT * FROM friends JOIN users on " +
      "users.user_id = target_user_id WHERE source_user_id = ? AND status = 'new'";
  private static final String CREATE_FRIEND_REQUEST = "INSERT INTO friends (source_user_id, target_user_id) VALUES (?,?)";

  private static final String RESTORE_FRIEND_REQUEST = "UPDATE friends SET status = 'new', update_at = NOW(), " +
      "source_user_id = ?, target_user_id = ? WHERE ((source_user_id = ? OR source_user_id = ?) AND " +
      "(target_user_id = ? OR target_user_id = ?))";
  private static final String APPROVE_FRIENDSHIP = "UPDATE friends SET status = 'ok', update_at = NOW()" +
      " WHERE (source_user_id = ? AND target_user_id = ?)";
  private static final String CANCEL_FRIENDSHIP = "UPDATE friends SET status = 'cancel', update_at = NOW() " +
      "WHERE ((source_user_id = ? OR source_user_id = ?) AND (target_user_id = ? OR target_user_id = ?))";

  /*private static final String CHECK_FRIENDS_SHIP = "SELECT source_user_id, target_user_id, status FROM friends" +
      " WHERE ((source_user_id = ? AND target_user_id = ?) OR (target_user_id = ? AND source_user_id = ?) AND status = 'ok')";*/


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
              rs.getLong("user_id"),
              rs.getString("username"),
              rs.getString("password"),
              rs.getTimestamp("createdDate"));
          log.info("user data Username=[{}] " + user.getUserName() + " received");
        }
      }
    } catch (SQLException se) {
      // log.info("Username=[{}] " + user.getUserName() + " not found, error " + se);
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

  //показать друзей
  public List<User> findAllFriends(long userId) {
    try (PreparedStatement statement = connection.prepareStatement(SHOW_ALL_FRIENDS)) {
      final List<User> friends = new ArrayList<>();
      statement.setLong(1, userId);
      // statement.setLong(2, userId);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        User user = new User();
        user.setUserId(rs.getLong("user_id"));
        user.setUserName(rs.getString("username"));
        friends.add(user);
      }
      return friends;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  // найти входящие заявки в друзья
  public List<User> findIncomingFriendRequests(long userId) {
    try (PreparedStatement statement = connection.prepareStatement(FIND_INCOMING_FRIEND_REQUESTS)) {
      final List<User> potentialFriends = new ArrayList<>();
      statement.setLong(1, userId);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        User user = new User();
        user.setUserId(rs.getLong("user_id"));
        user.setUserName(rs.getString("username"));
        potentialFriends.add(user);
      }
      return potentialFriends;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  // найти исходящие заявки в друзья
  public List<User> findOutgoingFriendsRequests(long userId) {
    try (PreparedStatement statement = connection.prepareStatement(FIND_OUTGOING_FRIEND_REQUESTS)) {
      final List<User> potentialFriends = new ArrayList<>();
      statement.setLong(1, userId);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        User user = new User();
        user.setUserId(rs.getLong("user_id"));
        user.setUserName(rs.getString("username"));
        potentialFriends.add(user);
      }
      return potentialFriends;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  //метод создание заявки в друзья
  public boolean createFriendRequest(long sourceUserId, long targetUserId) {
    try (PreparedStatement statement = connection.prepareStatement(CREATE_FRIEND_REQUEST)) {
      statement.setLong(1, sourceUserId);
      statement.setLong(2, targetUserId);
      int updates = statement.executeUpdate(); // метод возвращает количество измененных строк, если > 0 , значит были изменения
      return updates > 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  //проверка наличия дружбы
  /*public boolean checkFriendsShip(long sourceUserId, long targetUserId, String status) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(CHECK_FRIENDS_SHIP)) {
      statement.setLong(1, sourceUserId);
      statement.setLong(2, targetUserId);
      statement.setLong(3, sourceUserId);
      statement.setLong(4, targetUserId);
      statement.setString(5, status);
      ResultSet rs = statement.executeQuery();
      return rs.next();//тут проверяет если есть такие записи со статусом ок,значит true
      //если false - обращение к методу добавления
    }
  }*/

  // изменяет статус дружбы на new (если, например, вы удалили друг друга и друзей и пытаетесь заново подружиться)
  public boolean restoreFriendship(long sourceUserId, long targetUserId) {
    try (PreparedStatement statement = connection.prepareStatement(RESTORE_FRIEND_REQUEST)) {
      statement.setLong(1, sourceUserId);
      statement.setLong(2, targetUserId);
      statement.setLong(3, sourceUserId);
      statement.setLong(4, targetUserId);
      statement.setLong(5, sourceUserId);
      statement.setLong(6, targetUserId);
      int updates = statement.executeUpdate(); // метод возвращает количество измененных строк, если > 0 , значит были изменения
      return updates > 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  // подтверждение дружбы(добавление в друзья)
  public boolean approveFriendship(long sourceUserId, long targetUserId) {
    try (PreparedStatement statement = connection.prepareStatement(APPROVE_FRIENDSHIP)) {
      statement.setLong(1, sourceUserId);
      statement.setLong(2, targetUserId);
      int updates = statement.executeUpdate(); // метод возвращает количество измененных строк, если > 0 , значит были изменения
      return updates > 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  // изменения статуса "дружбы". применяется при отмене запроса, отклонении предложения о дружке, удалении из друзей
  public boolean cancelFriendship(long sourceUserId, long targetUserId) {
    try (PreparedStatement statement = connection.prepareStatement(CANCEL_FRIENDSHIP)) {
      statement.setLong(1, sourceUserId);
      statement.setLong(2, targetUserId);
      statement.setLong(3, sourceUserId);
      statement.setLong(4, targetUserId);
      int updates = statement.executeUpdate(); // метод возвращает количество измененных строк,
      // если > 0 , значит были изменения
      return updates > 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}




