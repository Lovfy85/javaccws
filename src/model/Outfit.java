package model;

import model.clothing.Top;
import model.clothing.Bottom;
import model.clothing.Footwear;

import exception.InvalidClothingException;
import util.ColorMatcher;

public class Outfit {

    private String id;
    private String userId;

    private Top top;
    private Bottom bottom;
    private Footwear footwear;

    private int score;


    public Outfit(String id, String userId, Top top, Bottom bottom, Footwear footwear) 
            throws InvalidClothingException {

        this.id = id;
        this.userId = userId;

        this.top = top;
        this.bottom = bottom;
        this.footwear = footwear;

        this.score = 0;

        validateOutfit();
    }


    private void validateOutfit() throws InvalidClothingException {

        if(top == null || bottom == null || footwear == null) {

            throw new InvalidClothingException(
                "An outfit must contain a top, bottom, and footwear."
            );
        }


        if(!ColorMatcher.isCompatible(
            top.getColor(),
            bottom.getColor())) {

            throw new InvalidClothingException(
                "Top color is not compatible with bottom color."
            );
        }


        if(!ColorMatcher.isCompatible(
            bottom.getColor(),
            footwear.getColor())) {

            throw new InvalidClothingException(
                "Bottom color is not compatible with footwear color."
            );
        }


        if(!ColorMatcher.isCompatible(
            top.getColor(),
            footwear.getColor())) {

            throw new InvalidClothingException(
                "Top color is not compatible with footwear color."
            );
        }
    }


    public String getId() {
        return id;
    }


    public String getUserId() {
        return userId;
    }


    public Top getTop(){
        return top;
    }


    public Bottom getBottom(){
        return bottom;
    }


    public Footwear getFootwear(){
        return footwear;
    }


    public int getScore(){
        return score;
    }


    public void setScore(int score){
        this.score = score;
    }


    public void display(){

        System.out.println("Outfit:");
        System.out.println("Top: " + top);
        System.out.println("Bottom: " + bottom);
        System.out.println("Footwear: " + footwear);
        System.out.println("Score: " + score);
    }
}