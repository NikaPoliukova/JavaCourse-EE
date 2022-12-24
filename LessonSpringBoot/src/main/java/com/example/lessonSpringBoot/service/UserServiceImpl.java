package com.example.lessonSpringBoot.service;


import com.example.lessonSpringBoot.model.User;
import com.example.lessonSpringBoot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl {
 
  private final UserRepository userRepository;
  private final HashPassServiceImpl hashPassService;

  public Page<User> getFilteredUsers(String searchValue, int pageNumber, int pageSize) {
    Pageable page = PageRequest.of(pageNumber, pageSize);
    if (searchValue != null) {
      return userRepository.findUsersByUserNameStartingWith(searchValue, page);
    } else {
      return userRepository.findAllBy(page);
    }
  }

  @Transactional
  public void addUser(String userName, String password) {
    if (userRepository.findUserByUserName(userName) != null) {
      throw new RuntimeException("This user already exists");
    }
    String hashPass = hashPassService.hashPass(password);
    userRepository.addUser(userName, hashPass);
  }

  public User findUserByUserNameAndPassword(String name, String password) {
    User user = userRepository.findUserByUserName(name);
    if (user !=null && hashPassService.verify(password, user.getPassword())) {
      return user;
    } else {
      throw new RuntimeException("enter incorrect password");
    }
  }

  public User findUserByUserName(String userName) {
    return userRepository.findUserByUserName(userName);
  }

  public User findUserByUserId(long targetUserId) {
    return userRepository.findUserByUserId(targetUserId);
  }
}

