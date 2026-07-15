package service;

import model.*;

import model.clothing.*;
import exception.InvalidClothingException;

import strategy.*;

public class RecommendationEngine {

    private OutfitScorer outfitScorer;

    public RecommendationEngine() {

        outfitScorer = new OutfitScorer();
    }

    public OutfitScorer getOutfitScorer(){

        return outfitScorer;
    }


    public Outfit recommendOutfit(User user) throws InvalidClothingException {

        ClothingStyle preferredStyle =
            ClothingStyle.valueOf(
                user.getStylesProfile()
                    .getStyle()
                    .toUpperCase()
            );

        RecommendationStrategy strategy =
                getStrategy(
                    preferredStyle
                );

        OutfitOptions options =
                strategy.getOutfitOptions(
                    user.getWardrobe()
                );

        Outfit bestOutfit = null;

        int highestScore = -1;

        for(Top top : options.getTops()) {
            for(Bottom bottom : options.getBottoms()) {
                for(Footwear footwear : options.getFootwear()) {
                    try {

                        Outfit currentOutfit = new Outfit(top, bottom, footwear);

                        int currentScore = outfitScorer.scoreOutfit(currentOutfit,user);

                        if(currentScore > highestScore) {

                            highestScore = currentScore;
                            currentOutfit.setScore(currentScore);
                            bestOutfit = currentOutfit;
                        }
                    }

                    catch(InvalidClothingException e) {
                    }
                }
            }
        }

        if(bestOutfit == null) {
            throw new InvalidClothingException(  "No valid outfit could be generated.");
        }

        return bestOutfit;
    }


    public OutfitOptions getOutfitOptions(User user) throws InvalidClothingException {

        ClothingStyle preferredStyle =
            ClothingStyle.valueOf(
                user.getStylesProfile()
                    .getStyle()
                    .toUpperCase()
            );

        RecommendationStrategy strategy = getStrategy(preferredStyle);

        return strategy.getOutfitOptions(user.getWardrobe());
    }


    public Outfit createOutfit(Top top, Bottom bottom, Footwear footwear) throws InvalidClothingException {
        return new Outfit(top, bottom, footwear);
    }


    public int scoreOutfit(Outfit outfit, User user) {

        int score = outfitScorer.scoreOutfit(outfit, user);
        outfit.setScore(score);
        return score;
    }


    private RecommendationStrategy getStrategy(ClothingStyle style) {

        switch(style) {

            case CASUAL:
                return new CasualStrategy();

            case FORMAL:
                return new FormalStrategy();

            case SPORTY:
                return new SportyStrategy();

            case STREETWEAR:
                return new StreetwearStrategy();

            case BUSINESS_CASUAL:
                return new BusinessCasualStrategy();

            default:
                return new CasualStrategy();
        }
    }
}