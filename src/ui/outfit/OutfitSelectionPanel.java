package ui.outfit;

import javax.swing.*;

import java.awt.*;

import model.*;
import model.clothing.*;

import service.RecommendationEngine;


public class OutfitSelectionPanel extends JPanel {

    private OutfitOptions options;
    private User user;
    private RecommendationEngine recommendationEngine;

    private JComboBox<Top> topSelector;
    private JComboBox<Bottom> bottomSelector;
    private JComboBox<Footwear> footwearSelector;

    private CategorySelectionPanel topPanel;
    private CategorySelectionPanel bottomPanel;
    private CategorySelectionPanel footwearPanel;

    private JLabel scoreLabel;
    private JLabel scoreDescription;

    private boolean scoreHidden = true;


    public OutfitSelectionPanel(OutfitOptions options, User user, RecommendationEngine recommendationEngine) {

        this.options = options;
        this.user = user;
        this.recommendationEngine = recommendationEngine;

        setLayout(
            new BoxLayout(
                this,
                BoxLayout.Y_AXIS
            )
        );

        setBackground(new Color(240, 240, 240));

        buildUI();
    }


    private void buildUI() {

        JLabel title = new JLabel("Select Outfit Components");

        title.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                22
            )
        );

        title.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        add(title);

        add(Box.createVerticalStrut(20));


        topSelector = new JComboBox<>(
            options.getTops()
                .toArray(new Top[0])
        );

        bottomSelector = new JComboBox<>(
            options.getBottoms()
                .toArray(new Bottom[0])
        );

        footwearSelector = new JComboBox<>(
            options.getFootwear()
                .toArray(new Footwear[0])
        );


        topPanel =
            new CategorySelectionPanel(
                "TOPS",
                options.getTops(),
                topSelector
            );

        bottomPanel =
            new CategorySelectionPanel(
                "BOTTOMS",
                options.getBottoms(),
                bottomSelector
            );

        footwearPanel =
            new CategorySelectionPanel(
                "FOOTWEAR",
                options.getFootwear(),
                footwearSelector
            );


        add(topPanel);

        add(Box.createVerticalStrut(20));

        add(bottomPanel);

        add(Box.createVerticalStrut(20));

        add(footwearPanel);

        add(Box.createVerticalStrut(20));


        JButton generateButton = new JButton("Generate Outfit");

        generateButton.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        generateButton.addActionListener(
            e -> generateOutfit()
        );

        add(generateButton);

        add(Box.createVerticalStrut(15));


        scoreLabel =
            new JLabel(
                "Recommendation Score: 0"
            );

        scoreLabel.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                18
            )
        );

        scoreLabel.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        add(scoreLabel);

        add(Box.createVerticalStrut(20));


        JButton scoreButton =
            new JButton(
                "Show Score Details"
            );


        scoreButton.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );


        scoreDescription =
            new JLabel(
                "<html>"
                + "Score evaluates:<br>"
                + "- color compatibility between clothing items<br>"
                + "- matching your preferred color profile<br>"
                + "- outfit style consistency<br>"
                + "- versatility of neutral colors<br><br>"
                + "Score Rating:<br>"
                + "60 - 70: Excellent match<br>"
                + "45 - 59: Strong match<br>"
                + "30 - 44: Good match<br>"
                + "15 - 29: Fair match<br>"
                + "0 - 14: Poor match"
                + "</html>"
            );


        scoreDescription.setFont(
            new Font(
                "Arial",
                Font.PLAIN,
                14
            )
        );


        scoreDescription.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );


        scoreDescription.setVisible(false);


        scoreButton.addActionListener(
            e -> {

                scoreHidden = !scoreHidden;

                scoreDescription.setVisible(
                    !scoreHidden
                );


                if(scoreHidden) {

                    scoreButton.setText(
                        "Show Score Details"
                    );

                } else {

                    scoreButton.setText(
                        "Hide Score Details"
                    );
                }


                revalidate();
                repaint();
            }
        );


        add(scoreButton);

        add(
            Box.createVerticalStrut(5)
        );

        add(scoreDescription);
    }


    public void refresh(OutfitOptions options) {

        this.options = options;


        topPanel.refresh(
            options.getTops()
        );

        bottomPanel.refresh(
            options.getBottoms()
        );

        footwearPanel.refresh(
            options.getFootwear()
        );


        topSelector.removeAllItems();

        for(Top top : options.getTops()) {
            topSelector.addItem(top);
        }


        bottomSelector.removeAllItems();

        for(Bottom bottom : options.getBottoms()) {
            bottomSelector.addItem(bottom);
        }


        footwearSelector.removeAllItems();

        for(Footwear footwear : options.getFootwear()) {
            footwearSelector.addItem(footwear);
        }


        scoreLabel.setText(
            "Recommendation Score: 0"
        );


        revalidate();
        repaint();
    }


    private void generateOutfit() {

        try {

            Outfit selectedOutfit =
                new Outfit(
                    java.util.UUID.randomUUID().toString(),
                    user.getId(),
                    (Top) topSelector.getSelectedItem(),
                    (Bottom) bottomSelector.getSelectedItem(),
                    (Footwear) footwearSelector.getSelectedItem()
                );


            int score =
                recommendationEngine
                    .getOutfitScorer()
                    .scoreOutfit(
                        selectedOutfit,
                        user
                    );


            selectedOutfit.setScore(score);


            scoreLabel.setText(
                "Recommendation Score: "
                + score
            );

        }

        catch(Exception ex) {

            JOptionPane.showMessageDialog(
                this,
                ex.getMessage()
            );
        }
    }
}