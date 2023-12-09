package main.java.objects;

import java.util.ArrayList;
import java.util.Scanner;

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
     * Constructs an inventory using an input file scanner.
     *
     * @param in the input file scanner to read from.
     * @throws NumberFormatException     thrown if price or availability cannot be converted from string.
     * @throws IndexOutOfBoundsException thrown if input line arguments does not match the amount to construct book.
     */
    public Inventory(Scanner in) throws NumberFormatException, IndexOutOfBoundsException {
        inventory = new ArrayList<>();
        while (in.hasNextLine()) {
            String[] csvValues = in.nextLine().split(",");
            if (csvValues.length != Book.ARG_COUNT) {
                throw new IndexOutOfBoundsException("Invalid argument count in CSV line.");
            }
            inventory.add(new Book(
                    csvValues[0],
                    csvValues[1],
                    csvValues[2],
                    Double.parseDouble(csvValues[3]),
                    Integer.parseInt(csvValues[4])
            ));
        }
    }

    /**
     * Returns a reference to the inventory array list.
     *
     * @return the inventory array list.
     */
    public ArrayList<Book> getInventory() {
        return inventory;
    }

    /**
     * Adds a book to inventory.
     *
     * @param book the book to add to inventory.
     */
    public void add(Book book) {
        inventory.add(book);
    }

    /**
     * Removes a book from inventory.
     *
     * @param book the book to remove from inventory.
     */
    public void remove(Book book) {
        inventory.removeIf(aBook -> aBook.equals(book));
    }

    @Override
    public String toString() {
        return "main.java.objects.Inventory{" +
                "inventory=" + inventory +
                '}';
    }

    /**
     * Creates a string of csv values from the books in inventory.
     *
     * @return a string of csv values from the books in inventory.
     */
    public String toCsv() {
        StringBuilder csv = new StringBuilder();
        for (Book book : inventory) {
            csv.append(book.toCsv()).append("\n");
        }
        return csv.toString();
    }
}
