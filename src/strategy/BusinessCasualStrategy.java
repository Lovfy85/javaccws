package strategy;

import model.*;
import exception.InvalidClothingException;

public class BusinessCasualStrategy extends DefaultStrategy{

    @Override
    public Outfit recommendOutfit(Wardrobe wardrobe) throws InvalidClothingException {

        return createStyleOutfit(wardrobe, ClothingStyle.BUSINESS_CASUAL);
    }
}
