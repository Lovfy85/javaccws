package ui.outfit;

import javax.swing.*;

import java.awt.*;

import model.Outfit;


public class GeneratedOutfitPanel extends JPanel {

    private JPanel outfitContent;
    private JButton toggleButton;
    private boolean collapsed = false;

    public GeneratedOutfitPanel() {

        setLayout(
            new BoxLayout(
                this,
                BoxLayout.Y_AXIS
            )
        );

        setBackground(new Color(240, 240, 240));


        toggleButton = new JButton("Hide Generated Outfit");

        toggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        toggleButton.addActionListener(e -> toggleVisibility());


        add(toggleButton);

        add(Box.createVerticalStrut(10));


        outfitContent = new JPanel();

        outfitContent.setLayout(
            new BoxLayout(
                outfitContent,
                BoxLayout.Y_AXIS
            )
        );

        outfitContent.setBackground(new Color(240, 240, 240));


        add(outfitContent);
    }


    public void display(Outfit outfit) {

        outfitContent.removeAll();


        JLabel title = new JLabel("Generated Outfit");

        title.setFont(new Font("Arial", Font.BOLD, 22));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        outfitContent.add(title);

        outfitContent.add(Box.createVerticalStrut(15));

        outfitContent.add(new ClothingCardPanel(outfit.getTop()));

        outfitContent.add(Box.createVerticalStrut(15));

        outfitContent.add(new ClothingCardPanel(outfit.getBottom()));

        outfitContent.add(Box.createVerticalStrut(15));

        outfitContent.add(new ClothingCardPanel(outfit.getFootwear()));

        outfitContent.setVisible(!collapsed);

        revalidate();
        repaint();
    }


    private void toggleVisibility() {

        collapsed = !collapsed;

        outfitContent.setVisible(!collapsed);

        if (collapsed) {

            toggleButton.setText("Show Generated Outfit");

        } else {

            toggleButton.setText("Hide Generated Outfit");
        }

        revalidate();
        repaint();
    }
}