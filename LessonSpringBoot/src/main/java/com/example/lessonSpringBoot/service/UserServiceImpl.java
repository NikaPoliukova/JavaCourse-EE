package com.example.lessonSpringBoot.service;


import com.example.lessonSpringBoot.model.User;
import com.example.lessonSpringBoot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl {

  private final UserRepository userRepository;
  private final HashPassServiceImpl hashPassService;

  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  @Transactional
  public void addUser(String userName, String password) {
    if (userRepository.findUserByUserName(userName) != null) {
      throw new RuntimeException("This user already exists");
    }
    String hashPass = hashPassService.hashPass(password);
    User user = new User(userName, hashPass);
    userRepository.save(user);
  }

  /* public List<User> findUsersBySearch(String name) {
     return userRepository.findUsersBySearch(name);
   }
  */
  public User findUserByUserNameAndPassword(String name, String password) {
    User user = userRepository.findUserByUserName(name);
      if (hashPassService.verify(password, user.getPassword())) {
      return user;
    }return null;
  }

  public String findPasswordByUserName(String userName) {
    return userRepository.findPasswordByUserName(userName);
  }
}

