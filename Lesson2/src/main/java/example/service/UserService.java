package example.service;


import example.model.User;

import java.util.List;

public interface UserService {

  public List<User> findUsers();

  public void addUser(User user);

  public User getUser(String name, String password);

}
