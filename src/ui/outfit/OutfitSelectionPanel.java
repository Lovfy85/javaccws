package ui.outfit;

import javax.swing.*;
import java.awt.*;

import model.*;
import model.clothing.*;

import service.RecommendationEngine;

import repository.OutfitRepository;

import java.sql.SQLException;


public class OutfitSelectionPanel extends JPanel {

    private OutfitOptions options;
    private User user;
    private RecommendationEngine recommendationEngine;

    private OutfitRepository outfitRepository =
            new OutfitRepository();


    private JComboBox<Top> topSelector;
    private JComboBox<Bottom> bottomSelector;
    private JComboBox<Footwear> footwearSelector;

    private CategorySelectionPanel topPanel,bottomPanel,footwearPanel;

    private JLabel scoreLabel;


    private Outfit generatedOutfit;



    public OutfitSelectionPanel(
            OutfitOptions options,
            User user,
            RecommendationEngine recommendationEngine){

        this.options=options;
        this.user=user;
        this.recommendationEngine=recommendationEngine;

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBackground(new Color(240,240,240));

        buildUI();
    }



    private void buildUI(){

        JLabel title=new JLabel("Select Outfit Components");

        title.setFont(
                new Font("Arial",Font.BOLD,22));

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT);


        add(title);

        add(Box.createVerticalStrut(20));



        topSelector=
                new JComboBox<>(
                        options.getTops()
                        .toArray(new Top[0]));


        bottomSelector=
                new JComboBox<>(
                        options.getBottoms()
                        .toArray(new Bottom[0]));


        footwearSelector=
                new JComboBox<>(
                        options.getFootwear()
                        .toArray(new Footwear[0]));



        topPanel=
                new CategorySelectionPanel(
                        "TOPS",
                        options.getTops(),
                        topSelector);


        bottomPanel=
                new CategorySelectionPanel(
                        "BOTTOMS",
                        options.getBottoms(),
                        bottomSelector);


        footwearPanel=
                new CategorySelectionPanel(
                        "FOOTWEAR",
                        options.getFootwear(),
                        footwearSelector);



        add(topPanel);

        add(Box.createVerticalStrut(20));

        add(bottomPanel);

        add(Box.createVerticalStrut(20));

        add(footwearPanel);

        add(Box.createVerticalStrut(20));



        JButton generateButton =
                new JButton(
                        "Calculate Outfit Score");


        generateButton.setAlignmentX(
                Component.CENTER_ALIGNMENT);


        generateButton.addActionListener(
                e->generateOutfit());


        add(generateButton);


        add(Box.createVerticalStrut(10));



        JButton saveButton =
                new JButton(
                        "Save Outfit");


        saveButton.setAlignmentX(
                Component.CENTER_ALIGNMENT);


        saveButton.addActionListener(
                e->saveOutfit());


        add(saveButton);


        add(Box.createVerticalStrut(15));



        scoreLabel=
                new JLabel(
                        "Recommendation Score: 0");


        scoreLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18));


        scoreLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT);


        add(scoreLabel);

    }




    public void refresh(OutfitOptions options){

        this.options=options;


        topPanel.refresh(
                options.getTops());

        bottomPanel.refresh(
                options.getBottoms());

        footwearPanel.refresh(
                options.getFootwear());



        topSelector.removeAllItems();

        for(Top top:options.getTops())
            topSelector.addItem(top);



        bottomSelector.removeAllItems();

        for(Bottom bottom:options.getBottoms())
            bottomSelector.addItem(bottom);



        footwearSelector.removeAllItems();

        for(Footwear footwear:options.getFootwear())
            footwearSelector.addItem(footwear);



        scoreLabel.setText(
                "Recommendation Score: 0");


        generatedOutfit=null;


        revalidate();
        repaint();

    }





    private void generateOutfit(){

        try{


            generatedOutfit =
                    new Outfit(
                        java.util.UUID.randomUUID().toString(),
                        user.getId(),
                        (Top)topSelector.getSelectedItem(),
                        (Bottom)bottomSelector.getSelectedItem(),
                        (Footwear)footwearSelector.getSelectedItem()
                    );



            int score =
                recommendationEngine
                    .getOutfitScorer()
                    .scoreOutfit(
                        generatedOutfit,
                        user);



            generatedOutfit.setScore(score);



            scoreLabel.setText(
                    "Recommendation Score: "
                    + score);



            new OutfitDisplayUI(
                    user,
                    generatedOutfit
            ).show();



        }

        catch(Exception ex){

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage());

        }

    }





    private void saveOutfit(){


        if(generatedOutfit==null){

            JOptionPane.showMessageDialog(
                    this,
                    "Generate an outfit first.");

            return;

        }



        try{


            boolean exists =
                    outfitRepository.exists(
                            user.getId(),
                            generatedOutfit.getTop().getId(),
                            generatedOutfit.getBottom().getId(),
                            generatedOutfit.getFootwear().getId()
                    );



            if(exists){

                JOptionPane.showMessageDialog(
                        this,
                        "This outfit is already saved.");

                return;

            }



            outfitRepository.save(
                    generatedOutfit.getId(),
                    user.getId(),
                    generatedOutfit);



            user.addSavedOutfit(
                    generatedOutfit);



            JOptionPane.showMessageDialog(
                    this,
                    "Outfit saved successfully.");



        }

        catch(SQLException ex){

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage());

        }

    }

}