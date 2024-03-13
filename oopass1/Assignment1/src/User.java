import java.util.Vector;

public class User {
    // declaring the private vars
    private final int userID;
    private final String name;
    private final String contactInformation;
    private Vector<Book> borrowedBooks;

    public User(int userID, String name, String contactInformation, Vector<Book> borrowedBooks) { // Create a constructor for the User class
        this.userID = userID;
        this.name = name;
        this.contactInformation = contactInformation;
        this.borrowedBooks = borrowedBooks;
    }

    public int getUserID() {
        return userID;
    } // Create a method to get the user ID

    public String getName() {
        return name;
    } // Create a method to get the name

    public String getContactInformation() {
        return contactInformation;
    } // Create a method to get the contact information

    public Vector<Book> getBorrowedBooks() {
        return borrowedBooks;
    } // Create a method to get the borrowed books

    public Vector<String> getBorrowedBookNames() { // Create a method to get the borrowed book names
        Vector<String> bookNames = new Vector<>();
        for (Book book : borrowedBooks) {
            bookNames.add(book.getTitle());
        }
        return bookNames;
    }

    public void setBorrowedBooks(Vector<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    } // Create a method to set the borrowed books


}
