package ui.wardrobe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import model.*;
import model.clothing.*;
import service.WardrobeService;

public class EditClothingPanel extends JPanel {

    private JFrame frame;
    private User user;
    private ClothingItem item;

    private WardrobeService service=new WardrobeService();

    private JTextField nameField,brandField,typeField;
    private JComboBox<String> colorBox;
    private JComboBox<ClothingStyle> styleBox;


    public EditClothingPanel(JFrame frame,User user,ClothingItem item){

        this.frame=frame;
        this.user=user;
        this.item=item;

        build();
    }


    private void build(){

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20,20,20,20));

        JPanel p=new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));


        nameField=new JTextField(item.getName());
        brandField=new JTextField(item.getBrand());


        colorBox=new JComboBox<>(new String[]{
            "Black","White","Grey","Gray",
            "Blue","Green","Red",
            "Orange","Yellow","Pink",
            "Purple","Brown","Navy",
            "Beige","Olive"
        });

        colorBox.setSelectedItem(
                item.getColor()
        );


        styleBox=new JComboBox<>(
                ClothingStyle.values()
        );

        styleBox.setSelectedItem(
                item.getStyle()
        );


        addField(p,"Name",nameField);
        addField(p,"Brand",brandField);
        addField(p,"Color",colorBox);
        addField(p,"Style",styleBox);


        typeField=new JTextField();


        if(item instanceof Top top){

            typeField.setText(
                    top.getSleeveType()
            );

            addField(
                    p,
                    "Sleeve",
                    typeField
            );

        }

        else if(item instanceof Bottom bottom){

            typeField.setText(
                    bottom.getFitType()
            );

            addField(
                    p,
                    "Fit",
                    typeField
            );

        }

        else if(item instanceof Footwear footwear){

            typeField.setText(
                    footwear.getType()
            );

            addField(
                    p,
                    "Footwear",
                    typeField
            );
        }


        JButton save=new JButton("Save");
        JButton back=new JButton("Back");


        save.addActionListener(e->update());
        back.addActionListener(e->back());


        p.add(save);
        p.add(back);


        add(p);

    }


    private void addField(
            JPanel p,
            String name,
            Component c){

        p.add(new JLabel(name));

        c.setMaximumSize(
                new Dimension(400,30)
        );

        p.add(c);

    }


    private void update(){

        try{

            item.setName(
                    nameField.getText()
            );

            item.setBrand(
                    brandField.getText()
            );

            item.setColor(
                    colorBox.getSelectedItem()
                    .toString()
            );

            item.setStyle(
                    (ClothingStyle)
                    styleBox.getSelectedItem()
            );


            if(item instanceof Top top)

                top.setSleeveType(
                        typeField.getText()
                );


            else if(item instanceof Bottom bottom)

                bottom.setFitType(
                        typeField.getText()
                );


            else if(item instanceof Footwear footwear)

                footwear.setType(
                        typeField.getText()
                );


            service.updateClothingItem(item);


            JOptionPane.showMessageDialog(
                    this,
                    "Updated successfully."
            );


            back();


        }catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );

        }

    }


    private void back(){

        frame.setContentPane(
                new WardrobeManagementPanel(
                        frame,
                        user
                )
        );

        frame.revalidate();
        frame.repaint();

    }

}