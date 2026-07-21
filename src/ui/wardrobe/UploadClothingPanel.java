package ui.wardrobe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.UUID;

import model.*;
import model.clothing.*;
import service.WardrobeService;
import exception.InvalidClothingException;

public class UploadClothingPanel extends JPanel {

    private JFrame frame;
    private User user;
    private WardrobeService service=new WardrobeService();

    private JTextField nameField,brandField;
    private JTextField sleeveField,fitField,footwearField;

    private JComboBox<String> categoryBox,colorBox;
    private JComboBox<ClothingStyle> styleBox;

    private JLabel imageLabel;
    private String imagePath;


    public UploadClothingPanel(JFrame frame,User user){
        this.frame=frame;
        this.user=user;
        build();
    }


    private void build(){

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20,20,20,20));

        JPanel p=new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));

        p.add(new JLabel("Upload Clothing Item"));

        nameField=new JTextField();
        brandField=new JTextField();

        sleeveField=new JTextField();
        fitField=new JTextField();
        footwearField=new JTextField();


        categoryBox=new JComboBox<>(
                new String[]{"TOP","BOTTOM","FOOTWEAR"});


        colorBox=new JComboBox<>(new String[]{
                "Black","White","Grey","Gray",
                "Beige","Brown","Navy",
                "Red","Orange","Yellow","Pink",
                "Blue","Green","Purple","Olive"
        });


        styleBox=new JComboBox<>(
                ClothingStyle.values()
        );


        addField(p,"Name:",nameField);
        addField(p,"Brand:",brandField);
        addField(p,"Category:",categoryBox);
        addField(p,"Color:",colorBox);
        addField(p,"Style:",styleBox);
        addField(p,"Sleeve Type:",sleeveField);
        addField(p,"Fit Type:",fitField);
        addField(p,"Footwear Type:",footwearField);


        JButton image=new JButton("Choose Image");
        imageLabel=new JLabel("No image");


        image.addActionListener(e->chooseImage());


        JButton upload=new JButton("Upload");
        JButton back=new JButton("Back");


        upload.addActionListener(e->uploadItem());
        back.addActionListener(e->back());


        p.add(image);
        p.add(imageLabel);
        p.add(upload);
        p.add(back);


        categoryBox.addActionListener(e->updateFields());


        add(p);
        updateFields();

    }


    private void addField(JPanel p,String name,Component c){

        p.add(new JLabel(name));
        c.setMaximumSize(new Dimension(400,30));
        p.add(c);

    }


    private void chooseImage(){

        JFileChooser chooser=new JFileChooser();

        if(chooser.showOpenDialog(this)
                ==JFileChooser.APPROVE_OPTION){

            File file=chooser.getSelectedFile();

            imagePath=file.getAbsolutePath();
            imageLabel.setText(file.getName());

        }
    }


    private void uploadItem(){

        try{

            if(nameField.getText().trim().isEmpty()
                    ||brandField.getText().trim().isEmpty()
                    ||imagePath==null)

                throw new InvalidClothingException(
                        "Name, brand, and image required."
                );


            String id=UUID.randomUUID().toString();

            String category=
                    categoryBox.getSelectedItem().toString();


            ClothingStyle style=
                    (ClothingStyle)styleBox.getSelectedItem();


            ClothingItem item;


            if(category.equals("TOP"))

                item=new Top(
                        id,
                        user.getId(),
                        nameField.getText(),
                        colorBox.getSelectedItem().toString(),
                        brandField.getText(),
                        imagePath,
                        style,
                        sleeveField.getText()
                );


            else if(category.equals("BOTTOM"))

                item=new Bottom(
                        id,
                        user.getId(),
                        nameField.getText(),
                        colorBox.getSelectedItem().toString(),
                        brandField.getText(),
                        imagePath,
                        style,
                        fitField.getText()
                );


            else

                item=new Footwear(
                        id,
                        user.getId(),
                        nameField.getText(),
                        colorBox.getSelectedItem().toString(),
                        brandField.getText(),
                        imagePath,
                        style,
                        footwearField.getText()
                );


            service.addClothingItem(item,user);


            JOptionPane.showMessageDialog(
                    this,
                    "Upload successful."
            );


            back();


        }catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );

        }

    }


    private void updateFields(){

        String c = categoryBox.getSelectedItem().toString();

        sleeveField.setEditable(c.equals("TOP"));
        fitField.setEditable(c.equals("BOTTOM"));
        footwearField.setEditable(c.equals("FOOTWEAR"));

    }


    private void back(){

        frame.setContentPane(
                new WardrobeManagementPanel(frame,user)
        );

        frame.revalidate();
        frame.repaint();

    }

}