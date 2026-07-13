package ui.tests;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

import model.Outfit;
import model.OutfitOptions;
import model.User;
import model.clothing.ClothingItem;

import util.ImageLoader;
import util.ColorMatcher;

public class OutfitDisplayUI {

    private User user;
    private Outfit outfit;
    private OutfitOptions options;

    public OutfitDisplayUI(User user, OutfitOptions options) {

        this.user = user;
        this.options = options;
    }

    public void show() {

        JFrame frame = new JFrame("Clothing Capsule Wardrobe");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(900,800);

        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.setBackground(new Color(240, 240, 240));

        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Clothing Capsule Wardrobe");

        title.setFont(new Font("Arial", Font.BOLD, 28));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel userInfo = new JLabel(
                "User: "
                + user.getName()
                + " | Style: "
                + user.getStylesProfile().getStyle()
                + " | Preference: "
                + user.getStylesProfile().getColorPreference()
        );

        userInfo.setFont(
            new Font(
                "Arial",
                Font.PLAIN,
                18
            )
        );

        userInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(title);

        mainPanel.add(
            Box.createVerticalStrut(10)
        );

        mainPanel.add(userInfo);


        mainPanel.add(
            Box.createVerticalStrut(30)
        );


        if(options != null) {

            String currentStyle = "Unknown";

            if(!options.getTops().isEmpty()) {
                currentStyle =
                    options.getTops()
                            .get(0)
                            .getStyle()
                            .toString();
            }

            else if(!options.getBottoms().isEmpty()) {
                currentStyle =
                    options.getBottoms()
                            .get(0)
                            .getStyle().toString();
            }

            else if(!options.getFootwear().isEmpty()) {
                currentStyle =
                    options.getFootwear()
                            .get(0)
                            .getStyle().toString();
            }

        JLabel optionTitle = new JLabel("Available Outfit Options - "+ currentStyle);

        optionTitle.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                22
            )
        );

        optionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(optionTitle);

            mainPanel.add(Box.createVerticalStrut(20));

            mainPanel.add(
                createCategoryPanel(
                    "TOPS",
                    options.getTops()
                )
            );

            mainPanel.add(Box.createVerticalStrut(20));

            mainPanel.add(
                createCategoryPanel(
                    "BOTTOMS",
                    options.getBottoms()
                )
            );

            mainPanel.add(Box.createVerticalStrut(20));

            mainPanel.add(
                createCategoryPanel(
                    "FOOTWEAR",
                    options.getFootwear()
                )
            );

        }

        else if(outfit != null) {

            mainPanel.add(createCard(outfit.getTop()));
            
            mainPanel.add(Box.createVerticalStrut(20));

            mainPanel.add(createCard(outfit.getBottom()));

            mainPanel.add(Box.createVerticalStrut(20));

            mainPanel.add(createCard(outfit.getFootwear()));

        }


        JScrollPane mainScrollPane = new JScrollPane(mainPanel);

        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        frame.add(mainScrollPane);

        frame.setVisible(true);

    }


    private JPanel createCategoryPanel(String category, List<? extends ClothingItem> items) {

        JPanel container = new JPanel(new BorderLayout());

        container.setBackground(Color.WHITE);

        container.setBorder(
            BorderFactory.createLineBorder(
                new Color(
                    210,
                    210,
                    210
                )
            )
        );

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


        JPanel clothingPanel = new JPanel();

        clothingPanel.setLayout(new BoxLayout(clothingPanel,BoxLayout.Y_AXIS));

        clothingPanel.setBackground(Color.WHITE);

        for(ClothingItem item : items) {

            clothingPanel.add(createCard(item));

            clothingPanel.add(Box.createVerticalStrut(15));

        }

        container.add(categoryLabel, BorderLayout.NORTH);


        if(items.size() > 1) {

            JScrollPane scrollPane = new JScrollPane(clothingPanel);

            scrollPane.setPreferredSize(new Dimension( 820, 300));

            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            scrollPane.getVerticalScrollBar().setUnitIncrement(16);

            container.add(scrollPane, BorderLayout.CENTER);
        }

        else {

            container.add(clothingPanel, BorderLayout.CENTER);
        }

        return container;
    }

    private JPanel createCard(ClothingItem item) {

        ImageIcon originalIcon = ImageLoader.load(item.getImagePath());

        int maxImageWidth = 180;

        int maxImageHeight = 180;

        int originalWidth = originalIcon.getIconWidth();

        int originalHeight = originalIcon.getIconHeight();

        double scale = Math.min((double) maxImageWidth / originalWidth, (double) maxImageHeight / originalHeight);

        int scaledWidth = (int)(originalWidth * scale);

        int scaledHeight = (int)(originalHeight * scale);
        
        Image scaledImage =
            originalIcon
                .getImage()
                .getScaledInstance(
                    scaledWidth,
                    scaledHeight,
                    Image.SCALE_SMOOTH
                );

        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));

        imageLabel.setPreferredSize(new Dimension(scaledWidth, scaledHeight));

        JPanel card = new JPanel(new BorderLayout(30, 10));

        card.setBackground(Color.WHITE);

        card.setBorder(
            BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(
                            new Color(
                                    210,
                                    210,
                                    210
                            )
                    ),
                    new EmptyBorder(
                            20,
                            20,
                            20,
                            20
                    )
            )
        );

        card.setPreferredSize(new Dimension(820, scaledHeight + 60));

        card.setMaximumSize(new Dimension(820,scaledHeight + 60));

        JPanel info = new JPanel();

        info.setBackground(Color.WHITE);

        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel(item.getName());

        nameLabel.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                26
            )
        );

        JLabel brandLabel =
            new JLabel(
                "Brand: "
                + item.getBrand()
            );



        JLabel colorLabel =
            new JLabel(
                "Color: "
                + item.getColor()
            );



        JLabel colorCategoryLabel =
            new JLabel(
                "Color Category: "
                + ColorMatcher.getColorCategory(
                        item.getColor()
                )
            );


        JLabel styleLabel =
            new JLabel(
                "Style: "
                + item.getStyle()
            );

        Font detailFont =
            new Font(
                "Arial",
                Font.PLAIN,
                18
            );

        brandLabel.setFont(detailFont);

        colorLabel.setFont(detailFont);

        colorCategoryLabel.setFont(detailFont);

        styleLabel.setFont(detailFont);

        info.add(nameLabel);

        info.add(Box.createVerticalStrut(10));

        info.add(brandLabel);

        info.add(colorLabel);

        info.add(colorCategoryLabel);

        info.add(styleLabel);

        card.add(imageLabel, BorderLayout.WEST);
    
        card.add(info, BorderLayout.CENTER);

        return card;
        
    }
}