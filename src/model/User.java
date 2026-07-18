package model;

public class User {

    private String id;
    private String username;
    private String passwordHash;
    private String name;
    private StylesProfile stylesProfile;
    private Wardrobe wardrobe;

    public User(String id, String username, String passwordHash,
                String name, StylesProfile stylesProfile, Wardrobe wardrobe) {

        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.name = name;
        this.stylesProfile = stylesProfile;
        this.wardrobe = wardrobe;
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
    }
}