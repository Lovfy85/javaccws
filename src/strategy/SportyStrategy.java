package strategy;

import model.*;
import exception.InvalidClothingException;

public class SportyStrategy extends DefaultStrategy {

    @Override
    public OutfitOptions getOutfitOptions(Wardrobe wardrobe) throws InvalidClothingException {

        return createStyleOptions(wardrobe, ClothingStyle.SPORTY);
    }
}