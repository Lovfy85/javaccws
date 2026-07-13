package model;

import model.clothing.Top;
import model.clothing.Bottom;
import model.clothing.Footwear;

import java.util.ArrayList;
import java.util.List;

public class OutfitOptions {

    private List<Top> tops;
    private List<Bottom> bottoms;
    private List<Footwear> footwear;

    public OutfitOptions() {

        tops = new ArrayList<>();
        bottoms = new ArrayList<>();
        footwear = new ArrayList<>();

    }

    public void addTop(Top top) {
        tops.add(top);
    }

    public void addBottom(Bottom bottom) {
        bottoms.add(bottom);
    }

    public void addFootwear(Footwear shoe) {
        footwear.add(shoe);
    }

    public List<Top> getTops() {
        return tops;
    }

    public List<Bottom> getBottoms() {
        return bottoms;
    }

    public List<Footwear> getFootwear() {
        return footwear;
    }

    public boolean isEmpty() {
        return tops.isEmpty() || bottoms.isEmpty()|| footwear.isEmpty();
    }

    public void displayOptions() {

        System.out.println("Available Tops:");
        for (Top top : tops) {
            System.out.println("- " + top.getName());
        }

        System.out.println();

        System.out.println("Available Bottoms:");
        for (Bottom bottom : bottoms) {
            System.out.println("- " + bottom.getName());
        }

        System.out.println();

        System.out.println("Available Footwear:");
        for (Footwear shoe : footwear) {
            System.out.println("- " + shoe.getName());
        }

    }

}
