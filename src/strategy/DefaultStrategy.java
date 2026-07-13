package strategy;

import model.*;
import model.clothing.*;
import exception.InvalidClothingException;

public abstract class DefaultStrategy implements RecommendationStrategy {


    @Override
    public Outfit recommendOutfit(Wardrobe wardrobe)throws InvalidClothingException {

        Top selectedTop = null;
        Bottom selectedBottom = null;
        Footwear selectedFootwear = null;

        for(ClothingItem item : wardrobe.getItems()) {

            if(item instanceof Top && selectedTop == null) {
                selectedTop = (Top) item;
            }

            if(item instanceof Bottom && selectedBottom == null) {
                selectedBottom = (Bottom) item;
            }

            if(item instanceof Footwear && selectedFootwear == null) {
                selectedFootwear = (Footwear) item;
            }

        }

        if(selectedTop == null || selectedBottom == null || selectedFootwear == null) {
            throw new InvalidClothingException("Not enough clothing items available to create an outfit.");
        }

        return new Outfit(selectedTop, selectedBottom, selectedFootwear);

    }


    @Override
    public OutfitOptions getOutfitOptions(Wardrobe wardrobe) throws InvalidClothingException {

        OutfitOptions options = new OutfitOptions();

        for(ClothingItem item : wardrobe.getItems()) {

            if(item instanceof Top) {
                options.addTop((Top)item);
            }


            else if(item instanceof Bottom) {
                options.addBottom((Bottom)item);
            }

            else if(item instanceof Footwear) {
                options.addFootwear((Footwear)item);
            }

        }

        if(options.isEmpty()) {
            throw new InvalidClothingException("No clothing options available.");
        }

        return options;
    }


    protected OutfitOptions createStyleOptions(Wardrobe wardrobe,ClothingStyle style)throws InvalidClothingException {

        OutfitOptions options = new OutfitOptions();

        for(ClothingItem item : wardrobe.getItems()) {
                
            if(item.getStyle() == style) {

                if(item instanceof Top) {
                    options.addTop((Top)item);
                }

                else if(item instanceof Bottom) {
                    options.addBottom((Bottom)item);
                }

                else if(item instanceof Footwear) {
                    options.addFootwear((Footwear)item);
                }
            }
        }

        if(options.isEmpty()) {
            throw new InvalidClothingException(
                    "Not enough clothing items available for "
                    + style
                    + " outfit."
            );
        }

        return options;
    }


    protected Outfit createStyleOutfit(Wardrobe wardrobe,ClothingStyle style) throws InvalidClothingException {

        OutfitOptions options = createStyleOptions(wardrobe, style);

        if(options.getTops().isEmpty() || options.getBottoms().isEmpty() || options.getFootwear().isEmpty()) {

            throw new InvalidClothingException(
                    "Missing clothing category for "
                    + style
                    + " outfit."
            );
        }

        return new Outfit(
            options.getTops().get(0),
            options.getBottoms().get(0),
            options.getFootwear().get(0)
        );
    }
}