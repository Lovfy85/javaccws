package ui.tests;

import model.ClothingStyle;
import model.Wardrobe;
import model.clothing.*;
import exception.InvalidClothingException;


public class TestDataFactory {

    public static Wardrobe createSampleWardrobe() throws InvalidClothingException {

        Wardrobe wardrobe = new Wardrobe();

        // CASUAL
        wardrobe.addItem(
                new Top(
                        "T1",
                        "White T-Shirt",
                        "White",
                        "Uniqlo",
                        "resources/images/tops/casual_tshirt.jpg",
                        ClothingStyle.CASUAL,
                        "Short Sleeve"
                )
        );

        wardrobe.addItem(
                new Bottom(
                        "B1",
                        "Blue Jeans",
                        "Blue",
                        "Levi's",
                        "resources/images/bottoms/casual_blue_jeans.jpg",
                        ClothingStyle.CASUAL,
                        "Slim Fit"
                )
        );

        wardrobe.addItem(
                new Footwear(
                        "F1",
                        "White Sneakers",
                        "White",
                        "Nike",
                        "resources/images/footwear/casual_white_sneakers.jpg",
                        ClothingStyle.CASUAL,
                        "Sneakers"
                )
        );


        // FORMAL
        wardrobe.addItem(
                new Top(
                        "T2",
                        "Dress Shirt",
                        "White",
                        "Hugo Boss",
                        "resources/images/tops/formal_dress_shirt.jpg",
                        ClothingStyle.FORMAL,
                        "Long Sleeve"
                )
        );

        wardrobe.addItem(
                new Bottom(
                        "B2",
                        "Dress Pants",
                        "Black",
                        "Calvin Klein",
                        "resources/images/bottoms/formal_dress_pants.jpg",
                        ClothingStyle.FORMAL,
                        "Slim Fit"
                )
        );

        wardrobe.addItem(
                new Footwear(
                        "F2",
                        "Dress Shoes",
                        "Black",
                        "Clarks",
                        "resources/images/footwear/formal_dress_shoes.jpg",
                        ClothingStyle.FORMAL,
                        "Leather Shoes"
                )
        );


        // SPORTY
        wardrobe.addItem(
                new Top(
                        "T3",
                        "Training Shirt",
                        "Black",
                        "Nike",
                        "resources/images/tops/sporty_training_shirt.jpg",
                        ClothingStyle.SPORTY,
                        "Athletic"
                )
        );

        wardrobe.addItem(
                new Bottom(
                        "B3",
                        "Joggers",
                        "Grey",
                        "Adidas",
                        "resources/images/bottoms/sporty_joggers.jpg",
                        ClothingStyle.SPORTY,
                        "Relaxed Fit"
                )
        );

        wardrobe.addItem(
                new Footwear(
                        "F3",
                        "Running Shoes",
                        "Black",
                        "Nike",
                        "resources/images/footwear/sporty_running_shoes.jpg",
                        ClothingStyle.SPORTY,
                        "Running Shoes"
                )
        );


        // STREETWEAR
        wardrobe.addItem(
                new Top(
                        "T4",
                        "Oversized Tee",
                        "Black",
                        "Supreme",
                        "resources/images/tops/streetwear_oversized_tee.jpg",
                        ClothingStyle.STREETWEAR,
                        "Oversized"
                )
        );

        wardrobe.addItem(
                new Bottom(
                        "B4",
                        "Cargo Pants",
                        "Green",
                        "Carhartt",
                        "resources/images/bottoms/streetwear_cargo_pants.jpg",
                        ClothingStyle.STREETWEAR,
                        "Loose Fit"
                )
        );

        wardrobe.addItem(
                new Footwear(
                        "F4",
                        "High Top Sneakers",
                        "White",
                        "Converse",
                        "resources/images/footwear/streetwear_high_top_sneakers.jpg",
                        ClothingStyle.STREETWEAR,
                        "High Top"
                )
        );


        // BUSINESS CASUAL
        wardrobe.addItem(
                new Top(
                        "T5",
                        "Polo Shirt",
                        "Navy",
                        "Ralph Lauren",
                        "resources/images/tops/businesscasual_polo.jpg",
                        ClothingStyle.BUSINESS_CASUAL,
                        "Short Sleeve"
                )
        );

        wardrobe.addItem(
                new Bottom(
                        "B5",
                        "Khaki Pants",
                        "Beige",
                        "Dockers",
                        "resources/images/bottoms/businesscasual_khaki_pants.jpg",
                        ClothingStyle.BUSINESS_CASUAL,
                        "Straight Fit"
                )
        );

        wardrobe.addItem(
                new Footwear(
                        "F5",
                        "Leather Sneakers",
                        "Brown",
                        "Cole Haan",
                        "resources/images/footwear/businesscasual_leather_sneakers.jpg",
                        ClothingStyle.BUSINESS_CASUAL,
                        "Leather Sneakers"
                )
        );

        return wardrobe;

    }

}