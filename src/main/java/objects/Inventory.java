package main.java.objects;

import main.java.objects.Book;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that represents a bookstore testInventory.dat, providing testInventory.dat management and search functionality.
 */
public class Inventory {
    private final ArrayList<Book> inventory;


    /**
     * Constructs an empty testInventory.dat.
     */
    public Inventory() {
        inventory = new ArrayList<>();
    }


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
     * Returns a reference to the testInventory.dat array list.
     * @return the testInventory.dat array list.
     */
    public ArrayList<Book> getInventory() {
        return inventory;
    }


    /**
     * Adds a book to testInventory.dat.
     * @param book the book to add to testInventory.dat.
     */
    public void add(Book book) {
        inventory.add(book);
    }


    /**
     * Removes a book from testInventory.dat.
     * @param book the book to remove from testInventory.dat.
     */
    public void remove(Book book) {
        inventory.removeIf(aBook -> aBook.equals(book));
    }


    @Override
    public String toString() {
        return "main.java.objects.Inventory{" +
                "testInventory.dat=" + inventory +
                '}';
    }


    /**
     * Creates a string of csv values from the books in testInventory.dat.
     * @return a string of csv values from the books in testInventory.dat.
     */
    public String toCsv() {
        StringBuilder csv = new StringBuilder();
        for (Book book : inventory) {
            csv.append(book.toCsv()).append("\n");
        }
        return csv.toString();
    }
}
