package main.ui;

import main.model.Book;
import main.model.Catalogue;
import main.persistence.MySQLConnector;
import main.persistence.Reader;
import main.persistence.Writer;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class GUI extends JFrame implements ActionListener {

    private Catalogue catalogue;
    private Connection connection;


    private JPanel mainMenu;
    private JButton button1;
    private JButton button2;
    private JButton button3;

    private JButton button6;

    private JPanel bookListingsPanel;
    private JLabel listings;
    private JPanel listingsPage;
    private JButton addBook;
    private JTextField t1;
    private JTextField t2;
    private JTextField t3;
    private JTextField t4;
    private JTextField t5;

    private JLabel title;
    private JLabel author;
    private JLabel publicationYear;
    private JLabel publisher;
    private JLabel pageCount;
    private JButton searchButton;
    private JButton removeBookButton;
    private JButton borrowBookButton;
    private JButton returnBookButton;

    // Makes a new JFrame with different attributes
    public GUI() {
        super("Library App");
        connection = MySQLConnector.getConnection();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 500));
        initializeMenu();
        makeBookListingsPanel();
        makeListYourBookPanel();

        JLabel welcomeLabel = new JLabel("Library Portal");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 5)); // Change the font size to 10
        JLabel mainScreenImage = new JLabel();
        addLabel(welcomeLabel);
        addImageToLabel(mainScreenImage);

        initializeMenuButtons();

        addButtons(button1, button2, button3, button6);

        addActionToButton();

        mainMenu.setVisible(true);
        connection = MySQLConnector.getConnection();
        catalogue = new Catalogue(connection);

        try {
            catalogue = Reader.readBooks(connection);
        } catch (SQLException e) {
            System.out.println("Error reading books from database: " + e.getMessage());
        }
    }

    // EFFECTS: Makes the main menu panel and changes the background color
    public void initializeMenu() {
        mainMenu = new JPanel();
        mainMenu.setBackground(Color.decode("#00F350"));
        add(mainMenu);
        listings = new JLabel();
        listings.setText("The Shelves are Bare...");
        loadBookListings();
    }

    // EFFECTS: Initializes main menu buttons and gives them labels
    public void initializeMenuButtons() {
        button1 = new JButton("Peruse the Shelves");
        button2 = new JButton("Donate a Tome");
        button3 = new JButton("Book Actions");
        button6 = new JButton("Depart the Library");
    }

    // MODIFIES: this
    // EFFECTS: adds buttons to mainMenu
    public void addButton(JButton button1, JPanel panel) {
        button1.setFont(new Font("Arial", Font.BOLD, 12));
        button1.setBackground(Color.WHITE);
        panel.add(button1);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: Calls the addButton method for each argument
    public void addButtons(JButton button1, JButton button2, JButton button3, JButton button6) {

        addButton(button1, mainMenu);
        addButton(button2, mainMenu);
        addButton(button3, mainMenu);
        addButton(button6, mainMenu);
    }

    // EFFECTS: Creates a button and adds it to the given panel, changing various attributes of the
    //          color and text of the button
    public void addMenuButton(JButton button1, JPanel panel) {
        button1.setFont(new Font("Arial", Font.BOLD, 12));
        button1.setBackground(Color.BLACK);
        button1.setForeground(Color.WHITE);
        panel.add(button1);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: Creates the welcome text label and adds it to the main menu panel
    public void addLabel(JLabel j1) {
        j1.setFont(new Font("ComicSans", Font.BOLD, 50));
        mainMenu.add(j1);
    }

    // EFFECTS: Creates the image on the main menu and its it to the panel
    public void addImageToLabel(JLabel j1) {
        j1.setIcon(new ImageIcon("./src/main/ui/data/image.jpg"));
        j1.setPreferredSize(new Dimension(500, 300));
        mainMenu.add(j1);
    }

    // MODIFIES: this
    // EFFECTS: Sets each button to their respective action
    public void addActionToButton() {

        button1.addActionListener(this);
        button1.setActionCommand("View listings");
        button2.addActionListener(this);
        button2.setActionCommand("List your book");
        button3.addActionListener(this);
        button6.setActionCommand("Exit application");

    }

    // EFFECTS: calls the given methods when a certain button is clicked on
    public void actionPerformed(ActionEvent ae) {

        if (ae.getActionCommand().equals("View listings")) {
            initializeBookListingsPanel();
            loadBookListings();
        } else if (ae.getActionCommand().equals("List your book")) {
            initializeListingsPanel();
        } else if (ae.getActionCommand().equals("Save listings file")) {
            saveBookListings();
        } else if (ae.getActionCommand().equals("Exit application")) {
            saveBookListings();
            System.exit(0);
        } else if (ae.getActionCommand().equals("Return to main menu")) {
            returnToMainMenu();
        } else if (ae.getActionCommand().equals("Add Book to listings")) {
            addBookToListings();
        } else if (ae.getActionCommand().equals("Load listings file")) {
            loadBookListings();
        }
        else if (ae.getActionCommand().equals("Search book")) {
            searchBook();
        } else if (ae.getActionCommand().equals("Remove book")) {
            removeBook();
        } else if (ae.getActionCommand().equals("Borrow book")) {
            borrowBook();
        } else if (ae.getActionCommand().equals("Return book")) {
            returnBook();
        }     else if (ae.getActionCommand().equals("Book Actions")) {
            makeBookActionsPanel();
            initializeBookActionsPanel();
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates the panel that displays the option for the user to input their book
    public void makeListYourBookPanel() {

        listingsPage  = new JPanel(new GridLayout(0, 2));
        JButton mainMenuButton = new JButton("Return to Main Menu");
        mainMenuButton.setActionCommand("Return to main menu");
        mainMenuButton.addActionListener(this);
        addMenuButton(mainMenuButton, listingsPage);

        createListingsPage();
        createListingsPage();
        addLabelsToListings();
    }

    // EFFECTS: Creates the panel that displays the option for the user to input their book actions
    public void makeBookActionsPanel() {

        listingsPage  = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        JButton mainMenuButton = new JButton("Return to Main Menu");
        mainMenuButton.setActionCommand("Return to main menu");
        mainMenuButton.addActionListener(this);
        topPanel.add(mainMenuButton);
        listingsPage.add(topPanel, BorderLayout.NORTH);

        createBookActionsPage();
        addLabelsToBookActions();
    }


    // EFFECTS: Generates the fields for the user to type into
    public void createBookActionsPage() {

        searchButton = new JButton("Search");
        searchButton.setActionCommand("Search book");
        searchButton.addActionListener(this);

        removeBookButton = new JButton("Remove Book");
        removeBookButton.setActionCommand("Remove book");
        removeBookButton.addActionListener(this);

        borrowBookButton = new JButton("Borrow Book");
        borrowBookButton.setActionCommand("Borrow book");
        borrowBookButton.addActionListener(this);

        returnBookButton = new JButton("Return Book");
        returnBookButton.setActionCommand("Return book");
        returnBookButton.addActionListener(this);

        title = new JLabel("Book title:");
        t1 = new JTextField(10);
        author = new JLabel("Author:");
        t2 = new JTextField(10);

        bookActionsLabelSettings();

    }

    // EFFECTS: Adds the user input labels onto the panel
    public void addLabelsToBookActions() {

        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        inputPanel.add(title);
        inputPanel.add(t1);
        inputPanel.add(author);
        inputPanel.add(t2);
        listingsPage.add(inputPanel, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(listings, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel actionPanel = new JPanel();
        actionPanel.add(searchButton);
        actionPanel.add(removeBookButton);
        actionPanel.add(borrowBookButton);
        actionPanel.add(returnBookButton);
        listingsPage.add(actionPanel, BorderLayout.SOUTH);
        listingsPage.add(scroll,BorderLayout.EAST);
    }

    // EFFECTS: Changes certain attributes of the labels and text fields
    private void bookActionsLabelSettings() {

        searchButton.setBackground(Color.BLACK);
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 12));

        removeBookButton.setBackground(Color.BLACK);
        removeBookButton.setForeground(Color.WHITE);
        removeBookButton.setFont(new Font("Arial", Font.BOLD, 12));

        borrowBookButton.setBackground(Color.BLACK);
        borrowBookButton.setForeground(Color.WHITE);
        borrowBookButton.setFont(new Font("Arial", Font.BOLD, 12));

        returnBookButton.setBackground(Color.BLACK);
        returnBookButton.setForeground(Color.WHITE);
        returnBookButton.setFont(new Font("Arial", Font.BOLD, 12));

        title.setFont(new Font("ComicSans", Font.BOLD, 24));
        author.setFont(new Font("ComicSans", Font.BOLD, 24));

        t1.setMaximumSize(new Dimension(1200, 400));
        t2.setMaximumSize(new Dimension(1200, 400));
    }

    // EFFECTS: Adds the bookActions panel to the screen,
// and sets the other ones false, so they are not visible to the user
    public void initializeBookActionsPanel() {
        add(listingsPage);
        listingsPage.setVisible(true);
        mainMenu.setVisible(false);
        bookListingsPanel.setVisible(false);
    }


    // EFFECTS: Searches the database by title and author
    public void searchBook() {
        Book book = catalogue.searchBook(t1.getText(), t2.getText());
        if (book != null) {
            // display book details
            System.out.println("Book found: " + book.getTitle() + " by " + book.getAuthor());
        } else {
            System.out.println("Book not found");
        }
    }

    // EFFECTS: Removes a book from the database
    public void removeBook() {
        catalogue.removeBook(t1.getText(), t2.getText());
        System.out.println("Book removed");
    }

    // EFFECTS: Borrows a book from the database
    public void borrowBook() {
        catalogue.borrowBook(t1.getText(), t2.getText());
        System.out.println("Book borrowed");
    }

    // EFFECTS: Returns a book to the database
    public void returnBook() {
        catalogue.returnBook(t1.getText(), t2.getText());
        System.out.println("Book borrowed");
    }

    // EFFECTS: Adds the listings page to the screen, and sets the other ones false, so they are not visible to the user
    public void initializeListingsPanel() {
        add(listingsPage);
        listingsPage.setVisible(true);
        mainMenu.setVisible(false);
        bookListingsPanel.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: Generates the fields for the user to type into
    public void createListingsPage() {

        addBook = new JButton("Add Book to listings");
        addBook.setActionCommand("Add Book to listings");
        addBook.addActionListener(this);

        title = new JLabel("Book title:");
        t1 = new JTextField(10);
        author = new JLabel("Author:");
        t2 = new JTextField(10);
        publicationYear = new JLabel("Publishing year:");
        t3 = new JTextField(10);
        publisher = new JLabel("Publisher");
        t4 = new JTextField(10);
        pageCount = new JLabel("Page count");
        t5 = new JTextField(10);

        listingLabelSettings();

    }

    // EFFECTS: Adds the user input labels onto the panel
    public void addLabelsToListings() {

        listingsPage.add(addBook);
        listingsPage.add(title);
        listingsPage.add(t1);
        listingsPage.add(author);
        listingsPage.add(t2);
        listingsPage.add(publicationYear);
        listingsPage.add(t3);
        listingsPage.add(publisher);
        listingsPage.add(t4);
        listingsPage.add(pageCount);
        listingsPage.add(t5);
    }

    // EFFECTS: Changes certain attributes of the labels and text fields
    private void listingLabelSettings() {

        addBook.setBackground(Color.BLACK);
        addBook.setForeground(Color.WHITE);
        addBook.setFont(new Font("Arial", Font.BOLD, 12));

        title.setFont(new Font("ComicSans", Font.BOLD, 24));
        author.setFont(new Font("ComicSans", Font.BOLD, 24));
        publicationYear.setFont(new Font("ComicSans", Font.BOLD, 24));
        publisher.setFont(new Font("ComicSans", Font.BOLD, 24));
        pageCount.setFont(new Font("ComicSans", Font.BOLD, 24));

        t1.setMaximumSize(new Dimension(1200, 400));
        t2.setMaximumSize(new Dimension(1200, 400));
        t3.setMaximumSize(new Dimension(1200, 400));
        t4.setMaximumSize(new Dimension(1200, 400));
        t5.setMaximumSize(new Dimension(1200, 400));
    }

    // MODIFIES: this
    // EFFECTS: Adds the user given listing into the Catalogue object to be displayed
    public void addBookToListings() {

        try {
            Book user = new Book(t1.getText(), t2.getText(), Integer.parseInt(t3.getText()),
                    t4.getText(), Integer.parseInt(t5.getText()), true);
            catalogue.addBook(user);
            // Uses HTML tags to create a multi line text label
            listings.setText("<html><pre>Current Listings: \n" + catalogue.getListings() + "\n</pre></html>");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input, please try again");
        } catch (IndexOutOfBoundsException e) {
            listings.setText("Please initialize listings file before proceeding");
        }

    }


    // MODIFIES: this
    // EFFECTS: Creates the panel that displays the current listings
    public void makeBookListingsPanel() {

        bookListingsPanel = new JPanel(new GridLayout(2, 1));
        JScrollPane scroll = new JScrollPane(listings, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton mainMenuButton = new JButton("Return to Main Menu");
        mainMenuButton.setActionCommand("Return to main menu");
        mainMenuButton.addActionListener(this);
        addMenuButton(mainMenuButton, bookListingsPanel);

        listings.setFont(new Font("ComicSans", Font.BOLD, 12));
        bookListingsPanel.add(scroll);
    }

    // EFFECTS: Adds the bookListings panel to the screen,
    // and sets the other ones false, so they are not visible to the user
    public void initializeBookListingsPanel() {
        add(bookListingsPanel);
        bookListingsPanel.setVisible(true);
        mainMenu.setVisible(false);
        listingsPage.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: loads the book listings from database if it exists,
    // otherwise prints error
    private void loadBookListings() {
        try {
            catalogue = Reader.readBooks(connection);
            listings.setText("<html><pre>Current Listings: \n" + catalogue.getListings() + "\n</pre></html>");
        } catch (SQLException e) {
            System.out.println("Error loading book listings from database: " + e.getMessage());
        }
    }

    // EFFECTS: saves state of the book listings with the user added listings
    private void saveBookListings() {
        try {
            Writer writer = new Writer(connection);
            writer.write();
        } catch (SQLException e) {
            System.out.println("Error saving book listings to database: " + e.getMessage());
        }
    }


    // EFFECTS: Sets all panels' visibility to false except for the main menu
    public void returnToMainMenu() {
        mainMenu.setVisible(true);
        bookListingsPanel.setVisible(false);
        listingsPage.setVisible(false);
    }

}