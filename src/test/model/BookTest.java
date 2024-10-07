package test.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.Book;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    private Book testBook;

    @BeforeEach
    public void runBefore() {
        testBook = new Book("To Kill a Mockingbird", "Harper Lee", 1960, "J.B. Lippincott & Co.", 281, true);
    }

    @Test
    public void testGetTitle() {
        assertEquals(testBook.getTitle(), "To Kill a Mockingbird");
    }

    @Test
    public void testGetAuthor() {
        assertEquals(testBook.getAuthor(), "Harper Lee");
    }

    @Test
    public void testGetPublicationYear() {
        assertEquals(testBook.getPublicationYear(), 1960);
    }

    @Test
    public void testGetPublisher() {
        assertEquals(testBook.getPublisher(), "J.B. Lippincott & Co.");
    }

    @Test
    public void testGetPageCount() {
        assertEquals(testBook.getPageCount(), 281);
    }

    @Test
    public void testIsAvailable() {
        assertTrue(testBook.isAvailable());
    }

    @Test
    public void testSetAvailability() {
        testBook.setAvailability(false);
        assertFalse(testBook.isAvailable());
    }
}