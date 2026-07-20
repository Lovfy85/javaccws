package ui.auth;

import exception.*;
import service.UserService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class RegisterPanel extends JPanel {

    private JFrame frame;
    private UserService userService = new UserService();

    private JTextField usernameField = new JTextField();
    private JTextField nameField = new JTextField();

    private JPasswordField passwordField = new JPasswordField();
    private JPasswordField confirmField = new JPasswordField();

    private JComboBox<String> styleBox = new JComboBox<>(
        new String[]{
            "CASUAL",
            "FORMAL",
            "SPORTY",
            "STREETWEAR",
            "BUSINESS_CASUAL"
        }
    );

    private JComboBox<String> colorBox = new JComboBox<>(
        new String[]{
            "Neutral",
            "Warm",
            "Cool",
            "Earth Tone"
        }
    );


    public RegisterPanel(JFrame frame) {

        this.frame = frame;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(30,40,30,40));

        add(createPanel(), BorderLayout.CENTER);
    }


    private JPanel createPanel() {

        JPanel panel = new JPanel();

        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        JLabel title = new JLabel("Create Account");

        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        panel.add(title);
        panel.add(Box.createVerticalStrut(20));


        addField(panel,"Username",usernameField);
        addField(panel,"Name",nameField);
        addField(panel,"Password",passwordField);
        addField(panel,"Confirm Password",confirmField);
        addField(panel,"Preferred Style",styleBox);
        addField(panel,"Preferred Color Category",colorBox);


        JButton register = new JButton("Register");

        register.setAlignmentX(Component.CENTER_ALIGNMENT);

        register.addActionListener(e -> registerUser());


        JButton login = new JButton("Already have an account? Login");

        login.setAlignmentX(Component.CENTER_ALIGNMENT);

        login.addActionListener(e -> {

            frame.setContentPane(new LoginPanel(frame));
            frame.revalidate();
            frame.repaint();

        });


        panel.add(register);
        panel.add(Box.createVerticalStrut(10));
        panel.add(login);


        return panel;
    }


    private void addField(JPanel panel,String label,JComponent field){

        JLabel labelComponent = new JLabel(label);

        labelComponent.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(labelComponent);

        field.setMaximumSize(new Dimension(300,30));

        field.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(field);

        panel.add(Box.createVerticalStrut(10));
    }


    private void registerUser(){

        String password = new String(passwordField.getPassword());
        String confirm = new String(confirmField.getPassword());

        if(!password.equals(confirm)){

            JOptionPane.showMessageDialog(this, "Passwords do not match.");

            return;
        }


        try{

            userService.register(
                usernameField.getText().trim(),
                password,
                nameField.getText().trim(),
                (String) styleBox.getSelectedItem(),
                (String) colorBox.getSelectedItem()
            );


            JOptionPane.showMessageDialog(this, "Registration successful!");

            frame.setContentPane(new LoginPanel(frame));

            frame.revalidate();
            frame.repaint();

        }

        catch(UserAlreadyExistsException | AuthenticationException | SQLException e){

            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}