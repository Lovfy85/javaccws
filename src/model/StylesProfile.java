package model;

public class StylesProfile {

    private String style;
    private String colorPreference;

    public StylesProfile(String style, String colorPreference) {
        this.style = style;
        this.colorPreference = colorPreference;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getColorPreference() {
        return colorPreference;
    }

    public void setColorPreference(String colorPreference) {
        this.colorPreference = colorPreference;
    }

    @Override
    public String toString() {
        return style + " | " + colorPreference;
    }
}
