package model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String id;
    private String username;
    private String passwordHash;
    private String name;

    private StylesProfile stylesProfile;
    private Wardrobe wardrobe;

    private List<Outfit> savedOutfits;


    public User(String id, String username, String passwordHash,
                String name, StylesProfile stylesProfile, Wardrobe wardrobe) {

        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.name = name;
        this.stylesProfile = stylesProfile;
        this.wardrobe = wardrobe;

        this.savedOutfits = new ArrayList<>();
    }


    public String getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }


    public String getPasswordHash() {
        return passwordHash;
    }


    public String getName() {
        return name;
    }


    public StylesProfile getStylesProfile() {
        return stylesProfile;
    }


    public Wardrobe getWardrobe() {
        return wardrobe;
    }

    public List<Outfit> getSavedOutfits() {
        return savedOutfits;
    }

    public void addSavedOutfit(Outfit outfit) {
        savedOutfits.add(outfit);
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setStylesProfile(StylesProfile stylesProfile) {
        this.stylesProfile = stylesProfile;
    }


    public void setWardrobe(Wardrobe wardrobe) {
        this.wardrobe = wardrobe;
    }


    public void display() {

        System.out.println("User: " + name);
        System.out.println("Style: " + stylesProfile);

        if (wardrobe != null) {
            wardrobe.printWardrobe();
        }

        System.out.println("Saved Outfits: " + savedOutfits.size());
    }
}