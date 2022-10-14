package example.repository;

import example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserRepositoryImpl implements UserRepository {
    private final Connection connection;

    private static final String SHOW_All_USERS = "SELECT username,password FROM users";
    private static final String SHOW_USERS_SEARCH = "SELECT * FROM users WHERE username LIKE ?";
    private final static String ADD_USER = "INSERT INTO users (username, password) VALUES (?,?)";
    private static final String FIND_USER = "SELECT * FROM users WHERE username=? AND password=?";

    public UserRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    public List<User> findUsers() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SHOW_All_USERS);
            final List<User> userList = new ArrayList<User>();
            while (rs.next()) {
                final User user = new User(
                        rs.getString("username"),
                        rs.getString("password"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(ADD_USER);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(String name, String password) {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(FIND_USER)) {
            statement.setString(1, name);
            statement.setString(2, password);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getString("username"),
                            rs.getString("password"));
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return user;
    }

    public List<User> findUserWithSearch(String name) {
        final List<User> userList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SHOW_USERS_SEARCH);
            statement.setString(1, name + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                final User user = new User(rs.getString("username"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


