package model;

public class User {
    
    private String id;
    private String name;
    private StylesProfile stylesProfile;
    private Wardrobe wardrobe;

    public User(String id, String name, StylesProfile stylesProfile, Wardrobe wardrobe){
        this.id = id;
        this.name = name;
        this.stylesProfile = stylesProfile;
        this.wardrobe = wardrobe;
    }

    public String id(){
        return id;
    }

    public String getName(){
        return name;
    }

    public StylesProfile getStylesProfile(){
        return stylesProfile;
    }

    public Wardrobe getWardrobe(){
        return wardrobe;
    }

    public void display(){
        System.out.println("User: " + name);
        System.out.println("Style: " + stylesProfile);
        wardrobe.printWardrobe();
    }
}
