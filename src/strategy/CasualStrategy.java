package strategy;

import model.*;
import exception.InvalidClothingException;

public class CasualStrategy extends DefaultStrategy {

    @Override
    public OutfitOptions getOutfitOptions(Wardrobe wardrobe) throws InvalidClothingException {

        return createStyleOptions(wardrobe, ClothingStyle.CASUAL);
    }
}