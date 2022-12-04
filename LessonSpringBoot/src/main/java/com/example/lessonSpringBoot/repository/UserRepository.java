package com.example.lessonSpringBoot.repository;


import com.example.lessonSpringBoot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  //List<User> findUsersBySearch(String name);
  @Query(value = "select password from users where username = ?1", nativeQuery = true)
  String findPasswordByUserName(String userName);

  User findUserByUserName(String name);

}
