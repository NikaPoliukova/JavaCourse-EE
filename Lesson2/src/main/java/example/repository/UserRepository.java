package example.repository;


import example.model.User;

import java.util.List;

public interface UserRepository {

    List<User> findUsers();

    boolean nameExistenceCheck(String name);

    boolean checkRegistered(String name, String password);

    void addUser(User user);

    List<User> findUserWithSearch(String name);

    User getUser(String name, String password);
}