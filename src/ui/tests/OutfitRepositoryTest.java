package ui.tests;


import model.*;

import model.clothing.*;

import repository.OutfitRepository;

public class OutfitRepositoryTest {

    public static void main(String[] args) {

        OutfitRepository repository =
                new OutfitRepository();


        try {


            System.out.println(
                    "=== OUTFIT REPOSITORY TEST ==="
            );


            String outfitId =
                    "OUTFIT001";


            String userId =
                    "USER002";



            Top top =
                    new Top(
                            "TOP001",
                            userId,
                            "Casual T-Shirt",
                            "Black",
                            "Nike",
                            "resources/images/tops/casual_tshirt.jpg",
                            ClothingStyle.CASUAL,
                            "SHORT_SLEEVE"
                    );



            Bottom bottom =
                    new Bottom(
                            "BOTTOM001",
                            userId,
                            "Casual Jeans",
                            "Blue",
                            "Levis",
                            "resources/images/bottoms/casual_blue_jeans.jpg",
                            ClothingStyle.CASUAL,
                            "SLIM"
                    );



            Footwear footwear =
                    new Footwear(
                            "SHOE001",
                            userId,
                            "Casual Sneakers",
                            "White",
                            "Adidas",
                            "resources/images/footwear/casual_white_sneakers.jpg",
                            ClothingStyle.CASUAL,
                            "SNEAKER"
                    );



            Outfit outfit =
                    new Outfit(
                            outfitId,
                            userId,
                            top,
                            bottom,
                            footwear
                    );


            outfit.setScore(85);



            System.out.println(
                    "\nChecking if outfit already exists..."
            );


            Outfit existingOutfit =
                    repository.findById(outfitId);



            if(existingOutfit == null) {


                System.out.println(
                        "\nSaving outfit..."
                );


                repository.save(
                        outfitId,
                        userId,
                        outfit
                );


                System.out.println(
                        "Outfit saved successfully!"
                );

            }

            else {


                System.out.println(
                        "Outfit already exists. Skipping save."
                );

            }




            System.out.println(
                    "\nLoading outfit..."
            );


            Outfit loadedOutfit =
                    repository.findById(
                            outfitId
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