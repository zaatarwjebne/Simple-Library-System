package main.model;

// Represents a book with its details
public class Book {
        // The title of the book
        private final String title;

        // The author of the book
        private final String author;

        // The year the book was published
        private final int publicationYear;

        // The publisher of the book
        private final String publisher;

        // The total number of pages in the book
        private final int pageCount;

        // Indicates whether the book is currently available
        private boolean isAvailable;

        /**
         * Creates a new book with the specified details.
         *
         * @param title           the title of the book
         * @param author          the author of the book
         * @param publicationYear the year the book was published
         * @param publisher       the publisher of the book
         * @param pageCount       the total number of pages in the book
         * @param isAvailable     indicates the initial availability of the book
         */
        public Book(String title, String author, int publicationYear, String publisher, int pageCount, boolean isAvailable) {
                this.title = title; // Assigns the book's title
                this.author = author; // Assigns the book's author
                this.publicationYear = publicationYear; // Assigns the publication year
                this.publisher = publisher; // Assigns the publisher's name
                this.pageCount = pageCount; // Assigns the total number of pages
                this.isAvailable = isAvailable; // Sets the availability status
        }

        /**
         * Returns the title of the book.
         *
         * @return the title of the book
         */
        public String getTitle() {
                return title; // Returns the title
        }

        /**
         * Returns the author of the book.
         *
         * @return the author of the book
         */
        public String getAuthor() {
                return author; // Returns the author
        }

        /**
         * Returns the publication year of the book.
         *
         * @return the publication year of the book
         */
        public int getPublicationYear() {
                return publicationYear; // Returns the publication year
        }

        /**
         * Returns the publisher of the book.
         *
         * @return the publisher of the book
         */
        public String getPublisher() {
                return publisher; // Returns the publisher
        }

        /**
         * Returns the total page count of the book.
         *
         * @return the total page count of the book
         */
        public int getPageCount() {
                return pageCount; // Returns the page count
        }

        /**
         * Returns true if the book is available, false otherwise.
         *
         * @return true if the book is available; false otherwise
         */
        public boolean isAvailable() {
                return isAvailable; // Returns availability status
        }

        /**
         * Sets the availability status of the book.
         *
         * @param availability the new availability status of the book
         */
        public void setAvailability(boolean availability) {
                this.isAvailable = availability; // Updates the availability status
        }
}
