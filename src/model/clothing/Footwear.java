package model.clothing;

public class Footwear extends ClothingItem{

    private String type;

    public Footwear(String id, String name, String color, String brand, String imagePath, String type) {
        super(id, name, color, brand, imagePath);
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
