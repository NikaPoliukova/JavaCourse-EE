package example.service;


import example.model.User;
import example.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;

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
      throw new RuntimeException("This login already exists");
    }
  }

  public boolean checkRegistered(String name, String password) throws SQLException {
    return userRepository.checkRegistered(name, password);
  }

  public boolean isExistsByName(String name) throws SQLException {
    return userRepository.isExistsByName(name);
  }

  public List<User> findUserWithSearch(String name) {
    return userRepository.findUserWithSearch(name);
  }

  public User getUser(String name, String password) {
    return userRepository.getUser(name, password);
  }
}
