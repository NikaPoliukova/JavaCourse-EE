package example.service;

import example.model.User;
import example.repository.FriendsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendsServiceImpl  {
  private final FriendsRepository friendsRepository;

  public List<User> findAllById(long userId) {
    return friendsRepository.findAllById(userId);
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
