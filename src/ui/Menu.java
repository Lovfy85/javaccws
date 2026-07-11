package ui;

import ui.tests.WardrobeTestUI;
import ui.tests.ColorMatcherTestUI;

public class Menu {

    public void start() {

        System.out.println("Starting tests...");

        ColorMatcherTestUI colorTest = new ColorMatcherTestUI();

        colorTest.runTest();

        WardrobeTestUI wardrobeTest = new WardrobeTestUI();

        wardrobeTest.runTest();

    }

}