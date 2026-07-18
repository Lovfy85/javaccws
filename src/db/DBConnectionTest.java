package db;

import java.sql.Connection;

public class DBConnectionTest {

    public static void main(String[] args) {

        try {

            Connection connection = DBConnection.getConnection();

            System.out.println("Connected successfully!");
            connection.close();

        } catch(Exception e) {
            
            e.printStackTrace();
        }
    }
}
