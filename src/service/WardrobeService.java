package service;

import model.*;
import model.clothing.*;

import repository.WardrobeRepository;
import exception.InvalidClothingException;

import java.sql.SQLException;
import java.util.*;

public class WardrobeService {

    private WardrobeRepository wardrobeRepository = new WardrobeRepository();

    public void addClothingItem(ClothingItem item, User user) throws SQLException,InvalidClothingException{

        user.getWardrobe().addItem(item);

        wardrobeRepository.save(
            item,
            user.getId()
        );
    }


    public void removeClothingItem(ClothingItem item, User user) throws SQLException{

        user.getWardrobe().removeItem(item);

        wardrobeRepository.delete(item.getId());
    }


    public void updateClothingItem(ClothingItem item) throws SQLException {

        wardrobeRepository.update(item);
    }


    public boolean hasItems(Wardrobe wardrobe){

        return !wardrobe.getItems().isEmpty();
    }


    public List<Top> getTops(Wardrobe wardrobe){

        List<Top> tops=new ArrayList<>();

        for(ClothingItem item:wardrobe.getItems())

            if(item instanceof Top) 
                tops.add((Top)item);

        return tops;

    }


    public List<Bottom> getBottoms(Wardrobe wardrobe){

        List<Bottom> bottoms=new ArrayList<>();

        for(ClothingItem item:wardrobe.getItems())

            if(item instanceof Bottom)
                bottoms.add((Bottom)item);

        return bottoms;

    }


    public List<Footwear> getFootwear(Wardrobe wardrobe){

        List<Footwear> footwear=new ArrayList<>();

        for(ClothingItem item:wardrobe.getItems())

            if(item instanceof Footwear)
                footwear.add((Footwear)item);

        return footwear;

    }


    public List<ClothingItem> getItemsByStyle(Wardrobe wardrobe, ClothingStyle style){

        List<ClothingItem> items=new ArrayList<>();

        for(ClothingItem item:wardrobe.getItems())

            if(item.getStyle()==style)
                items.add(item);

        return items;

    }


    public OutfitOptions getOutfitOptions(Wardrobe wardrobe){

        OutfitOptions options=new OutfitOptions();

        for(ClothingItem item:wardrobe.getItems()){

            if(item instanceof Top)
                options.addTop((Top)item);

            else if(item instanceof Bottom)
                options.addBottom((Bottom)item);

            else if(item instanceof Footwear)
                options.addFootwear((Footwear)item);

        }

        return options;

    }


    public OutfitOptions getOutfitOptionsByStyle(Wardrobe wardrobe, ClothingStyle style){

        OutfitOptions options=new OutfitOptions();

        for(ClothingItem item:wardrobe.getItems()){

            if(item.getStyle()!=style)
                continue;

            if(item instanceof Top)
                options.addTop((Top)item);

            else if(item instanceof Bottom)
                options.addBottom((Bottom)item);

            else if(item instanceof Footwear)
                options.addFootwear((Footwear)item);

        }

        return options;

    }

}