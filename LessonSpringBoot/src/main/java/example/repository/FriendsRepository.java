package example.repository;

import example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendsRepository extends JpaRepository<User,Long> {
  List<User> findAllById(long userId);

  List<User> findIncomingFriendRequests(long userId);

  List<User> findOutgoingFriendsRequests(long userId);

  boolean createFriendRequest(long sourceUserId, long targetUserId);

  boolean restoreFriendship(long sourceUserId, long targetUserId);

  boolean approveFriendship(long sourceUserId, long targetUserId);

  boolean cancelFriendship(long sourceUserId, long targetUserId);
}

