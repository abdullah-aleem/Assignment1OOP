public class Book {
    // declaring the private vars
    private final int bookID;
    private final String title;
    private final String author;
    private final String genre;
    private boolean availabilityStatus;

    public Book(int bookID, String title, String author, String genre, boolean availabilityStatus) { // Create a constructor for the Book class
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availabilityStatus = availabilityStatus;
    }

    public int getBookID() {
        return bookID;
    } // Create a method to get the book ID

    public String getTitle() {
        return title;
    } // Create a method to get the title

    public String getAuthor() {
        return author;
    } // Create a method to get the author

    public String getGenre() {
        return genre;
    } // Create a method to get the genre

    public boolean getAvailabilityStatus() {
        return availabilityStatus;
    } // Create a method to get the availability status

    public void setAvailabilityStatus(boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    } // Create a method to set the availability status

}
