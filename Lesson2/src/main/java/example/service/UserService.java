package example.service;


import example.model.User;

import java.util.List;

public interface UserService {

    List<User> findUsers();

    void addUser(User user);

    boolean nameExistenceCheck(String name);

    boolean checkRegistered(String name, String password);

    List<User> findUserWithSearch(String name);

    User getUser(String name, String password);

}
