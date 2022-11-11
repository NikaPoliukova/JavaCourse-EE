package example.repository;


import example.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {

  List<User> findUsers();

  boolean isExistsByName(String name) throws SQLException;

  boolean checkRegistered(String name, String password) throws SQLException;

  void addUser(User user);

  List<User> findUserWithSearch(String name);

  User getUser(String name, String password);

  //friends
  List<User> findAllFriends(long userId);

  List<User> findIncomingFriendRequests(long userId);

  List<User> findOutgoingFriendsRequests(long userId);

  boolean createFriendRequest(long sourceUserId, long targetUserId);

  boolean restoreFriendship(long sourceUserId, long targetUserId);

  boolean approveFriendship(long sourceUserId, long targetUserId);

  boolean cancelFriendship(long sourceUserId, long targetUserId);

  public String getUserPassword(String userName);


}
