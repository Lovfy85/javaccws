package strategy;

import model.*;
import exception.InvalidClothingException;

public class SportyStrategy extends DefaultStrategy{

    @Override
    public Outfit recommendOutfit(Wardrobe wardrobe) throws InvalidClothingException {

        return createStyleOutfit(wardrobe, ClothingStyle.SPORTY);
    }
}
