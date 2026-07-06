package model.clothing;

public class Top extends ClothingItem{

    private String sleeveType;

    public Top(String id, String name, String color, String brand, String imagePath, String sleeveType) {
        super(id, name, color, brand, imagePath);
        this.sleeveType = sleeveType;
    }
    
    public String getSleeveType(){
        return sleeveType;
    }
}
