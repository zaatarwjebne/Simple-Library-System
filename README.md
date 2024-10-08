# Simple Library System

A Java-based application for managing a library system that allows users to add, search, and manage books and patrons. This project uses **MySQL** as the database backend to store and retrieve library records.

## Features

- **Book Management**: Add, remove, and search for books.
- **Patron Management**: Add and remove library patrons.
- **Catalogue**: View all books available in the library.
- **GUI Interface**: A simple graphical user interface for interacting with the system.
- **Persistence**: Data is saved and loaded using a MySQL database.
  
## Technologies Used

- **Java**: Core programming language for building the system.
- **MySQL**: Used as the backend database for storing book and patron records.
- **JUnit**: For unit testing to ensure code quality and functionality.
- **Maven**: To manage project dependencies.

## Prerequisites

- **Java 8+**
- **MySQL 8+**: Set up a MySQL server and configure the database connection in `MySQLConnector.java`.
- **IDE (e.g., IntelliJ IDEA, Eclipse)**: To run and modify the project.

## Setup and Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/zaatarwjebne/Simple-Library-System.git
    ```
2. Import the project into your preferred IDE.
3. Set up the MySQL database by creating a database and adjusting the connection credentials in `src/persistence/MySQLConnector.java`.
4. Install necessary dependencies via Maven:
    ```bash
    mvn install
    ```
5. Run the project: execute `Main.java` 

## Database Configuration

In the `MySQLConnector.java` file, modify the following fields to match your MySQL setup:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database";
private static final String USER = "your_username";
private static final String PASS = "your_password";
