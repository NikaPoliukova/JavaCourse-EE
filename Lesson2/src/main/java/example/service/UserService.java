package example.service;


import example.model.User;

import java.util.List;

public interface UserService {

    List<User> findUsers();

    void addUser(User user);

    User getUser(String name, String password);

    List<User> findUserWithSearch(String name);

}
