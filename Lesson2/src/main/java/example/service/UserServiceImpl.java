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
        if (!nameExistenceCheck(user.getUserName())) {
            userRepository.addUser(user);
        } else {
            throw new RuntimeException("This login is busy");
        }
    }

    public boolean checkRegistered(String name, String password) {
        return userRepository.checkRegistered(name, password);
    }

    public boolean nameExistenceCheck(String name) {
        return userRepository.nameExistenceCheck(name);
    }

    public List<User> findUserWithSearch(String name) {
        return userRepository.findUserWithSearch(name);
    }
    public User getUser(String name, String password){
        return userRepository.getUser(name, password);
    }
}
