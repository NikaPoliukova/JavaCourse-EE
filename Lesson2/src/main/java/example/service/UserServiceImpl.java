package example.service;


import example.model.User;
import example.repository.UserRepository;
import lombok.SneakyThrows;
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

  @SneakyThrows
  public boolean checkRegistered(String name, String password) throws RuntimeException {
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

  public List<User> findAllFriends(long userId) {
    return userRepository.findAllFriends(userId);
  }

  public List<User> findIncomingFriendRequests(long userId) {
    return userRepository.findIncomingFriendRequests(userId);
  }

  public List<User> findOutgoingFriendsRequests(long userId) {
    return userRepository.findOutgoingFriendsRequests(userId);
  }

  // перед тем, как создать заявку мы проверим, может быть они уже были друзья и досаточно изменить статус
  public boolean createFriendRequest(long sourceUserId, long targetUserId) {
    boolean isUpdateExistingFriendship = userRepository.restoreFriendship(sourceUserId, targetUserId);
    if (!isUpdateExistingFriendship) {
      return userRepository.createFriendRequest(sourceUserId, targetUserId);
    }
    return isUpdateExistingFriendship;
  }

  public boolean approveFriendship(long sourceUserId, long targetUserId) {
    return userRepository.approveFriendship(sourceUserId, targetUserId);
  }

  public boolean cancelFriendship(long sourceUserId, long targetUserId) {
    return userRepository.cancelFriendship(sourceUserId, targetUserId);
  }

  public boolean restoreFriendship(long sourceUserId, long targetUserId) {
    return userRepository.restoreFriendship(sourceUserId, targetUserId);
  }

  public String getUserPassword(String userName) {
    return userRepository.getUserPassword(userName);
  }
}
