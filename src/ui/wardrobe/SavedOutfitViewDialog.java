package ui.wardrobe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import model.*;
import model.clothing.*;
import util.ImageLoader;
import util.ColorMatcher;

public class SavedOutfitViewDialog extends JDialog {

    public SavedOutfitViewDialog(User user, Outfit outfit){

        setTitle("Saved Outfit");
        setModal(true);
        setSize(750,450);
        setLocationRelativeTo(null);

        JPanel main=new JPanel();
        main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
        main.setBorder(new EmptyBorder(20,20,20,20));

        JLabel title=new JLabel("Saved Outfit");
        title.setFont(new Font("Arial",Font.BOLD,24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        main.add(title);
        main.add(Box.createVerticalStrut(15));

        JPanel items=new JPanel(new GridLayout(1,3,20,20));

        items.add(itemPanel(outfit.getTop()));
        items.add(itemPanel(outfit.getBottom()));
        items.add(itemPanel(outfit.getFootwear()));

        main.add(items);
        main.add(Box.createVerticalStrut(15));

        JLabel score=new JLabel("Score: "+outfit.getScore());
        score.setFont(new Font("Arial",Font.BOLD,16));
        score.setAlignmentX(Component.CENTER_ALIGNMENT);

        main.add(score);

        JButton close=new JButton("Close");
        close.setAlignmentX(Component.CENTER_ALIGNMENT);
        close.addActionListener(e->dispose());

        main.add(Box.createVerticalStrut(10));
        main.add(close);

        add(main);
        setVisible(true);
    }


    private JPanel itemPanel(ClothingItem item){

        JPanel p=new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        if(item!=null){

            JLabel image=new JLabel(
                    ImageLoader.load(
                            item.getImagePath(),
                            150,
                            150));

            image.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel name=new JLabel(item.getName());
            name.setAlignmentX(Component.CENTER_ALIGNMENT);


            String details="<html>"+
                    "Brand: "+item.getBrand()+"<br>"+
                    "Color: "+item.getColor()+"<br>"+
                    "Color Palette: "+
                    ColorMatcher.getColorCategory(item.getColor())+
                    "<br>"+
                    "Style: "+item.getStyle()+"<br>";


            if(item instanceof Top top)
                details+="Sleeve: "+top.getSleeveType()+"<br>";

            else if(item instanceof Bottom bottom)
                details+="Fit: "+bottom.getFitType()+"<br>";

            else if(item instanceof Footwear footwear)
                details+="Type: "+footwear.getType()+"<br>";

            JLabel info=new JLabel(details+"</html>");
            info.setAlignmentX(Component.CENTER_ALIGNMENT);

            p.add(image);
            p.add(name);
            p.add(info);
        }

        return p;
    }
}