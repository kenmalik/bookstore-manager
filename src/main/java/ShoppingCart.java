package main.java;

import java.util.ArrayList;

/**
 * A class that represents a shopping cart at a store, with methods to add and remove items.
 */
public class ShoppingCart {
    private final ArrayList<Book> cart;


    /**
     * Constructs an empty shopping cart.
     */
    public ShoppingCart() {
        cart = new ArrayList<>();
    }


    /**
     * Returns a reference to shopping cart array list.
     * @return the shopping cart array list.
     */
    public ArrayList<Book> getCart() {
        return cart;
    }


    /**
     * Adds a book to shopping cart.
     * @param book the book to add to shopping cart.
     */
    public void add(Book book) {
        cart.add(book);
    }


    /**
     * Removes a book from shopping cart.
     * @param book the book to remove from shopping cart.
     */
    public void remove(Book book) {
        cart.removeIf(aBook -> aBook.equals(book));
    }


    @Override
    public String toString() {
        return "ShoppingCart{" +
                "cart=" + cart +
                '}';
    }
}
