package model.clothing;

import model.ClothingStyle;

public class Top extends ClothingItem{

    private String sleeveType;

    public Top(String id, String name, String color, String brand, String imagePath, ClothingStyle style, String sleeveType) {
        super(id, name, color, brand, imagePath, style);
        this.sleeveType = sleeveType;
    }
    
    public String getSleeveType(){
        return sleeveType;
    }
}
