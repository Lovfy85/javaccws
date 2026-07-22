package util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

public class ImageProcessor {


    public static String process(File original) throws Exception {

        BufferedImage image=
                ImageIO.read(original);


        image=
                applyOrientation(
                        image,
                        getOrientation(original));


        File folder=
                new File(
                        "resources/images/uploaded");


        if(!folder.exists())
            folder.mkdirs();


        File output=
                new File(
                        folder,
                        UUID.randomUUID()
                        + ".png");


        ImageIO.write(
                image,
                "png",
                output);


        return output.getPath();

    }


    private static int getOrientation(File file){

        try{

            Metadata metadata=
                    ImageMetadataReader.readMetadata(file);


            ExifIFD0Directory directory=
                    metadata.getFirstDirectoryOfType(
                            ExifIFD0Directory.class);


            if(directory!=null)

                return directory.getInt(
                        ExifIFD0Directory.TAG_ORIENTATION);


        }catch(Exception ignored){}


        return 1;

    }


    private static BufferedImage applyOrientation(
            BufferedImage image,
            int orientation){


        switch(orientation){

            case 6:
                return rotate(image,90);

            case 3:
                return rotate(image,180);

            case 8:
                return rotate(image,270);

            default:
                return image;
        }

    }


    private static BufferedImage rotate(
            BufferedImage image,
            int degrees){


        int width=image.getWidth();
        int height=image.getHeight();


        BufferedImage rotated=
                new BufferedImage(
                        height,
                        width,
                        image.getType());


        Graphics2D g=
                rotated.createGraphics();


        if(degrees==90){

            g.translate(height,0);
            g.rotate(Math.toRadians(90));

        }
        else if(degrees==180){

            g.translate(width,height);
            g.rotate(Math.toRadians(180));

        }
        else if(degrees==270){

            g.translate(0,width);
            g.rotate(Math.toRadians(270));

        }


        g.drawImage(
                image,
                0,
                0,
                null);


        g.dispose();


        return rotated;

    }

}