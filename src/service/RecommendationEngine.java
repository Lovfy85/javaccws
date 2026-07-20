package service;
import java.util.UUID;

import model.*;
import model.clothing.*;
import exception.InvalidClothingException;
import strategy.*;

public class RecommendationEngine {

    private OutfitScorer outfitScorer;

    public RecommendationEngine() {

        outfitScorer = new OutfitScorer();
    }

    public OutfitScorer getOutfitScorer() {
        return outfitScorer;
    }


    public Outfit recommendOutfit(User user) throws InvalidClothingException {

        RecommendationStrategy strategy =
            getStrategy(
                ClothingStyle.valueOf(
                    user.getStylesProfile()
                        .getStyle()
                        .toUpperCase()
                )
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

                        Outfit outfit =
                            new Outfit(
                                UUID.randomUUID().toString(),
                                user.getId(),
                                top,
                                bottom,
                                footwear
                            );


                        int score =
                            outfitScorer.scoreOutfit(
                                outfit,
                                user
                            );


                        if(score > highestScore) {

                            highestScore = score;
                            outfit.setScore(score);
                            bestOutfit = outfit;

                        }
                    }
                    catch(InvalidClothingException e) {
                    }
                }
            }
        }

        if(bestOutfit == null) {
            throw new InvalidClothingException("No valid outfit could be generated.");
        }

        return bestOutfit;
    }


    public OutfitOptions getOutfitOptions(User user)
            throws InvalidClothingException {

        return getStrategy(
            ClothingStyle.valueOf(
                user.getStylesProfile()
                    .getStyle()
                    .toUpperCase()
            )
        ).getOutfitOptions(
            user.getWardrobe()
        );
    }


    public Outfit createOutfit(Top top, Bottom bottom, Footwear footwear, User user) throws InvalidClothingException {

        return new Outfit(
            UUID.randomUUID().toString(),
            user.getId(),
            top,
            bottom,
            footwear
        );
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