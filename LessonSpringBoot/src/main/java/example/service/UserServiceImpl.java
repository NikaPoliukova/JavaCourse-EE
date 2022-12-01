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
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public List<User> findUsers() {
    return userRepository.findUsers();
  }

  public void addUser(String userName, String password) {
    if (!isExistsByName(userName)) {
      userRepository.addUser(userName, password);
    } else {
      log.info("Пользователь существует");
      throw new RuntimeException("This login already exists");
    }
  }

  public boolean isExistsByName(String name) {
    return userRepository.isExistsByName(name);
  }

  public List<User> findUserWithSearch(String name) {
    return userRepository.findUserWithSearch(name);
  }

  public User getUser(String name, String password) {
    User user = userRepository.getUser(name, password);
    return user;
  }

  public String getUserPassword(String userName) {
    return userRepository.getUserPassword(userName);
  }
}
