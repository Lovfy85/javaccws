package model;

public class StylesProfile {

    private String style;
    private String colorPreference;


    public StylesProfile(String style, String colorPreference){

        setStyle(style);
        this.colorPreference = colorPreference;

    }


    public String getStyle(){
        return style;
    }


    public void setStyle(String style){

        if(style == null)
            throw new IllegalArgumentException(
                    "Style cannot be null"
            );


        ClothingStyle.valueOf(
                style.toUpperCase()
        );


        this.style = style;

    }


    public String getColorPreference(){
        return colorPreference;
    }


    public void setColorPreference(String colorPreference){

        this.colorPreference = colorPreference;

    }


    public ClothingStyle getClothingStyle(){

        return ClothingStyle.valueOf(
                style.toUpperCase()
        );

    }


    @Override
    public String toString(){

        return style + " | " + colorPreference;

    }

}