package ui.tests;

import model.*;
import strategy.*;
import exception.InvalidClothingException;

public class WardrobeTestUI {

    public void runTest() {

        try {

            StylesProfile stylesProfile = new StylesProfile("Casual","Neutral");

            Wardrobe wardrobe = TestDataFactory.createSampleWardrobe();

            User user = new User("U1", "Cedar", stylesProfile, wardrobe);

            // Change this whenever you want to test another clothing style.
            RecommendationStrategy strategy = new FormalStrategy();

            OutfitOptions options = strategy.getOutfitOptions(wardrobe);

            OutfitDisplayUI display = new OutfitDisplayUI(user, options);

            display.show();

        } catch(InvalidClothingException e) {

            System.out.println("Invalid clothing detected: "+ e.getMessage());
        }
    }
}