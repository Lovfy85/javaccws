package model.clothing;

import model.ClothingStyle;

public abstract class ClothingItem {
    
    protected String id;
    protected String name;
    protected String color;
    protected String brand;
    protected String imagePath;
    protected ClothingStyle style;

    public ClothingItem(String id, String name, String color, String brand, String imagePath, ClothingStyle style){
        this.id = id;
        this.name = name;
        this.color = color;
        this.brand = brand;
        this.imagePath = imagePath;
        this.style = style;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getColor(){
        return color;
    }

    public String getBrand(){
        return brand;
    }

    public String getImagePath(){
        return imagePath;
    }

    public ClothingStyle getStyle(){
        return style;
    }

    @Override
    public String toString(){
        return name + "[" + color + "," + brand + "," + style + "]";
    }
}