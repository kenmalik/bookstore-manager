package main.java;

import java.util.ArrayList;

/**
 * A class that represents a bookstore inventory, providing inventory management and search functionality.
 */
public class Inventory {
    private final ArrayList<Book> inventory;


    /**
     * Constructs an empty inventory.
     */
    public Inventory() {
        inventory = new ArrayList<>();
    }


    /**
     * Returns a reference to the inventory array list.
     * @return the inventory array list.
     */
    public ArrayList<Book> getInventory() {
        return inventory;
    }


    /**
     * Adds a book to inventory.
     * @param book the book to add to inventory.
     */
    public void add(Book book) {
        inventory.add(book);
    }


    /**
     * Removes a book from inventory.
     * @param book the book to remove from inventory.
     */
    public void remove(Book book) {
        inventory.removeIf(aBook -> aBook.equals(book));
    }


    @Override
    public String toString() {
        return "main.java.Inventory{" +
                "inventory=" + inventory +
                '}';
    }
}
