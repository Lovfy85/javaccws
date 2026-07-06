package model.clothing;

public class Bottom extends ClothingItem {

    private String fitType;
    
    public Bottom(String id, String name, String color, String brand, String imagePath, String fitType) {
        super(id, name, color, brand, imagePath);
        this.fitType = fitType;
    }

    public String getFitType(){
        return fitType;
    }
}
