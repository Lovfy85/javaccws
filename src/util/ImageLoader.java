package util;

import javax.swing.ImageIcon;
import java.awt.Image;

public class ImageLoader {

    public static ImageIcon load(String path) {

        ImageIcon icon = new ImageIcon(path);

        Image resized = icon.getImage()
                .getScaledInstance(
                        250,
                        250,
                        Image.SCALE_SMOOTH
                );

        return new ImageIcon(resized);
    }
}
