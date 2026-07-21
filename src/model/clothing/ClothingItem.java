package model.clothing;

import model.ClothingStyle;

public abstract class ClothingItem {

    protected String id;
    protected String userId;
    protected String name;
    protected String color;
    protected String brand;
    protected String imagePath;
    protected ClothingStyle style;


    public ClothingItem(String id, String userId, String name, String color,
                         String brand, String imagePath, ClothingStyle style) {

        this.id = id;
        this.userId = userId;
        this.name = name;
        this.color = color;
        this.brand = brand;
        this.imagePath = imagePath;
        this.style = style;
    }


    public String getId() {
        return id;
    }


    public String getUserId() {
        return userId;
    }


    public String getName() {
        return name;
    }


    public String getColor() {
        return color;
    }


    public String getBrand() {
        return brand;
    }


    public String getImagePath() {
        return imagePath;
    }


    public ClothingStyle getStyle() {
        return style;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public void setBrand(String brand) {
        this.brand = brand;
    }


    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public void setStyle(ClothingStyle style) {
        this.style = style;
    }


    @Override
    public String toString() {
        return name + "[" + color + "," + brand + "," + style + "]";
    }
}