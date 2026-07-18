package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionTest {

    public static void main(String[] args) {

        try {

            Connection connection =
                DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/",
                        "root",
                        "solaire85"
                );

            System.out.println("Connected successfully!");
            connection.close();

        } catch(Exception e) {
            
            e.printStackTrace();
        }
    }
}
