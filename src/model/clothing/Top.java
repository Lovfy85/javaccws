package model.clothing;

import model.ClothingStyle;

public class Top extends ClothingItem {

    private String sleeveType;


    public Top(String id, String userId, String name, String color, 
               String brand, String imagePath, ClothingStyle style, 
               String sleeveType) {

        super(id, userId, name, color, brand, imagePath, style);
        this.sleeveType = sleeveType;
    }


    public String getSleeveType() {
        return sleeveType;
    }


    public void setSleeveType(String sleeveType) {
        this.sleeveType = sleeveType;
    }
}