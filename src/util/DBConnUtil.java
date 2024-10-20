package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Register the MySQL driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                String connectionString = PropertyUtil.getPropertyString("resources/db.properties");

                connection = DriverManager.getConnection(connectionString);
            } catch (ClassNotFoundException e) {
                System.out.println("MySQL JDBC Driver not found.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Connection Failed!");
                e.printStackTrace();
            }
        }
        return connection;
    }
}