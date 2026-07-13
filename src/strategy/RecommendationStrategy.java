package strategy;

import model.*;
import exception.InvalidClothingException;

public interface RecommendationStrategy {

    Outfit recommendOutfit(Wardrobe wardrobe) throws InvalidClothingException;

    OutfitOptions getOutfitOptions(Wardrobe wardrobe) throws InvalidClothingException;

}
