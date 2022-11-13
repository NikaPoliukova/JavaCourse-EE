package example.service;


import example.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

  List<User> findUsers();

  void addUser(User user) throws SQLException;

  boolean isExistsByName(String name) throws SQLException;

  List<User> findUserWithSearch(String name);

  User getUser(String name, String password);

  String getUserPassword(String userName);

}
