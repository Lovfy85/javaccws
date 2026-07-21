package ui.outfit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import model.clothing.ClothingItem;
import util.ColorMatcher;
import util.ImageLoader;

public class ClothingCardPanel extends JPanel{

    private JLabel image=new JLabel();
    private JLabel name=new JLabel();
    private JLabel brand=new JLabel();
    private JLabel color=new JLabel();
    private JLabel category=new JLabel();
    private JLabel style=new JLabel();


    public ClothingCardPanel(ClothingItem item){
        build();
        updateItem(item);
    }


    private void build(){

        setLayout(new BorderLayout(20,10));
        setBackground(Color.WHITE);

        setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(210,210,210)),
                new EmptyBorder(15,15,15,15)
            )
        );


        image.setPreferredSize(
                new Dimension(170,170));


        JPanel info=new JPanel(
                new GridLayout(0,1));

        info.setBackground(Color.WHITE);


        name.setFont(
                new Font("Arial",Font.BOLD,24));


        Font f=new Font(
                "Arial",
                Font.PLAIN,
                16);

        brand.setFont(f);
        color.setFont(f);
        category.setFont(f);
        style.setFont(f);


        info.add(name);
        info.add(brand);
        info.add(color);
        info.add(category);
        info.add(style);


        add(image,BorderLayout.WEST);
        add(info,BorderLayout.CENTER);


        setPreferredSize(
                new Dimension(820,220));

        setMaximumSize(
                new Dimension(820,220));

    }


    public void updateItem(ClothingItem item){

        if(item==null){

            image.setIcon(null);

            name.setText(
                    "No matching clothing available");

            brand.setText("");
            color.setText("");
            category.setText("");
            style.setText("");

            return;
        }


        ImageIcon icon=
                ImageLoader.load(
                        item.getImagePath());


        image.setIcon(
                new ImageIcon(
                    icon.getImage()
                    .getScaledInstance(
                        160,
                        160,
                        Image.SCALE_SMOOTH)
                )
        );


        name.setText(
                item.getName());

        brand.setText(
                "Brand: "+item.getBrand());

        color.setText(
                "Color: "+item.getColor());

        category.setText(
                "Color Category: "+
                ColorMatcher.getColorCategory(
                        item.getColor()));

        style.setText(
                "Style: "+item.getStyle());


        revalidate();
        repaint();

    }
}