package strategy;

import model.*;
import exception.InvalidClothingException;

public interface RecommendationStrategy {

    OutfitOptions getOutfitOptions(Wardrobe wardrobe) throws InvalidClothingException;

}
