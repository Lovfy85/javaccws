package ui.wardrobe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.UUID;
import javax.imageio.ImageIO;

import model.*;
import model.clothing.*;
import service.WardrobeService;
import exception.InvalidClothingException;

public class UploadClothingPanel extends JPanel {

    private JFrame frame;
    private JDialog dialog;
    private User user;

    private WardrobeService service=new WardrobeService();

    private JTextField nameField,brandField;
    private JTextField sleeveField,fitField,footwearField;

    private JComboBox<String> categoryBox,colorBox;
    private JComboBox<ClothingStyle> styleBox;

    private JLabel imageLabel;
    private JLabel sleeveLabel,fitLabel,footwearLabel;

    private String imagePath;


    public UploadClothingPanel(
            JFrame frame,
            User user,
            JDialog dialog){

        this.frame=frame;
        this.user=user;
        this.dialog=dialog;

        build();

    }


    private void build(){

        setLayout(new GridBagLayout());

        setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20));


        JPanel p=new JPanel();

        p.setLayout(
                new BoxLayout(
                        p,
                        BoxLayout.Y_AXIS));


        p.setAlignmentX(
                Component.CENTER_ALIGNMENT);


        JLabel title=
                new JLabel(
                        "Upload Clothing Item",
                        SwingConstants.CENTER);


        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24));


        title.setAlignmentX(
                Component.CENTER_ALIGNMENT);


        p.add(title);


        p.add(
                Box.createVerticalStrut(15));


        nameField=new JTextField();
        brandField=new JTextField();

        sleeveField=new JTextField();
        fitField=new JTextField();
        footwearField=new JTextField();


        categoryBox=new JComboBox<>(
                new String[]{
                        "TOP",
                        "BOTTOM",
                        "FOOTWEAR"
                });


        colorBox=new JComboBox<>(
                new String[]{
                "Black","White","Grey","Gray",
                "Beige","Brown","Navy",
                "Red","Orange","Yellow","Pink",
                "Blue","Green","Purple","Olive"
        });


        styleBox=new JComboBox<>(
                ClothingStyle.values());


        addField(p,"Name:",nameField);
        addField(p,"Brand:",brandField);
        addField(p,"Category:",categoryBox);
        addField(p,"Color:",colorBox);
        addField(p,"Style:",styleBox);


        sleeveLabel=
                new JLabel("Sleeve Type:");

        fitLabel=
                new JLabel("Fit Type:");

        footwearLabel=
                new JLabel("Footwear Type:");


        addField(
                p,
                sleeveLabel,
                sleeveField);

        addField(
                p,
                fitLabel,
                fitField);

        addField(
                p,
                footwearLabel,
                footwearField);


        JButton image=
                new JButton(
                        "Choose Image");


        imageLabel=
                new JLabel(
                        "No image",
                        SwingConstants.CENTER);


        imageLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT);


        image.addActionListener(
                e->chooseImage());


        JButton upload=
                new JButton(
                        "Upload");


        JButton cancel=
                new JButton(
                        "Cancel");


        upload.addActionListener(
                e->uploadItem());


        cancel.addActionListener(
                e->dialog.dispose());


        JPanel buttons=
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER));


        buttons.setAlignmentX(
                Component.CENTER_ALIGNMENT);


        buttons.add(upload);
        buttons.add(cancel);


        image.setAlignmentX(
                Component.CENTER_ALIGNMENT);


        p.add(image);
        p.add(imageLabel);
        p.add(buttons);


        categoryBox.addActionListener(
                e->updateFields());


        add(
                p,
                new GridBagConstraints());


        updateFields();

    }


    private void addField(
            JPanel p,
            String name,
            Component c){

        JLabel label=
                new JLabel(name);


        label.setAlignmentX(
                Component.CENTER_ALIGNMENT);


        c.setMaximumSize(
                new Dimension(
                        300,
                        30));


        ((JComponent) c).setAlignmentX(
                Component.CENTER_ALIGNMENT);


        p.add(label);
        p.add(c);

    }


    private void addField(
            JPanel p,
            JLabel label,
            Component c){

        label.setAlignmentX(
                Component.CENTER_ALIGNMENT);


        c.setMaximumSize(
                new Dimension(
                        300,
                        30));


        ((JComponent) c).setAlignmentX(
                Component.CENTER_ALIGNMENT);


        p.add(label);
        p.add(c);

    }

    private void chooseImage(){

        JFileChooser chooser=
                new JFileChooser();


        chooser.setFileFilter(
                new javax.swing.filechooser.FileNameExtensionFilter(
                "Images",
                "png",
                "jpg",
                "jpeg",
                "gif",
                "webp"));


        if(chooser.showOpenDialog(this)
                ==JFileChooser.APPROVE_OPTION){

            File file=
                    chooser.getSelectedFile();

            try{

                Image img=
                        ImageIO.read(file);


                img=img.getScaledInstance(
                        150,
                        150,
                        Image.SCALE_SMOOTH);


                imageLabel.setIcon(
                        new ImageIcon(img));


                imageLabel.setText(
                        file.getName());


                imagePath=
                        file.getAbsolutePath();


            }catch(Exception e){

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid image file.");

            }

        }

    }


    private void uploadItem(){

        try{

            if(nameField.getText().isBlank()
                    ||brandField.getText().isBlank()
                    ||imagePath==null)

                throw new InvalidClothingException(
                        "Name, brand, and image required.");


            String id=
                    UUID.randomUUID().toString();


            String category=
                    categoryBox
                    .getSelectedItem()
                    .toString();


            ClothingStyle style=
                    (ClothingStyle)
                    styleBox.getSelectedItem();


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
                        sleeveField.getText());


            else if(category.equals("BOTTOM"))

                item=new Bottom(
                        id,
                        user.getId(),
                        nameField.getText(),
                        colorBox.getSelectedItem().toString(),
                        brandField.getText(),
                        imagePath,
                        style,
                        fitField.getText());


            else

                item=new Footwear(
                        id,
                        user.getId(),
                        nameField.getText(),
                        colorBox.getSelectedItem().toString(),
                        brandField.getText(),
                        imagePath,
                        style,
                        footwearField.getText());


            service.addClothingItem(
                    item,
                    user);


            JOptionPane.showMessageDialog(
                    this,
                    "Upload successful.");


            dialog.dispose();


            frame.setContentPane(
                    new WardrobeManagementPanel(
                            frame,
                            user));


            frame.revalidate();
            frame.repaint();


        }catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }

    }


    private void updateFields(){

        String c=
                categoryBox
                .getSelectedItem()
                .toString();


        boolean top=
                c.equals("TOP");

        boolean bottom=
                c.equals("BOTTOM");

        boolean footwear=
                c.equals("FOOTWEAR");


        sleeveLabel.setVisible(top);
        sleeveField.setVisible(top);

        fitLabel.setVisible(bottom);
        fitField.setVisible(bottom);

        footwearLabel.setVisible(footwear);
        footwearField.setVisible(footwear);


        revalidate();
        repaint();

    }

}