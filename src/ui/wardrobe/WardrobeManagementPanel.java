package ui.wardrobe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import model.*;
import model.clothing.*;
import service.*;
import exception.InvalidClothingException;
import ui.auth.LoginPanel;
import ui.outfit.*;

public class WardrobeManagementPanel extends JPanel {

    private JFrame frame;
    private User user;

    private RecommendationEngine engine=new RecommendationEngine();
    private WardrobeService service=new WardrobeService();

    private JLabel scoreLabel;

    private JComboBox<Top> topSelector;
    private JComboBox<Bottom> bottomSelector;
    private JComboBox<Footwear> footwearSelector;

    private ClothingItem selectedItem;


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

        JLabel title=new JLabel(
                "Welcome, "+user.getName());

        title.setFont(
                new Font("Arial",Font.BOLD,28));

        header.add(title,BorderLayout.WEST);
        header.add(buttons(),BorderLayout.EAST);

        main.add(header);

        main.add(
                new UserInfoPanel(
                        user,
                        this::refresh));

        main.add(wardrobe());
        main.add(score());


        JScrollPane scroll=new JScrollPane(main);
        scroll.getVerticalScrollBar()
                .setUnitIncrement(16);

        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scroll);
    }


    private JPanel wardrobe(){

        JPanel p=box();

        p.add(new JLabel(
                "Your Wardrobe ("+
                user.getStylesProfile()
                .getClothingStyle()+")"));


        topSelector=new JComboBox<>(getTops());
        bottomSelector=new JComboBox<>(getBottoms());
        footwearSelector=new JComboBox<>(getFootwear());


        addCategory(p,"TOPS",topSelector);
        addCategory(p,"BOTTOMS",bottomSelector);
        addCategory(p,"FOOTWEAR",footwearSelector);


        return p;
    }


    private Top[] getTops(){

        return user.getWardrobe()
                .getItems()
                .stream()
                .filter(i->i instanceof Top)
                .map(i->(Top)i)
                .filter(this::matchesStyle)
                .toArray(Top[]::new);

    }


    private Bottom[] getBottoms(){

        return user.getWardrobe()
                .getItems()
                .stream()
                .filter(i->i instanceof Bottom)
                .map(i->(Bottom)i)
                .filter(this::matchesStyle)
                .toArray(Bottom[]::new);

    }


    private Footwear[] getFootwear(){

        return user.getWardrobe()
                .getItems()
                .stream()
                .filter(i->i instanceof Footwear)
                .map(i->(Footwear)i)
                .filter(this::matchesStyle)
                .toArray(Footwear[]::new);

    }


    private boolean matchesStyle(ClothingItem item){

        return item.getStyle()
                ==user.getStylesProfile()
                .getClothingStyle();

    }


    private void addCategory(
            JPanel p,
            String name,
            JComboBox<?> box){

        p.add(new JLabel(name));


        ClothingItem item=
                box.getItemCount()>0?
                (ClothingItem)box.getItemAt(0):
                null;


        ClothingCardPanel card=
                new ClothingCardPanel(item);


        box.setMaximumSize(
                new Dimension(820,30));


        box.setPreferredSize(
                new Dimension(820,30));


        box.addActionListener(e->{

            selectedItem=
                    (ClothingItem)
                    box.getSelectedItem();

            if(selectedItem!=null)
                card.updateItem(selectedItem);

        });


        p.add(card);
        p.add(box);

    }


    private JPanel score(){

        JPanel p=box();

        JButton button=
                new JButton("Outfit Score");


        scoreLabel=
                new JLabel(
                        "Recommendation Score: --");


        button.addActionListener(
                e->calculateScore());


        p.add(button);
        p.add(scoreLabel);


        p.add(new JLabel(
            "<html><center>"+
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
            "</center></html>"));

        return p;
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


    private void upload(){

        frame.setContentPane(
                new UploadClothingPanel(
                        frame,user));

        refreshFrame();

    }


    private void edit(){

        if(selectedItem==null){

            JOptionPane.showMessageDialog(
                    this,
                    "Select clothing first.");

            return;
        }


        frame.setContentPane(
                new EditClothingPanel(
                        frame,user,selectedItem));

        refreshFrame();

    }


    private void remove(){

        if(selectedItem==null)return;


        try{

            service.removeClothingItem(
                    selectedItem,user);

            refresh();

        }catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }

    }


    private void calculateScore(){

        try{

            if(topSelector.getSelectedItem()==null||
               bottomSelector.getSelectedItem()==null||
               footwearSelector.getSelectedItem()==null)

                throw new InvalidClothingException(
                        "Complete outfit required.");


            Outfit outfit=new Outfit(
                    java.util.UUID.randomUUID().toString(),
                    user.getId(),
                    (Top)topSelector.getSelectedItem(),
                    (Bottom)bottomSelector.getSelectedItem(),
                    (Footwear)footwearSelector.getSelectedItem());


            scoreLabel.setText(
                    "Recommendation Score: "+
                    engine.scoreOutfit(outfit,user));


        }catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }

    }


    private void refresh(){

        frame.setContentPane(
                new WardrobeManagementPanel(
                        frame,user));

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


    private void logout(){

        frame.setContentPane(
                new LoginPanel(frame));

        refreshFrame();

    }

}