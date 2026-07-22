package repository;

import db.DBConnection;

import model.*;
import model.clothing.*;

import exception.InvalidClothingException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OutfitRepository {

    private WardrobeRepository wardrobeRepository = new WardrobeRepository();


    public void save(String outfitId, String userId, Outfit outfit) throws SQLException {

        String sql = """
                INSERT INTO outfits
                (id, user_id, top_id, bottom_id, footwear_id, score)
                VALUES (?, ?, ?, ?, ?, ?)
                """;


        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(
                    1,
                    outfitId
            );


            statement.setString(
                    2,
                    userId
            );


            statement.setString(
                    3,
                    outfit.getTop().getId()
            );


            statement.setString(
                    4,
                    outfit.getBottom().getId()
            );


            statement.setString(
                    5,
                    outfit.getFootwear().getId()
            );


            statement.setInt(
                    6,
                    outfit.getScore()
            );


            statement.executeUpdate();

        }

    }



    public boolean exists(
            String userId,
            String topId,
            String bottomId,
            String footwearId
    ) throws SQLException {


        String sql = """
                SELECT COUNT(*)
                FROM outfits
                WHERE user_id = ?
                AND top_id = ?
                AND bottom_id = ?
                AND footwear_id = ?
                """;


        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, userId);
            statement.setString(2, topId);
            statement.setString(3, bottomId);
            statement.setString(4, footwearId);


            ResultSet result = statement.executeQuery();


            if(result.next()) {

                return result.getInt(1) > 0;

            }

        }


        return false;
    }




    public Outfit findById(String outfitId)
            throws SQLException, InvalidClothingException {


        String sql = """
            SELECT *
            FROM outfits
            WHERE id = ?
            """;


        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(
                    1,
                    outfitId
            );


            ResultSet result =
                    statement.executeQuery();



            if(result.next()) {


                String topId =
                        result.getString("top_id");


                String bottomId =
                        result.getString("bottom_id");


                String footwearId =
                        result.getString("footwear_id");



                int score =
                        result.getInt("score");



                Top top =
                        (Top) wardrobeRepository.findByClothingId(topId);



                Bottom bottom =
                        (Bottom) wardrobeRepository.findByClothingId(bottomId);



                Footwear footwear =
                        (Footwear) wardrobeRepository.findByClothingId(footwearId);



                Outfit outfit =
                        new Outfit(
                                result.getString("id"),
                                result.getString("user_id"),
                                top,
                                bottom,
                                footwear
                        );



                outfit.setScore(score);



                return outfit;

            }

        }


        return null;

    }




    public List<Outfit> findByUserId(String userId)
            throws SQLException, InvalidClothingException {


        List<Outfit> outfits = new ArrayList<>();


        String sql = """
                SELECT id
                FROM outfits
                WHERE user_id = ?
                """;



        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(
                    1,
                    userId
            );


            ResultSet result =
                    statement.executeQuery();



            while(result.next()) {


                Outfit outfit =
                        findById(
                                result.getString("id")
                        );


                if(outfit != null) {

                    outfits.add(outfit);

                }

            }

        }


        return outfits;

    }

    
    public void delete(String outfitId) throws SQLException {


        String sql = """
            DELETE FROM outfits
            WHERE id = ?
            """;


        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {



            statement.setString(
                    1,
                    outfitId
            );


            statement.executeUpdate();

        }

    }

}