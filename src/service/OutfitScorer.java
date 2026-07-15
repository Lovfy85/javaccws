package service;

import model.*;
import util.ColorMatcher;

public class OutfitScorer {

    public int scoreOutfit(Outfit outfit, User user) {

        int score = 0;

        ClothingStyle preferredStyle =
            ClothingStyle.valueOf(
                user.getStylesProfile()
                    .getStyle()
                    .toUpperCase()
            );

        if(outfit.getTop().getStyle() == preferredStyle) {
            score += 20;
        }

        if(outfit.getBottom().getStyle() == preferredStyle) {
            score += 20;
        }

        if(outfit.getFootwear().getStyle() == preferredStyle) {
            score += 20;
        }


        if(ColorMatcher.isCompatible(
            outfit.getTop().getColor(),
            outfit.getBottom().getColor())) {

            score += 10;
        }

        else {
            score -= 10;
        }

        if(ColorMatcher.isCompatible(
            outfit.getBottom().getColor(),
            outfit.getFootwear().getColor())) {

            score += 10;
        }

        else {
            score -= 10;
        }


        if(ColorMatcher.isCompatible(
            outfit.getTop().getColor(),
            outfit.getFootwear().getColor())) {

            score += 10;
        }

        else {

            score -= 10;
        }


        String preferredColor = user.getStylesProfile().getColorPreference();


        if(ColorMatcher.getColorCategory(outfit.getTop().getColor()).equalsIgnoreCase(preferredColor)) {
            score += 5;
        }

        if(ColorMatcher.getColorCategory(outfit.getBottom().getColor()).equalsIgnoreCase(preferredColor)) {
            score += 5;
        }

        if(ColorMatcher.getColorCategory(outfit.getFootwear().getColor()).equalsIgnoreCase(preferredColor)) {
            score += 5;
        }


        if(outfit.getTop().getStyle() == outfit.getBottom().getStyle() &&
           outfit.getBottom().getStyle() == outfit.getFootwear().getStyle()) {

            score += 10;
        }


        if(ColorMatcher.isNeutral(outfit.getTop().getColor())) {
            score += 5;

        }

        if(ColorMatcher.isNeutral(outfit.getBottom().getColor())) {
            score += 5;

        }

        if(ColorMatcher.isNeutral(outfit.getFootwear().getColor())) {
            score += 5;
        }


        if(score < 0) {
            score = 0;
        }

        return score;
    }
}