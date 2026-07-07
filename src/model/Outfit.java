package model;

import model.clothing.Top;
import model.clothing.Bottom; 
import model.clothing.Footwear;

public class Outfit {
    
    private Top top;
    private Bottom bottom;
    private Footwear footwear;

    public Outfit(Top top, Bottom bottom, Footwear footwear){
        this.top = top;
        this.bottom = bottom;
        this.footwear = footwear;
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
        System.out.println("Bottom:: " + bottom);
        System.out.println("Footwear: " + footwear);
    }
}
