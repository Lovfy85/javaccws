package model;

import model.clothing.ClothingItem;
import java.util.ArrayList;
import java.util.List;

public class Wardrobe {
    
    private List<ClothingItem> items = new ArrayList<>();

    public void addItem(ClothingItem item){
        items.add(item);
    }

    public List<ClothingItem> getItems(){
        return items;
    }

    public void printWardrobe(){
        System.out.println("Wardrobe: ");
        for (ClothingItem item : items){
            System.out.println("- " + item);
        }
    }
}
