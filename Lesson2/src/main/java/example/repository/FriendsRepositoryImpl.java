package example.repository;

import example.model.User;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class FriendsRepositoryImpl implements FriendsRepository {
  private final Connection connection;

  private static final String SHOW_ALL_FRIENDS = "SELECT * FROM friends JOIN users on " +
      "(users.user_id = friends.target_user_id OR users.user_id = friends.source_user_id) WHERE " +
      "((friends.source_user_id = ? OR friends.target_user_id = ?) AND status = 'APPROVED')";
  private static final String FIND_INCOMING_FRIEND_REQUESTS = "SELECT * FROM friends JOIN users " +
      "on users.user_id = source_user_id WHERE target_user_id = ? AND status = 'NEW'";
  private static final String FIND_OUTGOING_FRIEND_REQUESTS = "SELECT * FROM friends JOIN users on " +
      "users.user_id = target_user_id WHERE source_user_id = ? AND status = 'NEW'";
  private static final String CREATE_FRIEND_REQUEST = "INSERT INTO friends (source_user_id, target_user_id) VALUES (?,?)";
  private static final String RESTORE_FRIEND_REQUEST = "UPDATE friends SET status = 'NEW', update_at = NOW(), " +
      "source_user_id = ?, target_user_id = ? WHERE ((source_user_id = ? OR source_user_id = ?) AND " +
      "(target_user_id = ? OR target_user_id = ?))";
  private static final String APPROVE_FRIENDSHIP = "UPDATE friends SET status = 'APPROVED', update_at = NOW()" +
      " WHERE (source_user_id = ? AND target_user_id = ?)";
  private static final String CANCEL_FRIENDSHIP = "UPDATE friends SET status = 'CANCEL', update_at = NOW() " +
      "WHERE ((source_user_id = ? OR source_user_id = ?) AND (target_user_id = ? OR target_user_id = ?))";

  public FriendsRepositoryImpl(Connection connection) {
    this.connection = connection;
  }

  public List<User> findAllFriends(long userId) {
    try (PreparedStatement statement = connection.prepareStatement(SHOW_ALL_FRIENDS)) {
      final List<User> friends = new ArrayList<>();
      statement.setLong(1, userId);
      statement.setLong(2, userId);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        if (rs.getLong("user_id") != userId) {
          User user = new User(rs.getLong("user_id"), rs.getString("username"));
          friends.add(user);
        }
      }
      return friends;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<User> findIncomingFriendRequests(long userId) {
    try (PreparedStatement statement = connection.prepareStatement(FIND_INCOMING_FRIEND_REQUESTS)) {
      final List<User> potentialFriends = new ArrayList<>();
      statement.setLong(1, userId);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        User user = new User(rs.getLong("user_id"), rs.getString("username"));
        potentialFriends.add(user);
      }
      return potentialFriends;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<User> findOutgoingFriendsRequests(long userId) {
    try (PreparedStatement statement = connection.prepareStatement(FIND_OUTGOING_FRIEND_REQUESTS)) {
      final List<User> potentialFriends = new ArrayList<>();
      statement.setLong(1, userId);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        User user = new User(rs.getLong("user_id"), rs.getString("username"));
        potentialFriends.add(user);
      }
      return potentialFriends;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean createFriendRequest(long sourceUserId, long targetUserId) {
    try (PreparedStatement statement = connection.prepareStatement(CREATE_FRIEND_REQUEST)) {
      statement.setLong(1, sourceUserId);
      statement.setLong(2, targetUserId);
      int updates = statement.executeUpdate(); // метод возвращает количество измененных строк,
      // если > 0 , значит были изменения
      return updates > 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  // изменяет статус дружбы на new (если, например, вы удалили друг друга и друзей и пытаетесь заново подружиться)
  public boolean restoreFriendship(long sourceUserId, long targetUserId) {
    try (PreparedStatement statement = connection.prepareStatement(RESTORE_FRIEND_REQUEST)) {
      statement.setLong(1, sourceUserId);
      statement.setLong(2, targetUserId);
      statement.setLong(3, sourceUserId);
      statement.setLong(4, targetUserId);
      statement.setLong(5, sourceUserId);
      statement.setLong(6, targetUserId);
      int updates = statement.executeUpdate(); // метод возвращает количество измененных строк,
      // если > 0 , значит были изменения
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
