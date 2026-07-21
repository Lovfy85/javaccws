package service;

import java.util.UUID;

import model.*;
import model.clothing.*;
import exception.InvalidClothingException;
import strategy.*;

public class RecommendationEngine {

    private OutfitScorer outfitScorer;


    public RecommendationEngine(){
        outfitScorer=new OutfitScorer();
    }


    public OutfitScorer getOutfitScorer(){
        return outfitScorer;
    }


    public Outfit recommendOutfit(User user)
            throws InvalidClothingException {


        ClothingStyle style =
                user.getStylesProfile()
                .getClothingStyle();


        RecommendationStrategy strategy =
                getStrategy(style);


        OutfitOptions options =
                strategy.getOutfitOptions(
                        getStyledWardrobe(user,style)
                );


        Outfit best=null;
        int highest=-1;


        for(Top top:options.getTops()){

            for(Bottom bottom:options.getBottoms()){

                for(Footwear footwear:options.getFootwear()){

                    try{

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


                        if(score>highest){

                            highest=score;
                            outfit.setScore(score);
                            best=outfit;

                        }


                    }catch(InvalidClothingException ignored){}
                }
            }
        }


        if(best==null)

            throw new InvalidClothingException(
                    "No valid outfit could be generated."
            );


        return best;
    }



    public OutfitOptions getOutfitOptions(User user)
            throws InvalidClothingException {


        ClothingStyle style =
                user.getStylesProfile()
                .getClothingStyle();


        return getStrategy(style)
                .getOutfitOptions(
                    getStyledWardrobe(user,style)
                );
    }



    private Wardrobe getStyledWardrobe(
            User user,
            ClothingStyle style
    ) throws InvalidClothingException {

        Wardrobe filtered =
                new Wardrobe();


        for(ClothingItem item:
                user.getWardrobe().getItems()){


            if(item.getStyle()==style)

                filtered.addItem(item);

        }


        return filtered;

    }



    public Outfit createOutfit(
            Top top,
            Bottom bottom,
            Footwear footwear,
            User user
    )
            throws InvalidClothingException {


        return new Outfit(
                UUID.randomUUID().toString(),
                user.getId(),
                top,
                bottom,
                footwear
        );

    }



    public int scoreOutfit(
            Outfit outfit,
            User user
    ){

        int score =
                outfitScorer.scoreOutfit(
                        outfit,
                        user
                );

        outfit.setScore(score);

        return score;

    }



    private RecommendationStrategy getStrategy(
            ClothingStyle style
    ){

        switch(style){

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