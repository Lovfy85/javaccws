package ui.wardrobe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import model.*;
import model.clothing.*;
import service.WardrobeService;

public class EditClothingPanel extends JPanel {

    private JFrame frame;
    private JDialog dialog;
    private User user;
    private ClothingItem item;

    private WardrobeService service=new WardrobeService();

    private JTextField nameField,brandField,typeField;
    private JComboBox<String> colorBox;
    private JComboBox<ClothingStyle> styleBox;


    public EditClothingPanel(
            JFrame frame,
            User user,
            ClothingItem item,
            JDialog dialog){

        this.frame=frame;
        this.user=user;
        this.item=item;
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


        JLabel title=
                new JLabel(
                        "Edit Clothing Item",
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


        nameField=
                new JTextField(
                        item.getName());

        brandField=
                new JTextField(
                        item.getBrand());


        colorBox=
                new JComboBox<>(
                new String[]{
                "Black","White","Grey","Gray",
                "Blue","Green","Red",
                "Orange","Yellow","Pink",
                "Purple","Brown","Navy",
                "Beige","Olive"
        });


        colorBox.setSelectedItem(
                item.getColor());


        styleBox=
                new JComboBox<>(
                        ClothingStyle.values());


        styleBox.setSelectedItem(
                item.getStyle());


        addField(p,"Name:",nameField);
        addField(p,"Brand:",brandField);
        addField(p,"Color:",colorBox);
        addField(p,"Style:",styleBox);


        typeField=
                new JTextField();


        if(item instanceof Top top){

            typeField.setText(
                    top.getSleeveType());

            addField(
                    p,
                    "Sleeve:",
                    typeField);

        }

        else if(item instanceof Bottom bottom){

            typeField.setText(
                    bottom.getFitType());

            addField(
                    p,
                    "Fit:",
                    typeField);

        }

        else if(item instanceof Footwear footwear){

            typeField.setText(
                    footwear.getType());

            addField(
                    p,
                    "Footwear:",
                    typeField);

        }


        JButton save=
                new JButton("Save");

        JButton cancel=
                new JButton("Cancel");


        save.addActionListener(
                e->update());

        cancel.addActionListener(
                e->dialog.dispose());


        JPanel buttons=
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER));


        buttons.add(save);
        buttons.add(cancel);

        p.add(buttons);


        add(
                p,
                new GridBagConstraints());

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


        ((JComponent)c)
                .setAlignmentX(
                        Component.CENTER_ALIGNMENT);


        p.add(label);
        p.add(c);

    }


    private void update(){

        try{

            item.setName(
                    nameField.getText());

            item.setBrand(
                    brandField.getText());

            item.setColor(
                    colorBox
                    .getSelectedItem()
                    .toString());

            item.setStyle(
                    (ClothingStyle)
                    styleBox.getSelectedItem());


            if(item instanceof Top top)

                top.setSleeveType(
                        typeField.getText());

            else if(item instanceof Bottom bottom)

                bottom.setFitType(
                        typeField.getText());

            else if(item instanceof Footwear footwear)

                footwear.setType(
                        typeField.getText());


            service.updateClothingItem(item);


            JOptionPane.showMessageDialog(
                    this,
                    "Updated successfully.");


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

}