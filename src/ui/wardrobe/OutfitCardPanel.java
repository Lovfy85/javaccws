package ui.wardrobe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

import model.*;
import model.clothing.*;
import repository.OutfitRepository;
import util.ImageLoader;

public class OutfitCardPanel extends JPanel {

    private Outfit outfit;
    private User user;
    private Runnable refresh;
    private OutfitRepository repository = new OutfitRepository();

    private JLabel topImage = new JLabel();
    private JLabel bottomImage = new JLabel();
    private JLabel footwearImage = new JLabel();

    public OutfitCardPanel(Outfit outfit, User user, Runnable refresh) {
        this.outfit = outfit;
        this.user = user;
        this.refresh = refresh;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                new EmptyBorder(10, 10, 10, 10)));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(330, 260));
        setMaximumSize(new Dimension(330, 260));

        buildUI();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                menu();
            }
        });
    }

    private void buildUI() {
        JLabel title = new JLabel("Saved Outfit");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);

        JLabel style = new JLabel("Style: " + getStyle());
        style.setFont(new Font("Arial", Font.PLAIN, 14));
        style.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(style);

        add(Box.createVerticalStrut(8));

        JPanel images = new JPanel(new GridLayout(1, 3, 20, 5));
        images.setBackground(Color.WHITE);
        images.add(createItemPanel(outfit.getTop(), topImage));
        images.add(createItemPanel(outfit.getBottom(), bottomImage));
        images.add(createItemPanel(outfit.getFootwear(), footwearImage));
        add(images);

        add(Box.createVerticalStrut(10));

        JLabel score = new JLabel("Score: " + outfit.getScore());
        score.setFont(new Font("Arial", Font.BOLD, 15));
        score.setHorizontalAlignment(SwingConstants.CENTER);
        score.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(score);

        add(Box.createVerticalStrut(8));

        JLabel description = new JLabel(
                "<html><center>"
                        + "Top: " + outfit.getTop().getName() + "<br>"
                        + "Bottom: " + outfit.getBottom().getName() + "<br>"
                        + "Footwear: " + outfit.getFootwear().getName()
                        + "</center></html>");

        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(description);
    }

    private String getStyle() {
        if (outfit.getTop() != null && outfit.getTop().getStyle() != null)
            return outfit.getTop().getStyle().toString();

        if (outfit.getBottom() != null && outfit.getBottom().getStyle() != null)
            return outfit.getBottom().getStyle().toString();

        if (outfit.getFootwear() != null && outfit.getFootwear().getStyle() != null)
            return outfit.getFootwear().getStyle().toString();

        return "Unknown";
    }

    private JPanel createItemPanel(ClothingItem item, JLabel image) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        if (item != null) {
            image.setIcon(ImageLoader.load(item.getImagePath(), 80, 80));
            image.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel name = new JLabel(item.getName(), SwingConstants.CENTER);
            name.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(image);
            panel.add(Box.createVerticalStrut(5));
            panel.add(name);
        }

        return panel;
    }

    private void menu() {
        String[] options = {"View Outfit", "Delete Outfit", "Cancel"};

        int choice = JOptionPane.showOptionDialog(
                this,
                "Saved Outfit",
                "Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            new SavedOutfitViewDialog(user, outfit);
        } else if (choice == 1) {
            delete();
        }
    }

    private void delete() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete this outfit?",
                "Confirm",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            repository.delete(outfit.getId());
            user.getSavedOutfits().remove(outfit);

            JOptionPane.showMessageDialog(this, "Outfit deleted.");
            refresh.run();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public Outfit getOutfit() {
        return outfit;
    }
}