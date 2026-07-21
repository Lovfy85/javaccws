package model.clothing;

import model.ClothingStyle;

public class Bottom extends ClothingItem {

    private String fitType;


    public Bottom(String id, String userId, String name, String color, 
                  String brand, String imagePath, ClothingStyle style, 
                  String fitType) {

        super(id, userId, name, color, brand, imagePath, style);
        this.fitType = fitType;
    }


    public String getFitType() {
        return fitType;
    }


    public void setFitType(String fitType) {
        this.fitType = fitType;
    }
}