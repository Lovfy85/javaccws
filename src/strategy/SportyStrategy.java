package strategy;

import model.*;
import exception.InvalidClothingException;

public class SportyStrategy extends DefaultStrategy {

    @Override
    public Outfit recommendOutfit(Wardrobe wardrobe) throws InvalidClothingException {

        return createStyleOutfit(wardrobe, ClothingStyle.SPORTY);
    }

    @Override
    public OutfitOptions getOutfitOptions(Wardrobe wardrobe) throws InvalidClothingException {

        return createStyleOptions(wardrobe, ClothingStyle.SPORTY);
    }
}