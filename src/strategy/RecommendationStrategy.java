package strategy;

import model.*;
import exception.InvalidClothingException;

public interface RecommendationStrategy {
    
    Outfit recommendOutfit(Wardrobe wardrobe) throws InvalidClothingException;
}
