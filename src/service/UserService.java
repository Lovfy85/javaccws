package service;

import java.sql.SQLException;
import java.util.UUID;

import exception.AuthenticationException;
import exception.UserAlreadyExistsException;
import model.*;
import repository.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }


    public User register(String username, String password, String name, String preferredStyle, String colorPreference) throws SQLException, UserAlreadyExistsException, AuthenticationException {

        if (username == null || username.isBlank()) {
            throw new AuthenticationException("Username cannot be empty.");
        }

        if (password == null || password.isBlank()) {
            throw new AuthenticationException("Password cannot be empty.");
        }

        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("Username already exists.");
        }

        StylesProfile profile = new StylesProfile(preferredStyle, colorPreference);

        User user = new User(
            UUID.randomUUID().toString(),
            username,
            password,       
            name,
            profile,
            new Wardrobe()
        );

        userRepository.save(user);

        return user;
    }


    public User login(String username, String password) throws SQLException, AuthenticationException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new AuthenticationException("Invalid username or password.");
        }

        if (!user.getPasswordHash().equals(password)) {
            throw new AuthenticationException("Invalid username or password.");
        }

        return user;
    }


    public boolean usernameExists(String username) throws SQLException {
        return userRepository.existsByUsername(username);
    }
}