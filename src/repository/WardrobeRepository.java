package repository;

import db.DBConnection;
import model.*;
import model.clothing.*;

import java.sql.*;
import java.util.*;

public class WardrobeRepository {


    public void save(ClothingItem item,String userId)throws SQLException{

        String sql="""
            INSERT INTO clothing_items
            (id,user_id,category,name,color,brand,image_path,style,
             sleeve_type,fit_type,footwear_type)
            VALUES(?,?,?,?,?,?,?,?,?,?,?)
            """;


        try(Connection c=DBConnection.getConnection();
            PreparedStatement s=c.prepareStatement(sql)){

            s.setString(1,item.getId());
            s.setString(2,userId);
            s.setString(3,getCategory(item));
            s.setString(4,item.getName());
            s.setString(5,item.getColor());
            s.setString(6,item.getBrand());
            s.setString(7,item.getImagePath());
            s.setString(8,item.getStyle().name());

            setExtraFields(s,item,9);

            s.executeUpdate();
        }
    }



    public void update(ClothingItem item)throws SQLException{

        String sql="""
            UPDATE clothing_items
            SET name=?,color=?,brand=?,image_path=?,style=?,
                sleeve_type=?,fit_type=?,footwear_type=?
            WHERE id=?
            """;


        try(Connection c=DBConnection.getConnection();
            PreparedStatement s=c.prepareStatement(sql)){

            s.setString(1,item.getName());
            s.setString(2,item.getColor());
            s.setString(3,item.getBrand());
            s.setString(4,item.getImagePath());
            s.setString(5,item.getStyle().name());

            setExtraFields(s,item,6);

            s.setString(9,item.getId());

            s.executeUpdate();
        }
    }



    public List<ClothingItem> findByUserId(String userId) throws SQLException{

        List<ClothingItem> items=new ArrayList<>();

        String sql="""
            SELECT *
            FROM clothing_items
            WHERE user_id=?
            """;


        try(Connection c=DBConnection.getConnection();
            PreparedStatement s=c.prepareStatement(sql)){

            s.setString(1,userId);

            ResultSet r=s.executeQuery();

            while(r.next())
                items.add(mapClothingItem(r));
        }

        return items;
    }



    public ClothingItem findByClothingId(String id) throws SQLException{

        String sql="""
            SELECT *
            FROM clothing_items
            WHERE id=?
            """;


        try(Connection c=DBConnection.getConnection();
            PreparedStatement s=c.prepareStatement(sql)){

            s.setString(1,id);

            ResultSet r=s.executeQuery();

            if(r.next())
                return mapClothingItem(r);
        }

        return null;
    }



    public void delete(String id)throws SQLException{

        String sql="""
            DELETE FROM clothing_items
            WHERE id=?
            """;


        try(Connection c=DBConnection.getConnection();
            PreparedStatement s=c.prepareStatement(sql)){

            s.setString(1,id);
            s.executeUpdate();
        }
    }



    private void setExtraFields(PreparedStatement s, ClothingItem item, int start)throws SQLException{

        if(item instanceof Top top){

            s.setString(start,top.getSleeveType());
            s.setString(start+1,null);
            s.setString(start+2,null);

        }

        else if(item instanceof Bottom bottom){

            s.setString(start,null);
            s.setString(start+1,bottom.getFitType());
            s.setString(start+2,null);

        }

        else if(item instanceof Footwear footwear){

            s.setString(start,null);
            s.setString(start+1,null);
            s.setString(start+2,footwear.getType());

        }
    }


    private String getCategory(ClothingItem item){

        if(item instanceof Top)
            return "TOP";

        if(item instanceof Bottom)
            return "BOTTOM";

        if(item instanceof Footwear)
            return "FOOTWEAR";

        return "UNKNOWN";
    }


    private ClothingItem mapClothingItem(ResultSet r) throws SQLException{

        String category=r.getString("category");

        String id=r.getString("id");
        String userId=r.getString("user_id");
        String name=r.getString("name");
        String color=r.getString("color");
        String brand=r.getString("brand");
        String image=r.getString("image_path");

        ClothingStyle style=
            ClothingStyle.valueOf(
                    r.getString("style")
            );


        return switch(category){

            case "TOP" -> new Top(
                id,userId,name,color,brand,image,
                style,
                r.getString("sleeve_type")
            );

            case "BOTTOM" -> new Bottom(
                id,userId,name,color,brand,image,
                style,
                r.getString("fit_type")
            );

            case "FOOTWEAR" -> new Footwear(
                id,userId,name,color,brand,image,
                style,
                r.getString("footwear_type")
            );

            default ->
                throw new SQLException(
                    "Unknown category: "+category
                );
        };
    }
}