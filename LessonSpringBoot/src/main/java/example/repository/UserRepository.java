package example.repository;


import example.model.User;

import java.util.List;

public interface UserRepository {

  List<User> findUsers();

  boolean isExistsByName(String name);

  void addUser(String userName, String password);

  List<User> findUserWithSearch(String name);

  User getUser(String name, String password);

  String getUserPassword(String userName);

  boolean checkRegistered(String name, String password);
}
