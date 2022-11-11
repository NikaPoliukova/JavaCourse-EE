package example.service;


import example.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

  List<User> findUsers();

  void addUser(User user) throws SQLException;

  boolean isExistsByName(String name) throws SQLException;

  boolean checkRegistered(String name, String password) throws SQLException;

  List<User> findUserWithSearch(String name);

  User getUser(String name, String password);

  List<User> findAllFriends(long userId);

  List<User> findIncomingFriendRequests(long userId);

  List<User> findOutgoingFriendsRequests(long userId);

  boolean createFriendRequest(long sourceUserId, long targetUserId);

  boolean approveFriendship(long sourceUserId, long targetUserId);

  boolean cancelFriendship(long sourceUserId, long targetUserId);

  boolean restoreFriendship(long sourceUserId, long targetUserId);

  String getUserPassword(String userName);

}
