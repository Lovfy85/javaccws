package repository;

import db.DBConnection;

import model.*;

import java.sql.*;


public class UserRepository {


    public void save(User user) throws SQLException {

        String sql = """
            INSERT INTO users
            (id, username, password_hash, name, preferred_style, color_preference)
            VALUES (?, ?, ?, ?, ?, ?)
            """;


        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, user.getId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPasswordHash());
            statement.setString(4, user.getName());
            statement.setString(5, user.getStylesProfile().getStyle());
            statement.setString(6, user.getStylesProfile().getColorPreference());


            statement.executeUpdate();
        }
    }



    public User findByUsername(String username) throws SQLException {

        String sql = """
            SELECT *
            FROM users
            WHERE username = ?
            """;


        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, username);


            ResultSet result = statement.executeQuery();


            if(result.next()) {

                return mapUser(result);

            }

        }

        return null;
    }


    public User findById(String id) throws SQLException {

        String sql = """
            SELECT *
            FROM users
            WHERE id = ?
            """;


        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, id);


            ResultSet result = statement.executeQuery();


            if(result.next()) {

                return mapUser(result);

            }

        }

        return null;
    }


    public boolean existsByUsername(String username) throws SQLException {

        String sql = """
            SELECT COUNT(*)
            FROM users
            WHERE username = ?
            """;


        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, username);


            ResultSet result = statement.executeQuery();


            if(result.next()) {

                return result.getInt(1) > 0;
            }

        }


        return false;
    }


    private User mapUser(ResultSet result) throws SQLException {


        StylesProfile profile =
            new StylesProfile(
                result.getString("preferred_style"),
                result.getString("color_preference")
            );


        return new User(
            result.getString("id"),
            result.getString("username"),
            result.getString("password_hash"),
            result.getString("name"),
            profile,
            new Wardrobe()
        );
    }
}