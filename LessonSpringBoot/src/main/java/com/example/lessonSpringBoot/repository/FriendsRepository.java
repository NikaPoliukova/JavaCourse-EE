package com.example.lessonSpringBoot.repository;

import com.example.lessonSpringBoot.model.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FriendsRepository extends Repository<User, Long> {

  @Query(value = "select * from friends f join users u on (u.user_id = f.target_user_id or u.user_id = f.source_user_id) " +
      "where (f.source_user_id = :userId or f.target_user_id = :userId) and f.status = 'APPROVED'")
  List<User> getFriendsByUserIdAndStatus(@Param("userId") long userId);

  @Query(value = "select * from friends f join users u on u.user_id = source_user_id where f.status = 'NEW' and f.target_user_id = :userId")
  List<User> findIncomingFriendRequests(@Param("userId") long userId);

  @Query(value = "select * from friends f join users u on u.user_id = target_user_id " +
      "where f.status = 'NEW' and f.source_user_id = :userId")
  List<User> findOutgoingFriendsRequests(@Param("userId") long userId);

  @Modifying
  @Query(value = "insert into friends (source_user_id, target_user_id) VALUES (:sourceUserId, :targetUserId)")
  void createFriendRequest(@Param("sourceUserId") long sourceUserId, @Param("targetUserId") long targetUserId);

  @Modifying
  @Query(value = "update friends set status = 'NEW', update_at = NOW(), source_user_id = ?1, target_user_id = ?2 " +
      "where ((source_user_id = :sourceUserId or source_user_id = :targetUserId)" +
      " and (target_user_id = :sourceUserId or target_user_id = :targetUserId))")
  Integer restoreFriendship(@Param("sourceUserId") long sourceUserId, @Param("targetUserId") long targetUserId);

  @Modifying
  @Query(value = "update friends set status = 'APPROVED', update_at = NOW() where (source_user_id = :sourceUserId " +
      "and target_user_id = :targetUserId)")
  void approveFriendship(@Param("sourceUserId") long sourceUserId, @Param("targetUserId") long targetUserId);

  @Modifying
  @Query(value = "update friends set status = 'CANCEL', update_at = NOW() where ((source_user_id = :sourceUserId " +
      "or source_user_id = :targetUserId) " +
      "and (target_user_id = :sourceUserId or target_user_id = :targetUserId))")
  void cancelFriendship(@Param("sourceUserId") long sourceUserId, @Param("targetUserId") long targetUserId);
}

