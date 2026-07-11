package ui.tests;

import util.ColorMatcher;

public class ColorMatcherTestUI {

    public void runTest() {

        System.out.println("==============================");
        System.out.println("Color Compatibility Tests");
        System.out.println("==============================");

        testCompatibility(
                "White",
                "Black"
        );

        testCompatibility(
                "Brown",
                "Blue"
        );


        testCompatibility(
                "Beige",
                "Green"
        );


        testCompatibility(
                "Red",
                "Purple"
        );


        testCompatibility(
                "Yellow",
                "Blue"
        );


        testCompatibility(
                "Pink",
                "Orange"
        );


        testCompatibility(
                "Black",
                "Green"
        );


        testCompatibility(
                "Red",
                "Blue"
        );



        System.out.println();

        System.out.println("==============================");
        System.out.println("Color Category Tests");
        System.out.println( "==============================");

        testCategory(
                "White"
        );


        testCategory(
                "Red"
        );


        testCategory(
                "Blue"
        );


        testCategory(
                "Brown"
        );


        testCategory(
                "Black"
        );


        testCategory(
                "Green"
        );

    }


    private void testCompatibility(String color1, String color2) {

        System.out.println(
                color1
                + " + "
                + color2
                + ": "
                + ColorMatcher.isCompatible(
                        color1,
                        color2
                )
        );
    }

    private void testCategory(String color) {
        
        System.out.println(
                color
                + ": "
                + ColorMatcher.getColorCategory(
                        color
                )
        );

    }

}