package ui.outfit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import model.clothing.ClothingItem;
import util.ColorMatcher;
import util.ImageLoader;


public class ClothingCardPanel extends JPanel {

    private JLabel imageLabel;
    private JLabel nameLabel;
    private JLabel brandLabel;
    private JLabel colorLabel;
    private JLabel categoryLabel;
    private JLabel styleLabel;


    public ClothingCardPanel(ClothingItem item){

        build();
        updateItem(item);
    }


    private void build(){

        setLayout(new BorderLayout(20,10));
        setBackground(Color.WHITE);

        setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(210,210,210)
                ),
                new EmptyBorder(15,15,15,15)
            )
        );


        imageLabel = new JLabel();

        imageLabel.setPreferredSize(
            new Dimension(170,170)
        );


        JPanel info = new JPanel();

        info.setBackground(Color.WHITE);

        info.setLayout(
            new BoxLayout(
                info,
                BoxLayout.Y_AXIS
            )
        );


        nameLabel = new JLabel();

        nameLabel.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                24
            )
        );


        Font font =
            new Font(
                "Arial",
                Font.PLAIN,
                16
            );


        brandLabel = new JLabel();
        colorLabel = new JLabel();
        categoryLabel = new JLabel();
        styleLabel = new JLabel();


        brandLabel.setFont(font);
        colorLabel.setFont(font);
        categoryLabel.setFont(font);
        styleLabel.setFont(font);


        info.add(nameLabel);
        info.add(Box.createVerticalStrut(8));
        info.add(brandLabel);
        info.add(colorLabel);
        info.add(categoryLabel);
        info.add(styleLabel);


        add(
            imageLabel,
            BorderLayout.WEST
        );

        add(
            info,
            BorderLayout.CENTER
        );


        setPreferredSize(
            new Dimension(820,220)
        );

        setMaximumSize(
            new Dimension(820,220)
        );

        setAlignmentX(
            Component.CENTER_ALIGNMENT
        );
    }


    public void updateItem(ClothingItem item){

        ImageIcon icon =
            ImageLoader.load(
                item.getImagePath()
            );


        Image img =
            icon.getImage()
            .getScaledInstance(
                160,
                160,
                Image.SCALE_SMOOTH
            );


        imageLabel.setIcon(
            new ImageIcon(img)
        );


        nameLabel.setText(
            item.getName()
        );


        brandLabel.setText(
            "Brand: " + item.getBrand()
        );


        colorLabel.setText(
            "Color: " + item.getColor()
        );


        categoryLabel.setText(
            "Color Category: "
            + ColorMatcher.getColorCategory(
                item.getColor()
            )
        );


        styleLabel.setText(
            "Style: " + item.getStyle()
        );


        revalidate();
        repaint();
    }
}