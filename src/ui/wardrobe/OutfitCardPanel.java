package ui.wardrobe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import model.Outfit;
import model.clothing.*;
import util.ImageLoader;


public class OutfitCardPanel extends JPanel {

    private JLabel topImage = new JLabel();
    private JLabel bottomImage = new JLabel();
    private JLabel footwearImage = new JLabel();

    private JLabel topName = new JLabel();
    private JLabel bottomName = new JLabel();
    private JLabel footwearName = new JLabel();

    private JLabel scoreLabel = new JLabel();


    private Outfit outfit;


    public OutfitCardPanel(Outfit outfit){

        this.outfit = outfit;

        setLayout(
            new BoxLayout(
                this,
                BoxLayout.Y_AXIS
            )
        );


        setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                new EmptyBorder(10,10,10,10)
            )
        );


        setBackground(Color.WHITE);

        buildUI();

    }



    private void buildUI(){


        JLabel title =
                new JLabel(
                    "Saved Outfit"
                );


        title.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                18
            )
        );


        title.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );


        add(title);


        add(
            Box.createVerticalStrut(10)
        );



        JPanel images =
                new JPanel(
                    new GridLayout(
                        1,
                        3,
                        10,
                        10
                    )
                );


        images.setBackground(Color.WHITE);



        images.add(
            createItemPanel(
                outfit.getTop(),
                topImage,
                topName
            )
        );


        images.add(
            createItemPanel(
                outfit.getBottom(),
                bottomImage,
                bottomName
            )
        );


        images.add(
            createItemPanel(
                outfit.getFootwear(),
                footwearImage,
                footwearName
            )
        );



        add(images);



        add(
            Box.createVerticalStrut(10)
        );



        scoreLabel.setText(
            "Score: "
            + outfit.getScore()
        );


        scoreLabel.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                15
            )
        );


        scoreLabel.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );


        add(scoreLabel);

    }





    private JPanel createItemPanel(
            ClothingItem item,
            JLabel image,
            JLabel name){


        JPanel panel =
                new JPanel();


        panel.setLayout(
            new BoxLayout(
                panel,
                BoxLayout.Y_AXIS
            )
        );


        panel.setBackground(
            Color.WHITE
        );


        if(item != null){


            image.setIcon(
                ImageLoader.load(
                    item.getImagePath(),
                    100,
                    100
                )
            );


            image.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );


            name.setText(
                item.getName()
            );


            name.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );


            panel.add(image);

            panel.add(
                Box.createVerticalStrut(5)
            );


            panel.add(name);

        }


        return panel;

    }




    public Outfit getOutfit(){

        return outfit;

    }

}