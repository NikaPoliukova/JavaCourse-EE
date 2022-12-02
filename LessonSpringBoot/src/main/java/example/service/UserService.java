package example.service;


import example.model.User;

import java.util.List;

public interface UserService {

  List<User> findUsers();

  void addUser(String userName, String password);

  boolean isExistsByName(String name);

  List<User> findUserWithSearch(String name);

  User getUser(String name, String password);

  String getUserPassword(String userName);
}
