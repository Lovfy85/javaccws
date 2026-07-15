package ui.outfit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import model.clothing.ClothingItem;

import util.*;

public class ClothingCardPanel extends JPanel {

    private ClothingItem item;

    public ClothingCardPanel(ClothingItem item) {

        this.item = item;

        setLayout(new BorderLayout(30, 10));

        setBackground(Color.WHITE);

        setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)),
                new EmptyBorder(20, 20, 20, 20)
            )
        );

        add(createImageLabel(), BorderLayout.WEST);

        add(createInfoPanel(), BorderLayout.CENTER);

        setPreferredSize(new Dimension(820, 240));

        setMaximumSize(new Dimension(820, 240));
    }


    private JLabel createImageLabel() {

        ImageIcon originalIcon = ImageLoader.load(item.getImagePath());


        int maxImageWidth = 180;
        int maxImageHeight = 180;


        int originalWidth = originalIcon.getIconWidth();

        int originalHeight = originalIcon.getIconHeight();


        double scale = Math.min(
            (double) maxImageWidth / originalWidth,
            (double) maxImageHeight / originalHeight
        );


        int scaledWidth = (int) (originalWidth * scale);

        int scaledHeight = (int) (originalHeight * scale);


        Image scaledImage = originalIcon
                .getImage()
                .getScaledInstance(
                    scaledWidth,
                    scaledHeight,
                    Image.SCALE_SMOOTH
                );


        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));


        imageLabel.setPreferredSize(
            new Dimension(
                scaledWidth,
                scaledHeight
            )
        );


        return imageLabel;
    }


    private JPanel createInfoPanel() {

        JPanel info = new JPanel();


        info.setBackground(Color.WHITE);

        info.setLayout(
            new BoxLayout(
                info,
                BoxLayout.Y_AXIS
            )
        );


        JLabel nameLabel = new JLabel(item.getName());

        nameLabel.setFont(new Font("Arial", Font.BOLD, 26));


        JLabel brandLabel = new JLabel(
            "Brand: " + item.getBrand()
        );


        JLabel colorLabel = new JLabel(
            "Color: " + item.getColor()
        );


        JLabel colorCategoryLabel = new JLabel(
            "Color Category: "
            + ColorMatcher.getColorCategory(item.getColor())
        );


        JLabel styleLabel = new JLabel(
            "Style: " + item.getStyle()
        );


        Font detailFont = new Font("Arial", Font.PLAIN, 18);


        brandLabel.setFont(detailFont);
        colorLabel.setFont(detailFont);
        colorCategoryLabel.setFont(detailFont);
        styleLabel.setFont(detailFont);


        info.add(nameLabel);

        info.add(Box.createVerticalStrut(10));

        info.add(brandLabel);

        info.add(colorLabel);

        info.add(colorCategoryLabel);

        info.add(styleLabel);

        return info;
    }
}