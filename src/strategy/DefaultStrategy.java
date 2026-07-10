package strategy;

import model.*;
import model.clothing.*;
import exception.InvalidClothingException;

public class DefaultStrategy implements RecommendationStrategy{


    @Override
    public Outfit recommendOutfit(Wardrobe wardrobe) throws InvalidClothingException {

        Top selectedTop = null;
        Bottom selectedBottom = null;
        Footwear selectedFootwear = null;


        for(ClothingItem item : wardrobe.getItems()){

            if(item instanceof Top && selectedTop == null){
                selectedTop = (Top) item;
            }


            if(item instanceof Bottom && selectedBottom == null){
                selectedBottom = (Bottom) item;
            }


            if(item instanceof Footwear && selectedFootwear == null){
                selectedFootwear = (Footwear) item;
            }
        }


        if(selectedTop == null || selectedBottom == null || selectedFootwear == null){
            throw new InvalidClothingException("Not enough clothing items available to create an outfit.");
        }


        return new Outfit(selectedTop, selectedBottom, selectedFootwear);
    }


    protected Outfit createStyleOutfit(Wardrobe wardrobe, ClothingStyle style) throws InvalidClothingException {

        Top selectedTop = null;
        Bottom selectedBottom = null;
        Footwear selectedFootwear = null;


        for(ClothingItem item : wardrobe.getItems()){

            if(item.getStyle() == style){


                if(item instanceof Top && selectedTop == null){
                    selectedTop = (Top) item;
                }


                if(item instanceof Bottom && selectedBottom == null){
                    selectedBottom = (Bottom) item;
                }


                if(item instanceof Footwear && selectedFootwear == null){
                    selectedFootwear = (Footwear) item;
                }
            }
        }


        if(selectedTop == null || selectedBottom == null || selectedFootwear == null){
            throw new InvalidClothingException("Not enough clothing items available for " + style + " outfit.");
        }


        return new Outfit(selectedTop, selectedBottom, selectedFootwear);
    }
}