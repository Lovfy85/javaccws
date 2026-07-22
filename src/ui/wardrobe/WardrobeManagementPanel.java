package ui.wardrobe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

import model.*;
import model.clothing.*;
import service.*;
import repository.OutfitRepository;
import exception.InvalidClothingException;
import ui.auth.LoginPanel;
import ui.outfit.ClothingCardPanel;
import ui.outfit.UserInfoPanel;


public class WardrobeManagementPanel extends JPanel {

    private JFrame frame;
    private User user;

    private RecommendationEngine engine=new RecommendationEngine();
    private WardrobeService service=new WardrobeService();
    private OutfitRepository outfitRepository=new OutfitRepository();

    private JComboBox<Top> topSelector;
    private JComboBox<Bottom> bottomSelector;
    private JComboBox<Footwear> footwearSelector;

    private JLabel scoreLabel;

    private ClothingItem selectedItem;
    private Outfit generatedOutfit;

    private boolean showDescription=false;
    private JPanel descriptionPanel;


    public WardrobeManagementPanel(JFrame frame,User user){
        this.frame=frame;
        this.user=user;
        build();
    }


    private void build(){

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20,20,20,20));

        JPanel main=box();

        JPanel header=new JPanel(new BorderLayout());

        JLabel title=new JLabel("Welcome, "+user.getName());
        title.setFont(new Font("Arial",Font.BOLD,28));

        header.add(title,BorderLayout.WEST);
        header.add(buttons(),BorderLayout.EAST);

        main.add(header);
        main.add(wardrobe());
        main.add(savedOutfits());

        main.add(new UserInfoPanel(user,this::refresh));

        main.add(score());


        JScrollPane scroll=new JScrollPane(main);

        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scroll);
    }



    private JPanel wardrobe(){

        JPanel p=box();

        JLabel label=new JLabel(
                "Current Clothing Style: "+
                user.getStylesProfile().getClothingStyle());

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(label);


        topSelector=new JComboBox<>(getTops());
        bottomSelector=new JComboBox<>(getBottoms());
        footwearSelector=new JComboBox<>(getFootwear());


        addCategory(p,"TOPS",topSelector);
        addCategory(p,"BOTTOMS",bottomSelector);
        addCategory(p,"FOOTWEAR",footwearSelector);

        return p;
    }



    private Top[] getTops(){

        return user.getWardrobe().getItems()
                .stream()
                .filter(i->i instanceof Top)
                .map(i->(Top)i)
                .filter(this::matchesStyle)
                .toArray(Top[]::new);

    }


    private Bottom[] getBottoms(){

        return user.getWardrobe().getItems()
                .stream()
                .filter(i->i instanceof Bottom)
                .map(i->(Bottom)i)
                .filter(this::matchesStyle)
                .toArray(Bottom[]::new);

    }


    private Footwear[] getFootwear(){

        return user.getWardrobe().getItems()
                .stream()
                .filter(i->i instanceof Footwear)
                .map(i->(Footwear)i)
                .filter(this::matchesStyle)
                .toArray(Footwear[]::new);

    }


    private boolean matchesStyle(ClothingItem item){

        return item.getStyle()
                ==
                user.getStylesProfile()
                .getClothingStyle();

    }


    private void addCategory(
            JPanel p,
            String name,
            JComboBox<?> box){

        JLabel label=new JLabel(name);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        p.add(label);


        ClothingItem item=
                box.getItemCount()>0?
                (ClothingItem)box.getItemAt(0):
                null;


        ClothingCardPanel card=
                new ClothingCardPanel(item);


        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        box.setAlignmentX(Component.CENTER_ALIGNMENT);


        Dimension cardSize=card.getPreferredSize();


        box.setPreferredSize(
                new Dimension(
                        cardSize.width,
                        35));


        box.setMaximumSize(
                new Dimension(
                        cardSize.width,
                        35));


        box.addActionListener(e->{

            selectedItem=
                    (ClothingItem)box.getSelectedItem();


            if(selectedItem!=null)
                card.updateItem(selectedItem);

        });


        p.add(card);

        p.add(Box.createVerticalStrut(10));

        p.add(box);

        p.add(Box.createVerticalStrut(20));

    }


    private JPanel savedOutfits(){

        JPanel container=box();

        JToggleButton toggle=
                new JToggleButton("Saved Outfits ▼");

        toggle.setAlignmentX(
                Component.CENTER_ALIGNMENT);


        JPanel cards=
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                15,
                                15));

        cards.setBackground(Color.WHITE);


        if(user.getSavedOutfits().isEmpty()){

            cards.add(
                    new JLabel(
                            "No saved outfits yet."));

        }else{


            for(Outfit outfit:user.getSavedOutfits()){


                JPanel wrapper=
                        new JPanel(
                                new BorderLayout());


                JLabel style=
                        new JLabel(
                                "Style: "+
                                outfit.getTop()
                                .getStyle());


                style.setHorizontalAlignment(
                        SwingConstants.CENTER);


                wrapper.add(
                        style,
                        BorderLayout.NORTH);


                wrapper.add(
                        new OutfitCardPanel(outfit),
                        BorderLayout.CENTER);


                cards.add(wrapper);

            }

        }


        cards.setVisible(false);


        toggle.addActionListener(e->{


            boolean visible=
                    toggle.isSelected();


            cards.setVisible(visible);


            toggle.setText(
                    visible?
                    "Saved Outfits ▲":
                    "Saved Outfits ▼");


            revalidate();
            repaint();

        });



        container.add(toggle);

        container.add(
                Box.createVerticalStrut(10));


        container.add(cards);


        return container;

    }


    private JPanel score(){

        JPanel p=box();


        JPanel row=
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER));


        JButton calculate=
                new JButton(
                        "Outfit Score");


        JButton save=
                new JButton(
                        "Save Outfit");


        JButton info=
                new JButton(
                        "Show Info");


        scoreLabel=
                new JLabel(
                        "Recommendation Score: --");


        calculate.addActionListener(
                e->calculateScore());


        save.addActionListener(
                e->saveOutfit());


        info.addActionListener(e->{


            showDescription=
                    !showDescription;


            descriptionPanel.setVisible(
                    showDescription);


            info.setText(
                    showDescription?
                    "Hide Info":
                    "Show Info");

        });



        row.add(calculate);
        row.add(save);
        row.add(info);


        p.add(row);



        scoreLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18));


        scoreLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT);


        p.add(scoreLabel);



        descriptionPanel=
                new JPanel();


        JLabel description=
                new JLabel(
                "<html><center>"+
                "Score evaluates:<br><br>"+
                "• Color compatibility<br>"+
                "• Preferred colors<br>"+
                "• Style consistency<br>"+
                "• Neutral versatility<br><br>"+
                "60-70 Excellent<br>"+
                "45-59 Strong<br>"+
                "30-44 Good<br>"+
                "15-29 Fair<br>"+
                "0-14 Poor"+
                "</center></html>");



        descriptionPanel.add(description);

        descriptionPanel.setVisible(false);

        p.add(descriptionPanel);


        return p;

    }


    private void calculateScore(){

        try{


            if(topSelector.getSelectedItem()==null||
               bottomSelector.getSelectedItem()==null||
               footwearSelector.getSelectedItem()==null){

                throw new InvalidClothingException(
                        "Complete outfit required.");

            }



            generatedOutfit=
                    new Outfit(
                    java.util.UUID.randomUUID()
                    .toString(),
                    user.getId(),
                    (Top)topSelector.getSelectedItem(),
                    (Bottom)bottomSelector.getSelectedItem(),
                    (Footwear)footwearSelector.getSelectedItem());



            int score=
                    engine.scoreOutfit(
                            generatedOutfit,
                            user);



            generatedOutfit.setScore(score);



            scoreLabel.setText(
                    "Recommendation Score: "+
                    score);


        }catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }

    }

    private void saveOutfit(){

        if(generatedOutfit==null){
            JOptionPane.showMessageDialog(this,"Calculate an outfit first.");
            return;
        }

        try{

            boolean exists=outfitRepository.exists(
                    user.getId(),
                    generatedOutfit.getTop().getId(),
                    generatedOutfit.getBottom().getId(),
                    generatedOutfit.getFootwear().getId());

            if(exists){
                JOptionPane.showMessageDialog(this,"This outfit is already saved.");
                return;
            }

            outfitRepository.save(
                    generatedOutfit.getId(),
                    user.getId(),
                    generatedOutfit);

            user.addSavedOutfit(generatedOutfit);

            JOptionPane.showMessageDialog(
                    this,
                    "Outfit saved successfully.");

            refresh();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());
        }
    }


    private JPanel buttons(){

        JPanel p=new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT));

        JButton upload=new JButton("Upload");
        JButton edit=new JButton("Edit");
        JButton remove=new JButton("Remove");
        JButton logout=new JButton("Logout");

        upload.addActionListener(e->upload());
        edit.addActionListener(e->edit());
        remove.addActionListener(e->remove());
        logout.addActionListener(e->logout());

        p.add(upload);
        p.add(edit);
        p.add(remove);
        p.add(logout);

        return p;
    }


    private void refresh(){

        frame.setContentPane(
                new WardrobeManagementPanel(
                        frame,
                        user));

        refreshFrame();
    }


    private void refreshFrame(){

        frame.revalidate();
        frame.repaint();

    }


    private JPanel box(){

        JPanel p=new JPanel();

        p.setLayout(
                new BoxLayout(
                        p,
                        BoxLayout.Y_AXIS));

        return p;
    }


    private void upload(){

        JDialog dialog=new JDialog(
                frame,
                "Upload Clothing Item",
                true);


        dialog.setContentPane(
                new UploadClothingPanel(
                        frame,
                        user,
                        dialog));


        dialog.setSize(450,600);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);

    }


    private void edit(){

        if(selectedItem==null){
            JOptionPane.showMessageDialog(
                    this,
                    "Select clothing first.");
            return;
        }


        JDialog dialog=new JDialog(
                frame,
                "Edit Clothing Item",
                true);


        dialog.setContentPane(
                new EditClothingPanel(
                        frame,
                        user,
                        selectedItem,
                        dialog));


        dialog.setSize(450,500);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);

    }


    private void remove(){

        if(selectedItem==null)return;


        int confirm=JOptionPane.showConfirmDialog(
                this,
                "Remove this item?",
                "Confirm",
                JOptionPane.YES_NO_OPTION);


        if(confirm!=JOptionPane.YES_OPTION)return;


        try{

            service.removeClothingItem(
                    selectedItem,
                    user);

            refresh();

        }catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }

    }


    private void logout(){

        int confirm=JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);


        if(confirm!=JOptionPane.YES_OPTION)return;


        frame.setExtendedState(JFrame.NORMAL);
        frame.setResizable(false);
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);


        frame.setContentPane(
                new LoginPanel(frame));


        refreshFrame();

    }

}