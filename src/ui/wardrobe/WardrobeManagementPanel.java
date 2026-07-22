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


        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        JPanel header = new JPanel(new BorderLayout());

        JLabel title =
                new JLabel("Welcome, "+user.getName());

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28));

        header.add(
                title,
                BorderLayout.WEST);

        header.add(
                buttons(),
                BorderLayout.EAST);

        main.add(header);

        main.add(
                Box.createVerticalStrut(20));

        JPanel center =
                new JPanel(
                        new BorderLayout(50,0));

        JPanel clothing =
                wardrobe();


        JPanel clothingWrapper =
            new JPanel(
                new GridBagLayout());


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.anchor =
                GridBagConstraints.CENTER;


        gbc.weightx = 1.0;
        gbc.weighty = 1.0;


        gbc.fill =
                GridBagConstraints.NONE;


        clothingWrapper.add(
                clothing,
                gbc);


        center.add(
                clothingWrapper,
                BorderLayout.CENTER);



        JPanel savedArea =
                new JPanel(
                        new BorderLayout(0,0));



        JButton toggle =
                new JButton("<");


        toggle.setPreferredSize(
                new Dimension(
                        60,
                        40));

    
        JScrollPane savedScroll = savedOutfits();
        
        savedScroll.setPreferredSize(new Dimension(420, 520));

        savedArea.add(toggle, BorderLayout.WEST);
        savedArea.add(savedScroll, BorderLayout.CENTER);


        toggle.addActionListener(e -> {

            boolean visible =
                    savedScroll.isVisible();


            savedScroll.setVisible(!visible);


            toggle.setText(
                    visible ?
                    ">" :
                    "<");


            savedArea.revalidate();
            savedArea.repaint();

        });

        center.add(
                savedArea,
                BorderLayout.EAST);

        main.add(center);

        main.add(
                Box.createVerticalStrut(20));

        main.add(score());

        main.add(
                Box.createVerticalStrut(20));

        main.add(
                new UserInfoPanel(
                        user,
                        this::refresh));

        JScrollPane appScroll =
                new JScrollPane(main);


        appScroll.getVerticalScrollBar()
                .setUnitIncrement(16);

        appScroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(appScroll);

    }


    private JPanel wardrobe(){

        JPanel p=box();
    
        p.setAlignmentX(Component.CENTER_ALIGNMENT);

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


    private JScrollPane savedOutfits(){

        JPanel container = new JPanel(new BorderLayout());

        container.setBackground(Color.WHITE);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setBackground(Color.WHITE);

        if(user.getSavedOutfits().isEmpty()){

            JLabel empty = new JLabel("No saved outfits yet.");
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);

            list.add(Box.createVerticalStrut(15));
            list.add(empty);
        }
        else{

            for(Outfit outfit : user.getSavedOutfits()){

                OutfitCardPanel card =
                        new OutfitCardPanel(
                                outfit,
                                user,
                                this::refresh);

                card.setPreferredSize(
                        new Dimension(320,260));

                card.setMaximumSize(
                        new Dimension(320,260));

                card.setAlignmentX(
                        Component.CENTER_ALIGNMENT);

                JPanel wrapper =
                        new JPanel(
                                new FlowLayout(
                                        FlowLayout.CENTER));

                wrapper.setBackground(Color.WHITE);

                wrapper.add(card);

                list.add(wrapper);
                list.add(Box.createVerticalStrut(20));
            }
        }

        container.add(list, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(container);

        scroll.setBorder(null);

        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scroll.setBorder(
            BorderFactory.createTitledBorder("Saved Outfits")
        );

        scroll.getVerticalScrollBar().setUnitIncrement(20);
        scroll.getVerticalScrollBar().setBlockIncrement(100);

        return scroll;
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
            JOptionPane.showMessageDialog(
                    this,
                    "Calculate an outfit first.");
            return;
        }

        try{

            boolean exists =
                    outfitRepository.exists(
                    user.getId(),
                    generatedOutfit.getTop().getId(),
                    generatedOutfit.getBottom().getId(),
                    generatedOutfit.getFootwear().getId());


            if(exists){
                JOptionPane.showMessageDialog(
                        this,
                        "This outfit is already saved.");
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
                    "Database error: "+e.getMessage());

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