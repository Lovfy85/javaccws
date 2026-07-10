package model.clothing;

import model.ClothingStyle;

public class Bottom extends ClothingItem {

    private String fitType;
    
    public Bottom(String id, String name, String color, String brand, String imagePath,ClothingStyle style,String fitType) {
        super(id, name, color, brand, imagePath, style);
        this.fitType = fitType;
    }

    public String getFitType(){
        return fitType;
    }
}