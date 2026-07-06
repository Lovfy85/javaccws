package model.clothing;

public abstract class ClothingItem {
    
    protected String id;
    protected String name;
    protected String color;
    protected String brand;
    protected String imagePath;

    public ClothingItem(String id, String name, String color, String brand, String imagePath){
        this.id = id;
        this.name = name;
        this.color = color;
        this.brand = brand;
        this.imagePath = imagePath;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getColor(){
        return brand;
    }

    public String getBrand(){
        return brand;
    }

    public String getImagePath(){
        return imagePath;
    }

    @Override
    public String toString(){
        return name + "[" + color + "," + brand + "]";
    }
}
