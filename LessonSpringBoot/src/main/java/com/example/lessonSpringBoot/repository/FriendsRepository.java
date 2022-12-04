package com.example.lessonSpringBoot.repository;

import com.example.lessonSpringBoot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FriendsRepository extends JpaRepository<User, Long> {

  @Query(value = "select * from friends f join users u on (u.user_id = f.target_user_id or u.user_id = f.source_user_id) " +
      "where (f.source_user_id = ?1 or f.target_user_id = ?1) and f.status = 'APPROVED'", nativeQuery = true)
  List<User> getFriendsByUserIdAndStatus(long userId);

  @Query(value = "select * from friends f join users u on u.user_id = source_user_id " +
      "where f.status = 'NEW' AND f.target_user_id = ?1", nativeQuery = true)
  List<User> findIncomingFriendRequests(long userId);//не знаю,как правильно называть метод

  @Query(value = "select * from friends f join users u on u.user_id = target_user_id " +
      "where f.status = 'NEW' and f.source_user_id = ?1", nativeQuery = true)
  List<User> findOutgoingFriendsRequests(long userId);//не знаю,как правильно называть метод

  @Modifying
  @Query(value = "insert into friends (source_user_id, target_user_id) VALUES (?1,?2)", nativeQuery = true)
  void createFriendRequest(long sourceUserId, long targetUserId);


  @Modifying
  @Query(value = "update friends set status = 'NEW', update_at = NOW(), source_user_id = ?1, target_user_id = ?2 " +
      "where ((source_user_id = ?1 or source_user_id = ?2) and (target_user_id = ?1 or target_user_id = ?2))",
      nativeQuery = true)
  Integer restoreFriendship(long sourceUserId, long targetUserId);


  @Modifying
  @Query(value = "update friends set status = 'APPROVED', update_at = NOW() where (source_user_id = ?1 " +
      "and target_user_id = ?2)", nativeQuery = true)
  void approveFriendship(long sourceUserId, long targetUserId);


  @Modifying
  @Query(value = "update friends set status = 'CANCEL', update_at = NOW() where ((source_user_id = ?1 " +
      "or source_user_id = ?2) and (target_user_id = ?1 or target_user_id = ?2))", nativeQuery = true)
  void cancelFriendship(long sourceUserId, long targetUserId);


}

