package example.service;


import example.model.User;
import example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl {

  private final UserRepository userRepository;

  public List<User> findUsers() {
    return userRepository.findAll();
  }

  public void addUser(String userName, String password) {
    if (userRepository.findUserByName(userName) != null || !(userRepository.findUserByName(userName).isEmpty())) {
      log.info("Пользователь существует");
      throw new RuntimeException("This user already exists");
    }
    final User user = new User(userName, password);
    userRepository.save(user);
  }
  public List<User> findUserWithSearch(String name) {
    return userRepository.findUserWithSearch(name);
  }

  public User findUserByNameAndPassword(String name, String password) {
    User user = userRepository.findUserByNameAndPassword(name, password);
    return user;
  }

  public String findUserByName(String userName) {
    return userRepository.findUserByName(userName);
  }
}
