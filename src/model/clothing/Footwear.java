package model.clothing;

import model.ClothingStyle;

public class Footwear extends ClothingItem {

    private String type;


    public Footwear(String id, String userId, String name, String color, 
                    String brand, String imagePath, ClothingStyle style, 
                    String type) {

        super(id, userId, name, color, brand, imagePath, style);
        this.type = type;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }
}