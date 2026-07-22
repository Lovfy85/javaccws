package ui.outfit;

import javax.swing.*;
import java.awt.*;

import model.User;


public class UserInfoPanel extends JPanel {

    private JComboBox<String> styleSelector;
    private JComboBox<String> colorSelector;

    public UserInfoPanel(User user, Runnable refreshAction) {

        setBackground(new Color(240,240,240));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        add(
            Box.createVerticalStrut(10)
        );


        JPanel preferencePanel = new JPanel();

        preferencePanel.setBackground(
            new Color(240,240,240)
        );

        preferencePanel.setLayout(
            new FlowLayout(
                FlowLayout.CENTER
            )
        );


        JLabel styleLabel = new JLabel(
            "Current Aesthetic:"
        );


        styleSelector = new JComboBox<>(
            new String[]{
                "CASUAL",
                "FORMAL",
                "SPORTY",
                "STREETWEAR",
                "BUSINESS_CASUAL"
            }
        );


        styleSelector.setSelectedItem(
            user.getStylesProfile()
                .getStyle()
                .toUpperCase()
        );


        JLabel colorLabel = new JLabel(
            "Current Color Palette:"
        );


        colorSelector = new JComboBox<>(
            new String[]{
                "Neutral",
                "Warm",
                "Cool",
                "Earth Tone"
            }
        );


        colorSelector.setSelectedItem(
            user.getStylesProfile()
                .getColorPreference()
        );


        preferencePanel.add(styleLabel);
        preferencePanel.add(styleSelector);

        preferencePanel.add(
            Box.createHorizontalStrut(20)
        );

        preferencePanel.add(colorLabel);
        preferencePanel.add(colorSelector);


        preferencePanel.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );


        add(preferencePanel);


        add(
            Box.createVerticalStrut(10)
        );


        JButton updateButton =
            new JButton(
                "Update Preferences"
            );


        updateButton.addActionListener(e -> {

            user.getStylesProfile()
                .setStyle(
                    styleSelector
                        .getSelectedItem()
                        .toString()
                );


            user.getStylesProfile()
                .setColorPreference(
                    colorSelector
                        .getSelectedItem()
                        .toString()
                );


            if(refreshAction != null) {

                refreshAction.run();

            }


            JOptionPane.showMessageDialog(
                this,
                "Preferences updated!"
            );
        });


        updateButton.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );


        add(updateButton);
    }
}