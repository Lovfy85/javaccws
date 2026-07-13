package strategy;

import model.*;
import exception.InvalidClothingException;

public class StreetwearStrategy extends DefaultStrategy {

    @Override
    public Outfit recommendOutfit(Wardrobe wardrobe) throws InvalidClothingException {

        return createStyleOutfit(wardrobe, ClothingStyle.STREETWEAR);
    }

    @Override
    public OutfitOptions getOutfitOptions(Wardrobe wardrobe) throws InvalidClothingException {

        return createStyleOptions(wardrobe, ClothingStyle.STREETWEAR);
    }
}
