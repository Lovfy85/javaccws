package strategy;

import model.*;
import exception.InvalidClothingException;

public class StreetwearStrategy extends DefaultStrategy {

    @Override
    public OutfitOptions getOutfitOptions(Wardrobe wardrobe) throws InvalidClothingException {

        return createStyleOptions(wardrobe, ClothingStyle.STREETWEAR);
    }
}
