package repository;

import db.DBConnection;

import model.*;
import model.clothing.*;

import java.sql.*;

import java.util.*;

public class WardrobeRepository {


    public void save(ClothingItem item, String userId) throws SQLException {


        String sql = """
                INSERT INTO clothing_items
                (id, user_id, category, name, color, brand, image_path, style,
                 sleeve_type, fit_type, footwear_type)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;


        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, item.getId());
            statement.setString(2, userId);
            statement.setString(3, getCategory(item));
            statement.setString(4, item.getName());
            statement.setString(5, item.getColor());
            statement.setString(6, item.getBrand());
            statement.setString(7, item.getImagePath());
            statement.setString(8, item.getStyle().name());


            if(item instanceof Top top) {

                statement.setString(9, top.getSleeveType());
                statement.setString(10, null);
                statement.setString(11, null);

            }

            else if(item instanceof Bottom bottom) {

                statement.setString(9, null);
                statement.setString(10, bottom.getFitType());
                statement.setString(11, null);

            }

            else if(item instanceof Footwear footwear) {

                statement.setString(9, null);
                statement.setString(10, null);
                statement.setString(11, footwear.getType());

            }

            statement.executeUpdate();

        }

    }


    public List<ClothingItem> findByUserId(String userId) throws SQLException {

        List<ClothingItem> items = new ArrayList<>();

        String sql = """
            SELECT *
            FROM clothing_items
            WHERE user_id = ?
            """;


        try(Connection connection = DBConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, userId);

            ResultSet result = statement.executeQuery();

            while(result.next()) {

                items.add(mapClothingItem(result));

            }

        }

        return items;

    }


    public ClothingItem findByClothingId(String clothingId) throws SQLException {

        String sql = """
            SELECT *
            FROM clothing_items
            WHERE id = ?
            """;

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, clothingId);


            ResultSet result = statement.executeQuery();


            if(result.next()) {

                return mapClothingItem(result);

            }

        }

        return null;

    }


    public void delete(String clothingId) throws SQLException {


        String sql = """
            DELETE FROM clothing_items
            WHERE id = ?
            """;


        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, clothingId);

            statement.executeUpdate();

        }

    }


    private String getCategory(ClothingItem item) {

        if(item instanceof Top) {

            return "TOP";

        }

        if(item instanceof Bottom) {

            return "BOTTOM";

        }

        if(item instanceof Footwear) {

            return "FOOTWEAR";

        }

        return "UNKNOWN";

    }


    private ClothingItem mapClothingItem(ResultSet result) throws SQLException {

        String id = result.getString("id");

        String userId = result.getString("user_id");

        String name = result.getString("name");

        String color = result.getString("color");

        String brand = result.getString("brand");

        String imagePath = result.getString("image_path");

        ClothingStyle style = ClothingStyle.valueOf(result.getString("style"));

        String category = result.getString("category");

        String sleeveType = result.getString("sleeve_type");

        String fitType = result.getString("fit_type");

        String footwearType = result.getString("footwear_type");


        switch(category) {

            case "TOP":

                return new Top(
                    id,
                    userId,
                    name,
                    color,
                    brand,
                    imagePath,
                    style,
                    sleeveType
                );


            case "BOTTOM":

                return new Bottom(
                    id,
                    userId,
                    name,
                    color,
                    brand,
                    imagePath,
                    style,
                    fitType
                );


            case "FOOTWEAR":

                return new Footwear(
                    id,
                    userId,
                    name,
                    color,
                    brand,
                    imagePath,
                    style,
                    footwearType
                );


            default:

                throw new SQLException(
                        "Unknown clothing category: " + category
                );

        }

    }

}