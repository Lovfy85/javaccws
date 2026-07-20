package ui.tests;


import model.*;
import model.clothing.*;

import repository.*;

import java.util.List;


public class WardrobeRepositoryTest {


    public static void main(String[] args) {


        WardrobeRepository wardrobeRepository =
                new WardrobeRepository();


        UserRepository userRepository =
                new UserRepository();



        try {


            System.out.println(
                    "=== WARDROBE REPOSITORY TEST ==="
            );



            User user =
                    userRepository.findByUsername(
                            "Alucrias"
                    );


            if(user == null) {

                System.out.println(
                        "User not found."
                );

                return;

            }



            String userId =
                    user.getId();



            System.out.println(
                    "Testing user: "
                    + user.getUsername()
            );


            System.out.println(
                    "User ID: "
                    + userId
            );




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
                        "Blue Jeans",
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
                        "White Sneakers",
                        "White",
                        "Adidas",
                        "resources/images/footwear/casual_white_sneakers.jpg",
                        ClothingStyle.CASUAL,
                        "SNEAKER"
                );





            System.out.println(
                    "\nChecking existing wardrobe..."
            );



            List<ClothingItem> existingItems =
                    wardrobeRepository.findByUserId(userId);



            if(existingItems.isEmpty()) {


                System.out.println(
                        "Saving clothing items..."
                );


                wardrobeRepository.save(
                        top,
                        userId
                );


                wardrobeRepository.save(
                        bottom,
                        userId
                );


                wardrobeRepository.save(
                        footwear,
                        userId
                );


                System.out.println(
                        "Clothing saved successfully!"
                );

            }

            else {


                System.out.println(
                        "Clothing items already exist. Skipping save."
                );

            }


            System.out.println(
                    "\nLoading wardrobe..."
            );



            List<ClothingItem> items =
                    wardrobeRepository.findByUserId(userId);




            for(ClothingItem item : items) {


                System.out.println(
                        "- "
                        + item
                );


                System.out.println(
                        "Class: "
                        + item.getClass().getSimpleName()
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