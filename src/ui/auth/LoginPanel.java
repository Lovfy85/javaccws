package ui.auth;

import exception.AuthenticationException;
import exception.InvalidClothingException;
import model.User;
import service.UserService;
import ui.wardrobe.WardrobeManagementPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class LoginPanel extends JPanel {

    private JFrame frame;
    private UserService userService=new UserService();

    private JTextField usernameField=new JTextField();
    private JPasswordField passwordField=new JPasswordField();


    public LoginPanel(JFrame frame){

        this.frame=frame;

        setLayout(new BorderLayout());
        setBackground(new Color(245,245,245));
        setBorder(new EmptyBorder(30,40,30,40));

        add(createPanel(),BorderLayout.CENTER);

    }


    private JPanel createPanel(){

        JPanel panel=new JPanel();

        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        new EmptyBorder(35,55,35,55)));

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));


        JLabel title=new JLabel("FitCheck");

        title.setFont(
                new Font("Arial",
                Font.BOLD,
                40));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);


        JLabel tagline=new JLabel(
                "Your Personal Style Assistant");

        tagline.setFont(
                new Font("Arial",
                Font.PLAIN,
                16));

        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(tagline);


        panel.add(Box.createVerticalStrut(25));


        addField(panel,"Username",usernameField);
        addField(panel,"Password",passwordField);


        JButton login=new JButton("Login");
        JButton register=new JButton("Create Account");


        login.setAlignmentX(Component.CENTER_ALIGNMENT);
        register.setAlignmentX(Component.CENTER_ALIGNMENT);


        login.addActionListener(e->loginUser());


        register.addActionListener(e->{

            frame.setContentPane(
                    new RegisterPanel(frame));

            frame.revalidate();
            frame.repaint();

        });


        panel.add(login);

        panel.add(Box.createVerticalStrut(10));

        panel.add(register);


        panel.add(Box.createVerticalStrut(20));


        JPanel description=new JPanel();

        description.setBackground(Color.WHITE);

        description.setBorder(
                BorderFactory.createTitledBorder(
                        "About FitCheck"));


        JLabel text=new JLabel(
                "<html><center>"+
                "Blend Your Clothes<br>"+
                "In Organized Styles!"+
                "</center></html>");

        text.setHorizontalAlignment(
                SwingConstants.CENTER);


        description.add(text);

        description.setAlignmentX(
                Component.CENTER_ALIGNMENT);


        panel.add(description);


        JLabel footer=new JLabel(
                "Organize • Style • Score");

        footer.setFont(
                new Font("Arial",
                Font.ITALIC,
                12));

        footer.setAlignmentX(
                Component.CENTER_ALIGNMENT);


        panel.add(Box.createVerticalStrut(10));

        panel.add(footer);


        return panel;

    }


    private void addField(
            JPanel panel,
            String label,
            JComponent field){

        JLabel l=new JLabel(label);

        l.setAlignmentX(
                Component.CENTER_ALIGNMENT);


        field.setMaximumSize(
                new Dimension(300,30));

        field.setAlignmentX(
                Component.CENTER_ALIGNMENT);


        panel.add(l);
        panel.add(field);
        panel.add(Box.createVerticalStrut(10));

    }


    private void loginUser(){

        try{

            User user=
                    userService.login(
                            usernameField.getText().trim(),
                            new String(
                            passwordField.getPassword()));


            JOptionPane.showMessageDialog(
                    this,
                    "Welcome, "+user.getName()+"!");


            frame.setResizable(true);

            frame.setContentPane(
                    new WardrobeManagementPanel(
                            frame,
                            user));


            frame.setExtendedState(
                    JFrame.MAXIMIZED_BOTH);


            frame.revalidate();
            frame.repaint();


        }catch(
                AuthenticationException |
                SQLException |
                InvalidClothingException e){

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }

    }

}