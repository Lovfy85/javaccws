package ui.outfit;

import javax.swing.*;

import java.awt.*;

import model.Outfit;
import model.OutfitOptions;
import model.User;

import model.clothing.Top;
import model.clothing.Bottom;
import model.clothing.Footwear;

import service.RecommendationEngine;


public class OutfitSelectionPanel extends JPanel {

    private OutfitOptions options;
    private User user;
    private RecommendationEngine recommendationEngine;

    private JComboBox<Top> topSelector;
    private JComboBox<Bottom> bottomSelector;
    private JComboBox<Footwear> footwearSelector;

    private JLabel scoreLabel;

    private GeneratedOutfitPanel generatedOutfitPanel;


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

        title.setFont(new Font("Arial", Font.BOLD, 22));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(title);

        add(Box.createVerticalStrut(20));


        topSelector = new JComboBox<>(
            options.getTops().toArray(new Top[0])
        );

        bottomSelector = new JComboBox<>(
            options.getBottoms().toArray(new Bottom[0])
        );

        footwearSelector = new JComboBox<>(
            options.getFootwear().toArray(new Footwear[0])
        );


        add(
            new CategorySelectionPanel(
                "TOPS",
                options.getTops(),
                topSelector
            )
        );

        add(Box.createVerticalStrut(20));

        add(
            new CategorySelectionPanel(
                "BOTTOMS",
                options.getBottoms(),
                bottomSelector
            )
        );

        add(Box.createVerticalStrut(20));

        add(
            new CategorySelectionPanel(
                "FOOTWEAR",
                options.getFootwear(),
                footwearSelector
            )
        );

        add(Box.createVerticalStrut(20));


        JButton generateButton = new JButton("Generate Outfit");

        generateButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        generateButton.addActionListener(e -> generateOutfit());

        add(generateButton);

        add(Box.createVerticalStrut(15));


        scoreLabel = new JLabel("Recommendation Score: 0");

        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));

        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(scoreLabel);

        add(Box.createVerticalStrut(20));


        generatedOutfitPanel = new GeneratedOutfitPanel();

        add(generatedOutfitPanel);
    }


    private void generateOutfit() {

        try {

            Outfit selectedOutfit = new Outfit(
                (Top) topSelector.getSelectedItem(),
                (Bottom) bottomSelector.getSelectedItem(),
                (Footwear) footwearSelector.getSelectedItem()
            );

            int score = recommendationEngine
                .getOutfitScorer()
                .scoreOutfit(
                    selectedOutfit,
                    user
                );

            selectedOutfit.setScore(score);

            scoreLabel.setText("Recommendation Score: " + score);

            generatedOutfitPanel.display(selectedOutfit);

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                this,
                ex.getMessage()
            );
        }
    }
}