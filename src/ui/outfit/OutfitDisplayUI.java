package ui.outfit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import model.*;

import service.RecommendationEngine;
import service.WardrobeService;

import exception.InvalidClothingException;


public class OutfitDisplayUI {

    private User user;
    private Outfit outfit;
    private OutfitOptions options;
    private RecommendationEngine recommendationEngine;

    private OutfitSelectionPanel outfitSelectionPanel;

    private WardrobeService wardrobeService;



    public OutfitDisplayUI(User user, Outfit outfit) {

        this.user = user;
        this.outfit = outfit;

        wardrobeService = new WardrobeService();
    }



    public OutfitDisplayUI(User user, OutfitOptions options, RecommendationEngine recommendationEngine) {

        this.user = user;
        this.options = options;
        this.recommendationEngine = recommendationEngine;

        wardrobeService = new WardrobeService();
    }



    public void show() {

        JFrame frame = new JFrame("Clothing Capsule Wardrobe");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(900,800);

        frame.setLocationRelativeTo(null);



        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(
                new BoxLayout(
                        mainPanel,
                        BoxLayout.Y_AXIS)
        );

        mainPanel.setBackground(
                new Color(240,240,240)
        );

        mainPanel.setBorder(
                new EmptyBorder(20,20,20,20)
        );



        JLabel title =
                new JLabel(
                        "Clothing Capsule Wardrobe"
                );


        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28)
        );


        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );



        mainPanel.add(title);

        mainPanel.add(
                Box.createVerticalStrut(10)
        );



        mainPanel.add(
                new UserInfoPanel(
                        user,
                        this::refreshOutfitOptions
                )
        );



        mainPanel.add(
                Box.createVerticalStrut(30)
        );



        if(options != null){


            outfitSelectionPanel =
                    new OutfitSelectionPanel(
                            options,
                            user,
                            recommendationEngine
                    );


            mainPanel.add(
                    outfitSelectionPanel
            );


        }


        else if(outfit != null){


            mainPanel.add(
                    createRecommendedOutfitPanel()
            );


        }




        JScrollPane scrollPane =
                new JScrollPane(mainPanel);


        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);



        frame.add(scrollPane);

        frame.setVisible(true);

    }





    public void refreshOutfitOptions(){

        try{

            options =
                    recommendationEngine
                            .getOutfitOptions(user);



            if(outfitSelectionPanel != null){

                outfitSelectionPanel.refresh(
                        options
                );

            }


        }


        catch(InvalidClothingException e){

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );

        }

    }





    private JPanel createRecommendedOutfitPanel(){


        JPanel panel =
                new JPanel();


        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS)
        );


        panel.setBackground(
                new Color(240,240,240)
        );



        JLabel title =
                new JLabel(
                        "Recommended Outfit"
                );


        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22)
        );


        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        panel.add(title);



        panel.add(
                Box.createVerticalStrut(20)
        );





        JLabel top =
                new JLabel(
                        "Top: "
                        + outfit.getTop().getName()
                );


        JLabel bottom =
                new JLabel(
                        "Bottom: "
                        + outfit.getBottom().getName()
                );


        JLabel footwear =
                new JLabel(
                        "Footwear: "
                        + outfit.getFootwear().getName()
                );



        JLabel score =
                new JLabel(
                        "Recommendation Score: "
                        + outfit.getScore()
                );



        top.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        bottom.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        footwear.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        score.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );



        score.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18)
        );



        panel.add(top);

        panel.add(bottom);

        panel.add(footwear);


        panel.add(
                Box.createVerticalStrut(15)
        );


        panel.add(score);



        panel.add(
                Box.createVerticalStrut(25)
        );





        JButton saveButton =
                new JButton(
                        "Save Outfit"
                );


        saveButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );



        saveButton.addActionListener(e->{


            try{


                wardrobeService.saveOutfit(
                        outfit,
                        user
                );


                JOptionPane.showMessageDialog(
                        null,
                        "Outfit saved successfully!"
                );


            }


            catch(InvalidClothingException ex){


                JOptionPane.showMessageDialog(
                        null,
                        ex.getMessage()
                );


            }


            catch(Exception ex){


                JOptionPane.showMessageDialog(
                        null,
                        "Error saving outfit: "
                        + ex.getMessage()
                );


            }


        });




        panel.add(saveButton);



        return panel;

    }

}