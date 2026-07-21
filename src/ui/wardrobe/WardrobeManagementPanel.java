package ui.wardrobe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import model.*;
import model.clothing.*;
import service.RecommendationEngine;
import exception.InvalidClothingException;
import ui.auth.LoginPanel;
import ui.outfit.*;

public class WardrobeManagementPanel extends JPanel {

    private JFrame frame;
    private User user;
    private RecommendationEngine engine=new RecommendationEngine();
    private JLabel scoreLabel;

    private JComboBox<Top> topSelector;
    private JComboBox<Bottom> bottomSelector;
    private JComboBox<Footwear> footwearSelector;

    public WardrobeManagementPanel(JFrame frame,User user){
        this.frame=frame;
        this.user=user;
        build();
    }


    private void build(){

        setLayout(new BorderLayout());
        setBackground(new Color(240,240,240));
        setBorder(new EmptyBorder(20,20,20,20));

        JPanel main=box();

        JPanel header=new JPanel(new BorderLayout());

        JLabel title=new JLabel("Welcome, "+user.getName());
        title.setFont(new Font("Arial",Font.BOLD,28));

        header.add(title,BorderLayout.WEST);
        header.add(buttons(),BorderLayout.EAST);

        main.add(header);
        main.add(new UserInfoPanel(user,this::calculateScore));
        main.add(wardrobe());
        main.add(score());

        JScrollPane scroll=new JScrollPane(main);

        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        add(scroll);
    }


    private JPanel wardrobe(){

        JPanel p=box();

        JLabel title=new JLabel("Your Wardrobe");
        title.setFont(new Font("Arial",Font.BOLD,22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        p.add(title);


        topSelector=new JComboBox<>(
            user.getWardrobe().getItems().stream()
            .filter(i->i instanceof Top)
            .map(i->(Top)i)
            .toArray(Top[]::new)
        );


        bottomSelector=new JComboBox<>(
            user.getWardrobe().getItems().stream()
            .filter(i->i instanceof Bottom)
            .map(i->(Bottom)i)
            .toArray(Bottom[]::new)
        );


        footwearSelector=new JComboBox<>(
            user.getWardrobe().getItems().stream()
            .filter(i->i instanceof Footwear)
            .map(i->(Footwear)i)
            .toArray(Footwear[]::new)
        );


        addCategory(p,"TOPS",Top.class,topSelector);
        addCategory(p,"BOTTOMS",Bottom.class,bottomSelector);
        addCategory(p,"FOOTWEAR",Footwear.class,footwearSelector);

        return p;
    }


    private void addCategory(
        JPanel p,
        String name,
        Class<?> type,
        JComboBox<?> box
    ){

        JLabel label=new JLabel(name);

        label.setFont(
            new Font("Arial",Font.BOLD,18)
        );

        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        p.add(label);


        ClothingItem item =
            user.getWardrobe()
            .getItems()
            .stream()
            .filter(type::isInstance)
            .findFirst()
            .orElse(null);


        ClothingCardPanel card = new ClothingCardPanel(item);

        p.add(card);


        box.setMaximumSize(
            new Dimension(820,30)
        );


        box.addActionListener(e -> {

            ClothingItem selected =
                (ClothingItem) box.getSelectedItem();

            card.updateItem(selected);
        });


        p.add(
            new JLabel("Choose "+name+":")
        );

        p.add(box);
    }


    private JPanel score(){

        JPanel p=box();

        JButton b=new JButton("Outfit Score");
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.addActionListener(e->calculateScore());


        scoreLabel=
            new JLabel("Recommendation Score: --");

        scoreLabel.setFont(
            new Font("Arial",Font.BOLD,18)
        );

        scoreLabel.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );


        JLabel info=new JLabel(
            "<html><div style='text-align:center;'>"+
            "Score evaluates:<br>"+
            "• color compatibility<br>"+
            "• preferred colors<br>"+
            "• style consistency<br>"+
            "• neutral versatility<br><br>"+
            "60-70 Excellent<br>"+
            "45-59 Strong<br>"+
            "30-44 Good<br>"+
            "15-29 Fair<br>"+
            "0-14 Poor"+
            "</div></html>"
        );


        info.setHorizontalAlignment(
            SwingConstants.CENTER
        );

        info.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );


        p.add(b);
        p.add(scoreLabel);
        p.add(info);

        return p;
    }


    private JPanel buttons(){

        JPanel p=new JPanel(
            new FlowLayout(
                FlowLayout.RIGHT
            )
        );


        JButton logout=new JButton("Logout");

        logout.addActionListener(
            e->logout()
        );


        p.add(new JButton("Upload"));
        p.add(new JButton("Remove"));
        p.add(logout);

        return p;
    }


    private JPanel box(){

        JPanel p=new JPanel();

        p.setLayout(
            new BoxLayout(
                p,
                BoxLayout.Y_AXIS
            )
        );

        p.setBackground(
            new Color(240,240,240)
        );

        return p;
    }


    private void calculateScore(){

        try{

            Outfit outfit=new Outfit(
                java.util.UUID.randomUUID().toString(),
                user.getId(),
                (Top)topSelector.getSelectedItem(),
                (Bottom)bottomSelector.getSelectedItem(),
                (Footwear)footwearSelector.getSelectedItem()
            );


            int score =
                engine.getOutfitScorer()
                .scoreOutfit(
                    outfit,
                    user
                );


            scoreLabel.setText(
                "Recommendation Score: "+score
            );


        }catch(InvalidClothingException e){

            JOptionPane.showMessageDialog(
                this,
                e.getMessage()
            );
        }
    }


    private void logout(){

        frame.setExtendedState(JFrame.NORMAL);
        frame.setSize(500,400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.setContentPane(
            new LoginPanel(frame)
        );

        frame.revalidate();
        frame.repaint();
    }
}