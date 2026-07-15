package ui.outfit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.List;

import model.clothing.ClothingItem;


public class CategorySelectionPanel extends JPanel {

    private String category;
    private JComboBox<?> selector;

    private JPanel contentPanel;


    public CategorySelectionPanel(String category, List<? extends ClothingItem> items, JComboBox<?> selector) {

        this.category = category;
        this.selector = selector;


        setLayout(new BorderLayout());

        setBackground(Color.WHITE);

        setBorder(
            BorderFactory.createLineBorder(new Color(210, 210, 210))
        );


        add(
            createCategoryLabel(),
            BorderLayout.NORTH
        );


        contentPanel = new JPanel();

        contentPanel.setLayout(
            new BoxLayout(
                contentPanel,
                BoxLayout.Y_AXIS
            )
        );

        contentPanel.setBackground(Color.WHITE);


        add(
            contentPanel,
            BorderLayout.CENTER
        );


        refresh(items);
    }


    private JLabel createCategoryLabel() {

        JLabel categoryLabel = new JLabel(category);

        categoryLabel.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                22
            )
        );

        categoryLabel.setBorder(
            new EmptyBorder(
                10,
                10,
                10,
                10
            )
        );


        return categoryLabel;
    }


    public void refresh(List<? extends ClothingItem> items) {

        contentPanel.removeAll();


        for(ClothingItem item : items) {

            contentPanel.add(
                new ClothingCardPanel(item)
            );

            contentPanel.add(
                Box.createVerticalStrut(10)
            );
        }


        JLabel selectedLabel = new JLabel("Selected:");

        selectedLabel.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                16
            )
        );


        contentPanel.add(selectedLabel);


        selector.setMaximumSize(
            new Dimension(
                300,
                35
            )
        );


        contentPanel.add(selector);


        revalidate();
        repaint();
    }
}