package model;

import model.clothing.ClothingItem;
import exception.InvalidClothingException;

import java.util.ArrayList;
import java.util.List;

public class Wardrobe {
    
    private List<ClothingItem> items = new ArrayList<>();

    public void addItem(ClothingItem item) throws InvalidClothingException {

        if(item == null){
            throw new InvalidClothingException(
                    "Cannot add null clothing item to wardrobe."
            );
        }

        if(item.getName() == null || item.getName().isEmpty()){
            throw new InvalidClothingException(
                    "Clothing item must have a name."
            );
        }

        if(item.getColor() == null || item.getColor().isEmpty()){
            throw new InvalidClothingException(
                    "Clothing item must have a color."
            );
        }

        if(item.getBrand() == null || item.getBrand().isEmpty()){
            throw new InvalidClothingException(
                    "Clothing item must have a brand."
            );
        }

        if(item.getImagePath() == null || item.getImagePath().isEmpty()){
            throw new InvalidClothingException(
                    "Clothing item must exist by a picture."
            );
        }

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
