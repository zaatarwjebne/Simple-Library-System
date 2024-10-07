package main.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Handles the connection to the MySQL database for the library catalogue
public class MySQLConnector {
    // Database URL for connecting to the MySQL database
    private static final String DB_URL = "jdbc:mysql://localhost:3306/librarycatalogue";

    // Username for accessing the database
    private static final String USER = "zaatar";

    // Password for accessing the database
    private static final String PASS = "appleapple";

    /**
     * Establishes and returns a connection to the database.
     * <p>
     * EFFECTS: Attempts to load the MySQL JDBC driver and establish a connection
     *          to the database. Returns a Connection object if successful,
     *          or null if an error occurs during the process.
     *
     * @return a Connection object to the database, or null if the connection fails
     */
    public static Connection getConnection() {
        try {
            // Loads the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Returns a new connection to the database
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            // Handles SQL exceptions during the connection process
            System.out.println("Error connecting to database: " + e.getMessage());
            return null; // Returns null if the connection fails
        } catch (ClassNotFoundException e) {
            // Handles exceptions when the JDBC driver is not found
            System.out.println("Error loading MySQL driver: " + e.getMessage());
            return null; // Returns null if the driver cannot be loaded
        }
    }
}
