package com.example.lessonSpringBoot.service;

import com.example.lessonSpringBoot.model.User;
import com.example.lessonSpringBoot.repository.FriendsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendsServiceImpl {

  private final FriendsRepository friendsRepository;

  public List<User> getFriendsByUserIdAndStatus(long userId) {
    return friendsRepository.getFriendsByUserIdAndStatus(userId);
  }

  public List<User> getFriendsAndFriendRequests(long userId) {
    return friendsRepository.getFriendsAndFriendRequests(userId);
  }

  public List<User> findIncomingFriendRequests(long userId) {
    return friendsRepository.findIncomingFriendRequests(userId);
  }

  public List<User> findOutgoingFriendsRequests(long userId) {
    return friendsRepository.findOutgoingFriendsRequests(userId);
  }

  @Transactional
  // перед тем, как создать заявку мы проверим, может быть они уже были друзья и досаточно изменить статус
  public void createFriendRequest(long sourceUserId, long targetUserId) {
    if (friendsRepository.restoreFriendship(sourceUserId, targetUserId) == 0) {
      friendsRepository.createFriendRequest(sourceUserId, targetUserId);
    }
  }

  @Transactional
  public void approveFriendship(long sourceUserId, long targetUserId) {
    friendsRepository.approveFriendship(sourceUserId, targetUserId);
  }

  @Transactional
  public void cancelFriendship(long sourceUserId, long targetUserId) {
    friendsRepository.cancelFriendship(sourceUserId, targetUserId);
  }
}
