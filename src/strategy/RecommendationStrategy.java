package strategy;

import model.*;
import exception.InvalidClothingException;

public interface RecommendationStrategy {
    
    Outfit recommedOutfit(Wardrobe wardrobe) throws InvalidClothingException;
}
