package main.persistence;

import main.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

// Responsible for writing book data from the Catalogue to the database
public class Writer {
    private final Connection connection;
    private final Set<Book> addedBooks = new HashSet<>();
    private final Set<Book> removedBooks = new HashSet<>();

    public Writer(Connection connection) {
        this.connection = connection;
    }

    public void addBook(Book book) throws SQLException {
        String query = "SELECT * FROM books WHERE title = ? AND author = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, book.getTitle());
        pstmt.setString(2, book.getAuthor());
        ResultSet rs = pstmt.executeQuery();

        if (!rs.next()) {
            query = "INSERT INTO books (title, author, publication_year, publisher, page_count, is_available) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, book.getPublicationYear());
            pstmt.setString(4, book.getPublisher());
            pstmt.setInt(5, book.getPageCount());
            pstmt.setBoolean(6, book.isAvailable());
            pstmt.executeUpdate();
            pstmt.close();
        }
    }
    public void removeBook(Book book) {
        removedBooks.add(book);
    }

    public void write() throws SQLException {
        // Write added books to database
        for (Book book : addedBooks) {
            String query = "INSERT INTO books (title, author, publication_year, publisher, page_count, is_available) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, book.getPublicationYear());
            pstmt.setString(4, book.getPublisher());
            pstmt.setInt(5, book.getPageCount());
            pstmt.setBoolean(6, book.isAvailable());
            pstmt.executeUpdate();
            pstmt.close();
        }

        // Remove removed books from database
        for (Book book : removedBooks) {
            String query = "DELETE FROM books WHERE title = ? AND author = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.executeUpdate();
            pstmt.close();
        }

        // Clear added and removed books
        addedBooks.clear();
        removedBooks.clear();
    }

    public void updateBook(Book book) throws SQLException {
        String query = "UPDATE books SET publication_year = ?, publisher = ?, page_count = ?, is_available = ? WHERE title = ? AND author = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, book.getPublicationYear());
        pstmt.setString(2, book.getPublisher());
        pstmt.setInt(3, book.getPageCount());
        pstmt.setBoolean(4, book.isAvailable());
        pstmt.setString(5, book.getTitle());
        pstmt.setString(6, book.getAuthor());
        pstmt.executeUpdate();
        pstmt.close();
    }
}