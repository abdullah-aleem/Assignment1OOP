import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Library {
    private final Vector<Book> books; // Create a Vector of Book objects

    private final Vector<User> users; // Create a Vector of User objects

    public Library() {
        // initialize the books and users vectors
        books = new Vector<>();
        users = new Vector<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void checkoutBook(int userID, int bookID) {
        User user = getUserByID(userID);
        Book book = getBookByID(bookID);
        if (user != null && book != null) { // If the user and book are found
            if (book.getAvailabilityStatus()) {
                book.setAvailabilityStatus(false);
                Vector<Book> borrowedBooks = user.getBorrowedBooks(); // Get the user's borrowed books
                borrowedBooks.add(book);
            } else {
                throw new RuntimeException("Book not available"); // If the book is not available, throw an exception
            }
        } else {
            throw new RuntimeException("User or book not found"); // If the user or book is not found, throw an exception
        }
    }

    public void returnBook(int userID, int bookID) {
        User user = getUserByID(userID);
        Book book = getBookByID(bookID);
        if (user != null && book != null) {
            Vector<Book> borrowedBooks = user.getBorrowedBooks();
            if (borrowedBooks.contains(book)) {
                borrowedBooks.remove(book);
                book.setAvailabilityStatus(true);
            }
        } else {
            throw new RuntimeException("User or book not found"); // similar to above, the user or book is not found, throw an exception
        }
    }


    public Vector<Book> searchBookByTitle(String title) { // Create a method to search for a book by title
        Vector<Book> result = new Vector<>();
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                result.add(book);
            }
        }
        return result;
    }

    public Vector<Book> searchBookByAuthor(String author) { // Create a method to search for a book by author
        Vector<Book> result = new Vector<>();
        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                result.add(book);
            }
        }
        return result;
    }

    public User getUserByID(int userID) { // Create a method to get a user by ID
        for (User user : users) {
            if (user.getUserID() == userID) {
                return user;
            }
        }
        return null;
    }

    public Book getBookByID(int bookID) { // Create a method to get a book by ID
        for (Book book : books) {
            if (book.getBookID() == bookID) {
                return book;
            }
        }
        return null;
    }

    public Vector<Book> getBooks() {
        return books;
    } // Create a method to get the books

    public Vector<User> getUsers() {
        return users;
    } // Create a method to get the users

    public void loadBooks(File file) { // Create a method to load books from a file
        try {
            Scanner myReader = new Scanner(file);
            Vector<Book> books = new Vector<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] bookData = data.split(",");
                int bookID = Integer.parseInt(bookData[0]);
                String title = bookData[1];
                String author = bookData[2];
                String genre = bookData[3];
                boolean availabilityStatus = Boolean.parseBoolean(bookData[4]);
                Book book = new Book(bookID, title, author, genre, availabilityStatus);
                this.addBook(book);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadUsers(File file) { // Create a method to load users from a file
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] userData = data.split(",");
                int userID = Integer.parseInt(userData[0]);
                String name = userData[1];
                String contactInformation = userData[2];
                Vector<Book> borrowedBooks = new Vector<>();
                User user = new User(userID, name, contactInformation, borrowedBooks);
                this.addUser(user);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveBooks(File file) { // Create a method to save books to a file
        try {
            java.io.PrintWriter output = new java.io.PrintWriter(file);
            for (Book book : books) {
                output.println(book.getBookID() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getGenre() + "," + book.getAvailabilityStatus());
            }
            output.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUsers(File file) { // Create a method to save users to a file
        try {
            java.io.PrintWriter output = new java.io.PrintWriter(file);
            for (User user : users) {
                output.println(user.getUserID() + "," + user.getName() + "," + user.getContactInformation());
            }
            output.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}



