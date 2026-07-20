package ui.tests;

import model.*;

import service.*;

import ui.outfit.OutfitDisplayUI;

import exception.*;

import java.sql.SQLException;


public class WardrobeTestUI {

    public void runTest() {

        try {

            UserService userService = new UserService();

            User user =
                userService.login(
                "Alucrias",
                "somepassword"
                );


            System.out.println("User: " + user.getName());

            System.out.println("\nLoaded Wardrobe:");

            user.getWardrobe().printWardrobe();

            RecommendationEngine engine = new RecommendationEngine();

            OutfitOptions options = engine.getOutfitOptions(user);

            OutfitDisplayUI display = new OutfitDisplayUI(user, options, engine);

            display.show();

        }

        catch(AuthenticationException | SQLException | InvalidClothingException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }


    public static void main(String[] args) {

        WardrobeTestUI test = new WardrobeTestUI();

        test.runTest();

    }

}