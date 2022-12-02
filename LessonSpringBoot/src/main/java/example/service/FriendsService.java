package example.service;

import example.model.User;

import java.util.List;

public interface FriendsService {
  List<User> findAllFriends(long userId);

  List<User> findIncomingFriendRequests(long userId);

  List<User> findOutgoingFriendsRequests(long userId);

  boolean createFriendRequest(long sourceUserId, long targetUserId);

  boolean restoreFriendship(long sourceUserId, long targetUserId);

  boolean approveFriendship(long sourceUserId, long targetUserId);

  boolean cancelFriendship(long sourceUserId, long targetUserId);
}
