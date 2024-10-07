package main.persistence;

import main.model.Book;
import main.model.Catalogue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Responsible for reading book data from the database and creating a Catalogue object
public class Reader {

    /**
     * Reads books from the database and returns a Catalogue object containing the books.
     * <p>
     * MODIFIES: catalogue (the Catalogue object populated with books from the database)
     * EFFECTS: Creates a new Catalogue and populates it with Book objects retrieved from the database.
     *          Throws SQLException if an SQL error occurs during the reading process.
     *
     * @param connection the database connection used to read book data
     * @return a Catalogue object containing the books read from the database
     * @throws SQLException if an SQL error occurs during the execution
     */
    public static Catalogue readBooks(Connection connection) throws SQLException {
        // Creates a new Catalogue object with the provided database connection
        Catalogue catalogue = new Catalogue(connection);

        // Using try-with-resources to ensure Statement and ResultSet are closed automatically
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {

            // Iterates through the ResultSet to retrieve book data
            while (rs.next()) {
                // Creates a new Book object using data from the current row of the ResultSet
                Book book = new Book(
                        rs.getString("title"),           // Retrieves the book title
                        rs.getString("author"),          // Retrieves the book author
                        rs.getInt("publication_year"),   // Retrieves the publication year
                        rs.getString("publisher"),       // Retrieves the publisher
                        rs.getInt("page_count"),         // Retrieves the page count
                        rs.getBoolean("is_available")    // Retrieves the availability status
                );
                // Adds the newly created Book object to the Catalogue
                catalogue.addBook(book);
            }
        }
        // Returns the populated Catalogue object
        return catalogue;
    }
}
