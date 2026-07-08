package model;

import model.clothing.Top;
import model.clothing.Bottom;
import model.clothing.Footwear;

import exception.InvalidClothingException;
import util.ColorMatcher;

public class Outfit {
    
    private Top top;
    private Bottom bottom;
    private Footwear footwear;

    public Outfit(Top top, Bottom bottom, Footwear footwear) throws InvalidClothingException {
        
        this.top = top;
        this.bottom = bottom;
        this.footwear = footwear;

        validateOutfit();
    }


    private void validateOutfit() throws InvalidClothingException {

        if(top == null || bottom == null || footwear == null){
            throw new InvalidClothingException(
                    "An outfit must contain a top, bottom, and footwear."
            );
        }


        if(!ColorMatcher.isCompatible(
                top.getColor(),
                bottom.getColor()
        )){
            throw new InvalidClothingException(
                    "Top color is not compatible with bottom color."
            );
        }


        if(!ColorMatcher.isCompatible(
                bottom.getColor(),
                footwear.getColor()
        )){
            throw new InvalidClothingException(
                    "Bottom color is not compatible with footwear color."
            );
        }


        if(!ColorMatcher.isCompatible(
                top.getColor(),
                footwear.getColor()
        )){
            throw new InvalidClothingException(
                    "Top color is not compatible with footwear color."
            );
        }
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

    public void display(){
        System.out.println("Outfit: ");
        System.out.println("Top: " + top);
        System.out.println("Bottom: " + bottom);
        System.out.println("Footwear: " + footwear);
    }
}
