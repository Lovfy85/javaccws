package ui.outfit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.List;

import model.clothing.ClothingItem;


public class CategorySelectionPanel extends JPanel {

    private String category;
    private List<? extends ClothingItem> items;
    private JComboBox<?> selector;


    public CategorySelectionPanel(String category, List<? extends ClothingItem> items, JComboBox<?> selector) {

        this.category = category;
        this.items = items;
        this.selector = selector;


        setLayout(new BorderLayout());

        setBackground(Color.WHITE);

        setBorder(
            BorderFactory.createLineBorder(new Color(210, 210, 210))
        );

        add(createCategoryLabel(), BorderLayout.NORTH);

        add(createContentPanel(), BorderLayout.CENTER);
    }


    private JLabel createCategoryLabel() {

        JLabel categoryLabel = new JLabel(category);

        categoryLabel.setFont(new Font("Arial", Font.BOLD, 22));

        categoryLabel.setBorder(
            new EmptyBorder(10, 10, 10, 10)
        );


        return categoryLabel;
    }


    private JPanel createContentPanel() {

        JPanel contentPanel = new JPanel();


        contentPanel.setLayout(
            new BoxLayout(
                contentPanel,
                BoxLayout.Y_AXIS
            )
        );

        contentPanel.setBackground(Color.WHITE);


        for (ClothingItem item : items) {

            contentPanel.add(new ClothingCardPanel(item));

            contentPanel.add(Box.createVerticalStrut(10));
        }


        JLabel selectedLabel = new JLabel("Selected:");


        selectedLabel.setFont(new Font("Arial", Font.BOLD, 16));


        contentPanel.add(selectedLabel);


        selector.setMaximumSize(new Dimension(300, 35));


        contentPanel.add(selector);


        return contentPanel;
    }
}