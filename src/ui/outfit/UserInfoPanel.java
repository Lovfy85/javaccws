package ui.outfit;

import javax.swing.*;
import java.awt.*;

import model.User;


public class UserInfoPanel extends JPanel {

    public UserInfoPanel(User user) {

        setBackground(new Color(240, 240, 240));


        JLabel userInfo = new JLabel(
            "User: "
            + user.getName()
            + " | Style: "
            + user.getStylesProfile().getStyle()
            + " | Preference: "
            + user.getStylesProfile().getColorPreference()
        );

        userInfo.setFont(new Font("Arial", Font.PLAIN, 18));

        userInfo.setAlignmentX(Component.CENTER_ALIGNMENT);


        add(userInfo);
    }
}