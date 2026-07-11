package util;

import java.util.Arrays;
import java.util.List;

public class ColorMatcher {
    
    private static final List<String> NEUTRAL_COLORS = 
        Arrays.asList(
            "black",
            "white",
            "grey",
            "gray",
            "beige",
            "brown",
            "navy"
        );

    private static final List<String> WARM_COLORS = 
        Arrays.asList(
            "red",
            "orange",
            "yellow",
            "pink"
        );

    private static final List<String> COOL_COLORS = 
        Arrays.asList(
            "blue",
            "green",
            "purple"
        );


    private static final List<String> EARTH_TONES =
        Arrays.asList(
            "brown",
            "beige",
            "olive",
            "green"
        );


    private static final List<String> CONTRAST_COLORS =
        Arrays.asList(
            "black",
            "white",
            "red",
            "blue"
        );

    
    public static boolean isCompatible(String color1, String color2){

        if(color1 == null || color2 == null){
            return false;
        }

        color1 = color1.toLowerCase();
        color2 = color2.toLowerCase();

        if(color1.equals(color2)){
            return true;
        }

        if(isNeutral(color1) || isNeutral(color2)){
            return true;
        }

        if(isWarm(color1) && isWarm(color2)){
            return true;
        }

        if(isCool(color1) && isCool(color2)){
            return true;
        }

        if(isEarthTone(color1) && isEarthTone(color2)){
            return true;
        }

        if((isWarm(color1) && isCool(color2)) || (isWarm(color2) && isCool(color1))){
            return true;
        }

        if(CONTRAST_COLORS.contains(color1) || CONTRAST_COLORS.contains(color2)){
            return true;
        }


        return false;
    }


    public static boolean isNeutral(String color){
        return NEUTRAL_COLORS.contains(color.toLowerCase());
    }

    public static boolean isWarm(String color){
        return WARM_COLORS.contains(color.toLowerCase());
    }

    public static boolean isCool(String color){
        return COOL_COLORS.contains(color.toLowerCase());
    }

    public static boolean isEarthTone(String color){
        return EARTH_TONES.contains(color.toLowerCase());
    }


    public static String getColorCategory(String color){

        if(isNeutral(color)){
            return "Neutral";
        }

        if(isWarm(color)){
            return "Warm";
        }

        if(isCool(color)){
            return "Cool";
        }

        if(isEarthTone(color)){
            return "Earth Tone";
        }

        return "Unknown";
    }

}
