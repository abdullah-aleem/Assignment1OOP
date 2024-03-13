import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;
import java.util.Vector;

public class LibraryManagementSystem extends Library {

    public LibraryManagementSystem() {
        File file = new File("books.txt");
        if (file.exists()) {
            try {
                this.loadBooks(file);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        file = new File("users.txt");
        if (file.exists()) {
            try {
                this.loadUsers(file);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public void start() {
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(0, 1));
        frame.getContentPane().setBackground(Color.DARK_GRAY);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        addButton(buttonPanel, "Add Book");
        addButton(buttonPanel, "Add User");
        addButton(buttonPanel, "Checkout Book");
        addButton(buttonPanel, "Return Book");
        addButton(buttonPanel, "Display Inventory");
        addButton(buttonPanel, "Display Users");
        addButton(buttonPanel, "Search Book By Title");
        addButton(buttonPanel, "Search Book By Author");

        frame.add(buttonPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void addButton(Container container, String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Impact", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(150, 53, 2));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.addActionListener(getActionListener(text));
        container.add(button);
    }

    private ActionListener getActionListener(String buttonName) {
        return e -> {
            try {
                switch (buttonName) {
                    case "Add Book":
                        addBookAction();
                        break;
                    case "Add User":
                        addUserAction();
                        break;
                    case "Checkout Book":
                        checkoutBookAction();
                        break;
                    case "Return Book":
                        returnBookAction();
                        break;
                    case "Display Books":
                        displayBooksAction();
                        break;
                    case "Display Users":
                        displayUsersAction();
                        break;
                    case "Search Book By Title":
                        searchBookByTitleAction();
                        break;
                    case "Search Book By Author":
                        searchBookByAuthorAction();
                        break;
                    default:
                        break;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        };
    }

    private void addBookAction() {
        String title = JOptionPane.showInputDialog("Enter title");
        String author = JOptionPane.showInputDialog("Enter author");
        String genre = JOptionPane.showInputDialog("Enter genre");

        if (Objects.equals(title, "") || Objects.equals(author, "") || Objects.equals(genre, "")) {
            throw new RuntimeException("Title, author, or genre not found");
        }

        Book book = new Book(getBooks().size() + 1, title, author, genre, true);
        addBook(book);
        saveBooks(new File("books.txt"));
        saveUsers(new File("users.txt"));
    }

    private void addUserAction() {
        String name = JOptionPane.showInputDialog("Enter name");
        String contactInformation = JOptionPane.showInputDialog("Enter contact information");
        if (Objects.equals(name, "") || Objects.equals(contactInformation, "")) {
            throw new RuntimeException("Name or contact information not found");
        }
        User user = new User(getUsers().size() + 1, name, contactInformation, new Vector<>());
        addUser(user);
        saveBooks(new File("books.txt"));
        saveUsers(new File("users.txt"));
    }

    private void checkoutBookAction() {
        String userID = JOptionPane.showInputDialog("Enter user ID");
        String bookID = JOptionPane.showInputDialog("Enter book ID");
        if (Objects.equals(userID, "") || Objects.equals(bookID, "")) {
            throw new RuntimeException("User or book not found");
        }
        int iUserID = Integer.parseInt(userID);
        int iBookID = Integer.parseInt(bookID);
        checkoutBook(iUserID, iBookID);
        saveBooks(new File("books.txt"));
        saveUsers(new File("users.txt"));
    }

    private void returnBookAction() {
        String userID = JOptionPane.showInputDialog("Enter user ID");
        String bookID = JOptionPane.showInputDialog("Enter book ID");
        if (Objects.equals(userID, "") || Objects.equals(bookID, "")) {
            throw new RuntimeException("User or book not found");
        }
        int iUserID = Integer.parseInt(userID);
        int iBookID = Integer.parseInt(bookID);
        returnBook(iUserID, iBookID);
        saveBooks(new File("books.txt"));
        saveUsers(new File("users.txt"));
    }

    private void displayBooksAction() {
        String[] columnLabels = {"Book ID", "Title", "Author", "Genre", "Availability"};
        Object[][] rowData = new Object[getBooks().size()][5];

        int i = 0;
        for (Book book : getBooks()) {
            rowData[i][0] = book.getBookID();
            rowData[i][1] = book.getTitle();
            rowData[i][2] = book.getAuthor();
            rowData[i][3] = book.getGenre();
            rowData[i][4] = book.getAvailabilityStatus();
            i++;
        }

        if (rowData.length == 0) {
            throw new RuntimeException("No books found");
        }

        JTable table = new JTable(rowData, columnLabels);
        JOptionPane.showMessageDialog(null, new JScrollPane(table));
    }

    private void displayUsersAction() {
        String[] columnLabels = {"User ID", "Name", "Contact Information", "Borrowed Books"};
        Object[][] rowData = new Object[getUsers().size()][4];

        int i = 0;
        for (User user : getUsers()) {
            rowData[i][0] = user.getUserID();
            rowData[i][1] = user.getName();
            rowData[i][2] = user.getContactInformation();
            rowData[i][3] = String.join(", ", user.getBorrowedBookNames());
            i++;
        }

        if (rowData.length == 0) {
            throw new RuntimeException("No users found");
        }

        JTable table = new JTable(rowData, columnLabels);
        JOptionPane.showMessageDialog(null, new JScrollPane(table));
    }

    private void searchBookByTitleAction() {
        String title = JOptionPane.showInputDialog("Enter title");
        String[] columnLabels = {"Book ID", "Title", "Author", "Genre", "Availability"};
        Vector<Book> searchResult = searchBookByTitle(title);
        if (searchResult.isEmpty()) {
            throw new RuntimeException("No books found");
        }
        Object[][] rowData = new Object[searchResult.size()][5];

        int i = 0;
        for (Book book : searchResult) {
            rowData[i][0] = book.getBookID();
            rowData[i][1] = book.getTitle();
            rowData[i][2] = book.getAuthor();
            rowData[i][3] = book.getGenre();
            rowData[i][4] = book.getAvailabilityStatus();
            i++;
        }

        JTable table = new JTable(rowData, columnLabels);
        JOptionPane.showMessageDialog(null, new JScrollPane(table));
    }

    private void searchBookByAuthorAction() {
        String author = JOptionPane.showInputDialog("Enter author");
        String[] columnLabels = {"Book ID", "Title", "Author", "Genre", "Availability"};
        Vector<Book> searchResult = searchBookByAuthor(author);
        if (searchResult.isEmpty()) {
            throw new RuntimeException("No books found");
        }
        Object[][] rowData = new Object[searchResult.size()][5];

        int i = 0;
        for (Book book : searchResult) {
            rowData[i][0] = book.getBookID();
            rowData[i][1] = book.getTitle();
            rowData[i][2] = book.getAuthor();
            rowData[i][3] = book.getGenre();
            rowData[i][4] = book.getAvailabilityStatus();
            i++;
        }

        JTable table = new JTable(rowData, columnLabels);
        JOptionPane.showMessageDialog(null, new JScrollPane(table));
    }
}
