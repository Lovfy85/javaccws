package service;

import java.sql.SQLException;
import java.util.UUID;

import exception.*;

import model.*;
import model.clothing.ClothingItem;

import repository.UserRepository;
import repository.WardrobeRepository;
import repository.OutfitRepository;

public class UserService {

    private UserRepository userRepository;
    private WardrobeRepository wardrobeRepository;
    private OutfitRepository outfitRepository;


    public UserService() {

        userRepository = new UserRepository();
        wardrobeRepository = new WardrobeRepository();
        outfitRepository = new OutfitRepository();

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



    public User login(String username, String password) throws SQLException, AuthenticationException, InvalidClothingException {

        User user = userRepository.findByUsername(username);


        if(user == null) {
            throw new AuthenticationException("Invalid username or password.");
        }


        if(!user.getPasswordHash().equals(password)) {
            throw new AuthenticationException("Invalid username or password.");
        }



        Wardrobe wardrobe = new Wardrobe();


        for(ClothingItem item : wardrobeRepository.findByUserId(user.getId())) {

            wardrobe.addItem(item);

        }


        user.setWardrobe(wardrobe);



        for(Outfit outfit : outfitRepository.findByUserId(user.getId())) {

            user.addSavedOutfit(outfit);

        }


        return user;
    }



    public boolean usernameExists(String username) throws SQLException {

        return userRepository.existsByUsername(username);

    }

}