package ui.tests;

import model.*;
import strategy.*;
import exception.InvalidClothingException;

public class WardrobeTestUI {

    public void runTest() {

        try {

            StylesProfile stylesProfile = new StylesProfile("Casual","Neutral");

            Wardrobe wardrobe = TestDataFactory.createSampleWardrobe();

            User user = new User( "U1","Cedar", stylesProfile, wardrobe);

            //Chnage this whenever you want to see other fits in other styles for now.
            RecommendationStrategy strategy = new FormalStrategy();

            Outfit outfit = strategy.recommendOutfit(wardrobe);
            OutfitDisplayUI display = new OutfitDisplayUI(user, outfit);
            display.show();

        } catch(InvalidClothingException e) {

            System.out.println("Invalid clothing detected: "+ e.getMessage());
        }
    }
}