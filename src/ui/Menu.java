package ui;

import javax.swing.*;
import ui.auth.LoginPanel;

public class Menu {

    public void start() {

        JFrame frame = new JFrame("Clothing Capsule Wardrobe System");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(500,400);

        frame.setLocationRelativeTo(null);

        frame.setContentPane(new LoginPanel(frame));

        frame.setVisible(true);
    }
}