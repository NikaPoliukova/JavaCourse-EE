package com.example.lessonSpringBoot.repository;

import com.example.lessonSpringBoot.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends Repository<User, Long> {

  Page<User> findAllBy(Pageable page);

  @Modifying
  @Query(value = "insert into users (username, password) VALUES (:userName, :password)")
  void addUser(@Param("userName") String userName, @Param("password") String password);

  Page<User> findUsersByUserNameStartingWith(String searchValue, Pageable page);

  User findUserByUserName(@Param("userName") String userName);

  User findUserByUserId(long targetUserId);

  @Modifying
  @Query( "update users set username =:userName where user_id =:userId ")
  User updateUserName(@Param("userName") String userName,@Param("userId") long userId);
}

