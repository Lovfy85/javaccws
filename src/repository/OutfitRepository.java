package repository;

import db.DBConnection;

import model.*;
import model.clothing.*;

import exception.InvalidClothingException;

import java.sql.*;

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


    public Outfit findById(String outfitId) throws SQLException, InvalidClothingException {

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