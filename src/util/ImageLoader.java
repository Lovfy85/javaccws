package util;

import javax.swing.ImageIcon;
import java.awt.Image;

public class ImageLoader {

    public static ImageIcon load(String path) {

        return load(path,250,250);

    }


    public static ImageIcon load(
            String path,
            int width,
            int height) {


        ImageIcon icon =
                new ImageIcon(path);


        Image resized =
                icon.getImage()
                .getScaledInstance(
                        width,
                        height,
                        Image.SCALE_SMOOTH
                );


        return new ImageIcon(resized);

    }
}