package ui.auth;

import exception.AuthenticationException;
import exception.InvalidClothingException;
import model.User;
import service.UserService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class LoginPanel extends JPanel {

    private JFrame frame;
    private UserService userService = new UserService();

    private JTextField usernameField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();


    public LoginPanel(JFrame frame) {

        this.frame = frame;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(30,40,30,40));

        add(createPanel(), BorderLayout.CENTER);
    }


    private JPanel createPanel() {

        JPanel panel = new JPanel();

        panel.setBackground(Color.WHITE);

        panel.setLayout(
            new BoxLayout(
                panel,
                BoxLayout.Y_AXIS
            )
        );


        JLabel title = new JLabel("Login");

        title.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                28
            )
        );

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);

        panel.add(Box.createVerticalStrut(20));

        addField(
            panel,
            "Username",
            usernameField
        );


        addField(
            panel,
            "Password",
            passwordField
        );

        JButton login = new JButton("Login");

        login.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        login.addActionListener(
            e -> loginUser()
        );

        JButton register = new JButton("Create Account");

        register.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        register.addActionListener(
            e -> {

                frame.setContentPane(
                    new RegisterPanel(frame)
                );

                frame.revalidate();

                frame.repaint();
            }
        );

        panel.add(login);

        panel.add(Box.createVerticalStrut(10));

        panel.add(register);

        return panel;
    }


    private void addField(JPanel panel, String label, JComponent field) {

        JLabel labelComponent = new JLabel(label);

        labelComponent.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(labelComponent);


        field.setMaximumSize(
            new Dimension(
                300,
                30
            )
        );


        field.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );


        panel.add(field);


        panel.add(
            Box.createVerticalStrut(10)
        );
    }


    private void loginUser() {

        try {

            User user =
                userService.login(
                    usernameField.getText().trim(),
                    new String(
                        passwordField.getPassword()
                    )
                );


            JOptionPane.showMessageDialog(
                this,
                "Welcome, " + user.getName() + "!"
            );


            // Next stage:
            // Open WardrobeManagementPanel here


        } catch(AuthenticationException | SQLException | InvalidClothingException e) {

            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }
}