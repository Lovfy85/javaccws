import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import model.*;
import model.clothing.*;
import exception.InvalidClothingException;
import util.ImageLoader;

public class Main {

    public static void main(String[] args) {

        try {

            StylesProfile stylesProfile = new StylesProfile(
                    "Casual",
                    "Neutral"
            );

            Wardrobe wardrobe = new Wardrobe();

            Top top = new Top(
                    "T1",
                    "White T-Shirt",
                    "White",
                    "Uniqlo",
                    "resources/images/tops/white_tshirt.jpg",
                    "Short Sleeve"
            );

            Bottom bottom = new Bottom(
                    "B1",
                    "Blue Jeans",
                    "Blue",
                    "Levi's",
                    "resources/images/bottoms/blue_jeans.jpg",
                    "Slim Fit"
            );

            Footwear footwear = new Footwear(
                    "F1",
                    "White Sneakers",
                    "White",
                    "Nike",
                    "resources/images/footwear/white_sneakers.jpg",
                    "Sneakers"
            );

            wardrobe.addItem(top);
            wardrobe.addItem(bottom);
            wardrobe.addItem(footwear);


            User user = new User(
                    "U1",
                    "Cedar",
                    stylesProfile,
                    wardrobe
            );


            Outfit outfit = new Outfit(
                    top,
                    bottom,
                    footwear
            );


            user.display();

            System.out.println();

            outfit.display();



            JFrame frame = new JFrame(
                    "Clothing Capsule Wardrobe"
            );

            frame.setDefaultCloseOperation(
                    JFrame.EXIT_ON_CLOSE
            );

            frame.setSize(
                    900,
                    800
            );

            frame.setLocationRelativeTo(null);



            JPanel mainPanel = new JPanel();

            mainPanel.setLayout(
                    new BoxLayout(
                            mainPanel,
                            BoxLayout.Y_AXIS
                    )
            );

            mainPanel.setBackground(
                    new Color(
                            240,
                            240,
                            240
                    )
            );

            mainPanel.setBorder(
                    new EmptyBorder(
                            20,
                            20,
                            20,
                            20
                    )
            );


            JLabel title = new JLabel(
                    "Clothing Capsule Wardrobe"
            );

            title.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            28
                    )
            );

            title.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );


            JLabel userInfo = new JLabel(
                    "User: " + user.getName()
                    + " | Style: " + stylesProfile.getStyle()
                    + " | Preference: " + stylesProfile.getColorPreference()
            );

            userInfo.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            18
                    )
            );

            userInfo.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );



            mainPanel.add(title);

            mainPanel.add(
                    Box.createVerticalStrut(10)
            );

            mainPanel.add(userInfo);

            mainPanel.add(
                    Box.createVerticalStrut(30)
            );


            mainPanel.add(
                    createCard(
                            top.getName(),
                            top.getBrand(),
                            top.getColor(),
                            ImageLoader.load(
                                    top.getImagePath()
                            )
                    )
            );

            mainPanel.add(
                    Box.createVerticalStrut(20)
            );


            mainPanel.add(
                    createCard(
                            bottom.getName(),
                            bottom.getBrand(),
                            bottom.getColor(),
                            ImageLoader.load(
                                    bottom.getImagePath()
                            )
                    )
            );


            mainPanel.add(
                    Box.createVerticalStrut(20)
            );


            mainPanel.add(
                    createCard(
                            footwear.getName(),
                            footwear.getBrand(),
                            footwear.getColor(),
                            ImageLoader.load(
                                    footwear.getImagePath()
                            )
                    )
            );


            JScrollPane scrollPane = new JScrollPane(
                    mainPanel
            );


            scrollPane.getVerticalScrollBar()
                    .setUnitIncrement(16);


            frame.add(scrollPane);

            frame.setVisible(true);

        } catch (InvalidClothingException e) {

            System.out.println(
                    "Invalid clothing detected: "
                    + e.getMessage()
            );
        }
    }



    private static JPanel createCard(
            String name,
            String brand,
            String color,
            ImageIcon image
    ) {

        JPanel card = new JPanel(
                new BorderLayout(
                        20,
                        10
                )
        );


        card.setBackground(
                Color.WHITE
        );


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


        JLabel imageLabel = new JLabel(
                image
        );


        JPanel info = new JPanel();

        info.setBackground(
                Color.WHITE
        );

        info.setLayout(
                new BoxLayout(
                        info,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel nameLabel = new JLabel(
                name
        );


        nameLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );


        JLabel brandLabel = new JLabel(
                "Brand: " + brand
        );


        JLabel colorLabel = new JLabel(
                "Color: " + color
        );


        info.add(nameLabel);

        info.add(
                Box.createVerticalStrut(10)
        );

        info.add(brandLabel);

        info.add(colorLabel);



        card.add(
                imageLabel,
                BorderLayout.WEST
        );


        card.add(
                info,
                BorderLayout.CENTER
        );


        return card;
    }
}