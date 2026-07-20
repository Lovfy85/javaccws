package strategy;

import model.*;
import exception.InvalidClothingException;

public class BusinessCasualStrategy extends DefaultStrategy {

    @Override
    public OutfitOptions getOutfitOptions(Wardrobe wardrobe) throws InvalidClothingException {

        return createStyleOptions(wardrobe, ClothingStyle.BUSINESS_CASUAL);
    }

}