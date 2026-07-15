package ui.tests;

import model.*;
import service.RecommendationEngine;
import ui.outfit.OutfitDisplayUI;
import exception.InvalidClothingException;


public class WardrobeTestUI {

    public void runTest() {

        try {

            StylesProfile stylesProfile =
                    new StylesProfile(
                            "Casual",
                            "Neutral"
                    );


            Wardrobe wardrobe =
                    TestDataFactory.createSampleWardrobe();



            User user =
                    new User(
                            "U1",
                            "Cedar",
                            stylesProfile,
                            wardrobe
                    );



            RecommendationEngine engine =
                    new RecommendationEngine();



            OutfitOptions options =
                    engine.getOutfitOptions(
                            user
                    );



            OutfitDisplayUI display =
                    new OutfitDisplayUI(
                            user,
                            options,
                            engine
                    );



            display.show();


        } catch(InvalidClothingException e) {

            System.out.println(
                    "Invalid clothing detected: "
                    + e.getMessage()
            );

        }

    }

}