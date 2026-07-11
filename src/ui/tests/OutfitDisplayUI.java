package ui.tests;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import model.Outfit;
import model.User;
import model.clothing.ClothingItem;
import util.ImageLoader;

public class OutfitDisplayUI {

    private User user;
    private Outfit outfit;


    public OutfitDisplayUI(User user, Outfit outfit) {

        this.user = user;
        this.outfit = outfit;

    }


    public void show() {

        JFrame frame = new JFrame("Clothing Capsule Wardrobe");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(900,800);

        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.setBackground(new Color(240, 240, 240));

        mainPanel.setBorder(
            new EmptyBorder(
                20,
                20,
                20,
                20
            )
        );

        JLabel title = new JLabel("Clothing Capsule Wardrobe");

        title.setFont(
            new Font("Arial", Font.BOLD, 28)
        );

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel userInfo = new JLabel(
                "User: "
                + user.getName()
                + " | Style: "
                + user.getStylesProfile().getStyle()
                + " | Preference: "
                + user.getStylesProfile().getColorPreference()
        );

        userInfo.setFont(
            new Font(
                "Arial",
                Font.PLAIN,
                18
            )
        );

        userInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(title);

        mainPanel.add(
            Box.createVerticalStrut(10)
        );

        mainPanel.add(userInfo);


        mainPanel.add(
            Box.createVerticalStrut(30)
        );

        mainPanel.add(createCard(outfit.getTop()));

        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(createCard(outfit.getBottom()));

        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(createCard(outfit.getFootwear()));

        JScrollPane scrollPane =
                new JScrollPane(
                        mainPanel
                );

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        frame.add(scrollPane);

        frame.setVisible(true);

    }


    private JPanel createCard(ClothingItem item) {

        JPanel card = new JPanel(
            new BorderLayout(
                20,
                10
            )
        );

        card.setBackground(Color.WHITE);

        card.setBorder(
            BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(
                        new Color(
                            210,
                            210,
                            210
                        )
                    ),
                    new EmptyBorder(
                        15,
                        15,
                        15,
                        15
                    )
            )
        );

        JLabel imageLabel = new JLabel(ImageLoader.load(item.getImagePath()));

        JPanel info = new JPanel();

        info.setBackground(Color.WHITE);

        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel(item.getName());

        nameLabel.setFont(
            new Font(
                    "Arial",
                    Font.BOLD,
                    22
            )
        );

        JLabel brandLabel = new JLabel("Brand: " + item.getBrand());

        JLabel colorLabel = new JLabel("Color: " + item.getColor());

        JLabel styleLabel = new JLabel("Style: " + item.getStyle());

        info.add(nameLabel);

        info.add(Box.createVerticalStrut(10));

        info.add(brandLabel);

        info.add(colorLabel);

        info.add(styleLabel);

        card.add(imageLabel, BorderLayout.WEST);

        card.add(info, BorderLayout.CENTER);

        return card;

    }
}