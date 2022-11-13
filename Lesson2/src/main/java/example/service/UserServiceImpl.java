package example.service;


import example.model.User;
import example.repository.UserRepository;
import lombok.extern.log4j.Log4j2;

import java.sql.SQLException;
import java.util.List;

@Log4j2
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> findUsers() {
    return userRepository.findUsers();
  }

  public void addUser(User user) throws SQLException {
    if (!isExistsByName(user.getUserName())) {
      userRepository.addUser(user);
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
    return userRepository.getUser(name, password);
  }

  public String getUserPassword(String userName) {
    return userRepository.getUserPassword(userName);
  }
}
