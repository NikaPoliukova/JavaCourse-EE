package example.service;

import example.model.User;
import example.repository.FriendsRepository;

import java.util.List;

public class FriendsServiceImpl implements FriendsService {
  private final FriendsRepository friendsRepository;

  public FriendsServiceImpl(FriendsRepository friendsRepository) {
    this.friendsRepository = friendsRepository;
  }

  public List<User> findAllFriends(long userId) {
    return friendsRepository.findAllFriends(userId);
  }

  public List<User> findIncomingFriendRequests(long userId) {
    return friendsRepository.findIncomingFriendRequests(userId);
  }

  public List<User> findOutgoingFriendsRequests(long userId) {
    return friendsRepository.findOutgoingFriendsRequests(userId);
  }

  // перед тем, как создать заявку мы проверим, может быть они уже были друзья и досаточно изменить статус
  public boolean createFriendRequest(long sourceUserId, long targetUserId) {
    boolean isUpdateExistingFriendship = friendsRepository.restoreFriendship(sourceUserId, targetUserId);
    if (!isUpdateExistingFriendship) {
      return friendsRepository.createFriendRequest(sourceUserId, targetUserId);
    }
    return isUpdateExistingFriendship;
  }

  public boolean approveFriendship(long sourceUserId, long targetUserId) {
    return friendsRepository.approveFriendship(sourceUserId, targetUserId);
  }

  public boolean cancelFriendship(long sourceUserId, long targetUserId) {
    return friendsRepository.cancelFriendship(sourceUserId, targetUserId);
  }

  public boolean restoreFriendship(long sourceUserId, long targetUserId) {
    return friendsRepository.restoreFriendship(sourceUserId, targetUserId);
  }
}
