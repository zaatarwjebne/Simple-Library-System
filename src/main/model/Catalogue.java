package main.model;

import main.persistence.Saveable;
import main.persistence.Writer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

// Represents a catalogue of books that can be saved to a database
public class Catalogue implements Saveable {

    // List to hold all the Book objects in the catalogue
    private final ArrayList<Book> listOfBooks;
    private final Writer writer;

    // Constructs a Catalogue with the specified database connection
    public Catalogue(Connection connection) {
        // Database connection for saving the catalogue
        writer = new Writer(connection);
        listOfBooks = new ArrayList<>(); // Initializes the list of books
    }

    /**
     * Adds the specified book to the catalogue.
     * <p>
     * MODIFIES: this
     * REQUIRES: adds a Book object to the list
     * EFFECTS: adds the specified book to the catalogue
     *
     * @param book the Book object to be added to the catalogue
     */
    public void addBook(Book book) {
        listOfBooks.add(book);
        try {
            writer.addBook(book);
            writer.write();
        } catch (SQLException e) {
            System.out.println("Error adding book to database: " + e.getMessage());
        }
    } // Adds the book to the list of books

    /**
     * Returns the number of books in the catalogue.
     * <p>
     * EFFECTS: returns the total count of books in the catalogue
     *
     * @return the number of books in the catalogue
     */
    public int getNumberOfBooksInCatalogue() {
        return listOfBooks.size(); // Returns the size of the list
    }

    /**
     * Lists all the books in the catalogue with their details.
     * <p>
     * EFFECTS: returns a formatted string containing details of all books
     *
     * @return a string representation of all book listings
     */
    public String getListings() {
        String listings = ""; // Initializes an empty string for listings
        // Iterates through each book in the catalogue
        for (int i = 0; i < getNumberOfBooksInCatalogue(); i++) {
            // Appends book details to the listings string
            listings += "Book #" + i + "\n"
                    + listOfBooks.get(i).getTitle() + "\n"
                    + listOfBooks.get(i).getAuthor() + "\n"
                    + listOfBooks.get(i).getPublicationYear() + "\n"
                    + listOfBooks.get(i).getPublisher() + "\n"
                    + listOfBooks.get(i).getPageCount() + " pages" + "\n"
                    + "Availability: " + (listOfBooks.get(i).isAvailable() ? "Yes" : "No") + "\n" + "\n";
        }
        return listings; // Returns the formatted string of book listings
    }

    /**
     * Removes the specified book from the catalogue if it exists.
     * <p>
     * EFFECTS: removes the specified book if it is in the catalogue,
     * returns false if the book was removed, otherwise returns true
     *
     * @param book the Book object to be removed
     */
    public void removeBook(Book book) {
        listOfBooks.remove(book);
        try {
            writer.removeBook(book);
            writer.write();
        } catch (SQLException e) {
            System.out.println("Error removing book from database: " + e.getMessage());
        }
    }

    /**
     * Saves the current catalogue to the database using the provided PreparedStatement.
     * <p>
     * MODIFIES: pstmt
     * EFFECTS: writes each book's details to the database
     *
     * @param pstmt the PreparedStatement used to save book details
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void save(PreparedStatement pstmt) throws SQLException {
        // Iterates through each book in the list to save its details
        for (Book book : listOfBooks) {
            pstmt.setString(1, book.getTitle()); // Sets the title parameter
            pstmt.setString(2, book.getAuthor()); // Sets the author parameter
            pstmt.setInt(3, book.getPublicationYear()); // Sets the publication year parameter
            pstmt.setString(4, book.getPublisher()); // Sets the publisher parameter
            pstmt.setInt(5, book.getPageCount()); // Sets the page count parameter
            pstmt.setBoolean(6, book.isAvailable()); // Sets the availability parameter
            pstmt.executeUpdate(); // Executes the update for the current book
        }
    }

    /**
     * Searches for a book in the catalogue by title and author.
     * <p>
     * EFFECTS: returns the book if found, otherwise returns null
     *
     * @param title the title of the book to be searched
     * @param author the author of the book to be searched
     * @return the book if found, otherwise null
     */
    public Book searchBook(String title, String author) {
        for (Book book : listOfBooks) {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                return book;
            }
        }
        return null;
    }

    /**
     * Borrows a book from the catalogue by title and author.
     * <p>
     * EFFECTS: sets the book's availability to false if found
     *
     * @param title the title of the book to be borrowed
     * @param author the author of the book to be borrowed
     */
    public void borrowBook(String title, String author) {
        for (Book book : listOfBooks) {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                book.setAvailability(false);
                try {
                    writer.updateBook(book);
                    writer.write();
                } catch (SQLException e) {
                    System.out.println("Error borrowing book from database: " + e.getMessage());
                }
                return;
            }
        }
    }

    /**
     * Returns a book to the catalogue by title and author.
     * <p>
     * EFFECTS: sets the book's availability to true if found
     *
     * @param title the title of the book to be returned
     * @param author the author of the book to be returned
     */
    public void returnBook(String title, String author) {
        for (Book book : listOfBooks) {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                book.setAvailability(true);
                try {
                    writer.updateBook(book);
                    writer.write();
                } catch (SQLException e) {
                    System.out.println("Error returning book to database: " + e.getMessage());
                }
                return;
            }
        }
    }

    public void removeBook(String title, String author) {
        try {
            Book removedbook = searchBook(title, author);
            writer.removeBook(removedbook);
            writer.write();
            for (Book book : listOfBooks) {
                if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                    listOfBooks.remove(book);
                    return;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error removing book from database: " + e.getMessage());
        }
        System.out.println("Book not found in catalogue");
    }
}
