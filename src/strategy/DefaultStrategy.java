package strategy;

import model.*;
import model.clothing.*;

import exception.InvalidClothingException;
import service.WardrobeService;


public abstract class DefaultStrategy implements RecommendationStrategy {

    private WardrobeService wardrobeService;

    public DefaultStrategy() {
        wardrobeService = new WardrobeService();
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


    protected OutfitOptions createStyleOptions(Wardrobe wardrobe, ClothingStyle style) throws InvalidClothingException {

        OutfitOptions options = wardrobeService.getOutfitOptionsByStyle(wardrobe, style);

        if(options.isEmpty()) {
            throw new InvalidClothingException("No clothing available for " + style + " style."
            );
        }

        return options;
    }


    protected Outfit createStyleOutfit(Wardrobe wardrobe, ClothingStyle style) throws InvalidClothingException {

        OutfitOptions options = createStyleOptions(wardrobe, style);

        if(options.getTops().isEmpty()
                || options.getBottoms().isEmpty()
                || options.getFootwear().isEmpty()) {

            throw new InvalidClothingException(
                    "Missing clothing category for "
                    + style
                    + " outfit."
            );
        }

        return null;
    }

}