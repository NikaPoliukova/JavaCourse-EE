package com.example.lessonSpringBoot.repository;

import com.example.lessonSpringBoot.model.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends Repository<User, Long> {

  /*@Query(value = "select * from users", nativeQuery = true)
  List<User> findAllUsers();*/

  @Query("select * from users")
  List<User> findAllUsers();

  @Modifying
  @Query(value = "insert into users (username, password) VALUES (:userName, :password)")
  void addUser(@Param("userName") String userName, @Param("password") String password);

  @Query(value = "select * from users where username like :name")
  List<User> findByUserNameStartingWith(@Param("name") String name);

  @Query(value = "select * from users where username= :name")
  User findUserByUserName(@Param("name") String name);


}
