package example.repository;


import example.model.User;

import java.util.List;

public interface UserRepository {

    List<User> findUsers();

    User getUser(String name, String password);

    void addUser(User user);


}