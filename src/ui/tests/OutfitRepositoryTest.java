package ui.tests;


import model.*;

import repository.OutfitRepository;

import service.RecommendationEngine;
import service.UserService;


public class OutfitRepositoryTest {


    public static void main(String[] args) {

        OutfitRepository outfitRepository = new OutfitRepository();

        UserService userService = new UserService();

        RecommendationEngine engine = new RecommendationEngine();

        try {

            System.out.println(  "=== OUTFIT REPOSITORY TEST ===");

            User user = userService.login( "Alucrias", "somepassword");

            if(user == null) {

                System.out.println("User not found.");
                return;

            }

            System.out.println("Testing user: " + user.getUsername());

            System.out.println("User ID: " + user.getId());

            System.out.println("\nLoaded Wardrobe:");

            System.out.println(user.getWardrobe());

            Outfit outfit = engine.recommendOutfit(user);

            int score = engine.scoreOutfit(outfit, user);

            System.out.println("\nGenerated Outfit:");

            outfit.display();

            System.out.println("Score: " + score);

            System.out.println("\nSaving outfit...");

            outfitRepository.save(
                outfit.getId(),
                user.getId(),
                outfit
            );

            System.out.println("Outfit saved successfully!");

            System.out.println("\nLoading saved outfit...");

            Outfit loadedOutfit =
                outfitRepository.findById(
                        outfit.getId()
                );



            if(loadedOutfit != null) {


                System.out.println(
                        "Outfit found!"
                );


                loadedOutfit.display();

            }

            else {


                System.out.println(
                        "Outfit not found."
                );

            }

            System.out.println(
                    "\n=== TEST COMPLETE ==="
            );



        }
        catch(Exception e) {

            e.printStackTrace();

        }

    }

}