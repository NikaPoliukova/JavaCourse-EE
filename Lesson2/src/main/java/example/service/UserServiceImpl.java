package example.service;


import example.model.User;
import example.repository.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findUsers() {
        return userRepository.findUsers();
    }

    public void addUser(User user) {
        userRepository.addUser(user);
    }

    public User getUser(String name, String password) {
        return userRepository.getUser(name, password);
    }

    public List<User> findUserWithSearch(String name) {
        return userRepository.findUserWithSearch(name);
    }
}
