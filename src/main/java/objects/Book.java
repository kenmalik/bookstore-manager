package main.java.objects;

import java.util.Objects;

/**
 * A class that represents a book in a bookstore, containing the book's information as well as its list price and availability.
 */
public class Book {
    public static final int ARG_COUNT = 5;
    private String title;
    private String author;
    private String genre;
    private double price;
    private int availability;


    /**
     * Constructs a book with default values.
     */
    public Book() {
        this("N/A", "N/A", "N/A", -1, -1);
    }


    /**
     * Constructs a book given a set of data.
     * @param title the title of the book.
     * @param author the author of the book.
     * @param genre the genre of the book.
     * @param price the price of the book.
     * @param availability the amount of copies available in testInventory.dat.
     */
    public Book(String title, String author, String genre, double price, int availability) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.availability = availability;
    }


    /**
     * Returns the title of the book.
     * @return the title of the book.
     */
    public String getTitle() {
        return title;
    }


     /**
     * Returns the author of the book.
     * @return the author of the book.
     */
    public String getAuthor() {
        return author;
    }


     /**
     * Returns the genre of the book.
     * @return the genre of the book.
     */
    public String getGenre() {
        return genre;
    }


     /**
     * Returns the price of the book.
     * @return the price of the book.
     */
    public double getPrice() {
        return price;
    }


     /**
     * Returns the availability of the book.
     * @return the availability of the book.
     */
    public int getAvailability() {
        return availability;
    }


    /**
     * Sets the title of the book.
     * @param title the title of the book.
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * Sets the author of the book.
     * @param author the author of the book.
     */
    public void setAuthor(String author) {
        this.author = author;
    }


    /**
     * Sets the genre of the book.
     * @param genre the genre of the book.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }


    /**
     * Sets the price of the book.
     * @param price the price of the book.
     */
    public void setPrice(double price) {
        this.price = price;
    }


    /**
     * Sets the availability of the book.
     * @param availability the availability of the book.
     */
    public void setAvailability(int availability) {
        this.availability = availability;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Double.compare(price, book.price) == 0
                && availability == book.availability
                && Objects.equals(title, book.title)
                && Objects.equals(author, book.author)
                && Objects.equals(genre, book.genre);
    }


    @Override
    public String toString() {
        return "main.java.objects.Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", price=" + price +
                ", availability=" + availability +
                '}';
    }


    /**
     * Creates a csv line from book data.
     * @return a csv line containing a book's data.
     */
    public String toCsv() {
        return String.format(
          "%s,%s,%s,%.2f,%d",
          title, author, genre, price, availability
        );
    }
}
