package test.java;

import main.java.objects.Book;
import main.java.objects.Inventory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InventoryCsvTest {
    private static final Logger LOGGER = Logger.getLogger(InventoryCsvTest.class.getName());
    public static void main(String[] args) {
        // Default-constructed to csv test
        Book emptyBook = new Book();
        System.out.println("Default Book CSV Test:");
        System.out.println(emptyBook.toCsv());

        Inventory emptyInventory = new Inventory();
        System.out.println("Default Inventory CSV Test:");
        System.out.println(emptyInventory.toCsv());

        // Filled inventory to csv test
        Inventory filledInventory = new Inventory();
        filledInventory.add(
                new Book("Atomic Habits", "James Clear", "Educational", 27.00, 1)
        );
        filledInventory.add(
                new Book("A Promised Land", "Barack Obama", "Biography", 35.00, 2)
        );
        System.out.println("\nInventory CSV Test:");
        System.out.println(filledInventory.toCsv());

        // Csv output file test
        File outFile = new File("src\\test\\resources\\inventoryCsvTestOut.dat");
        try (PrintWriter out = new PrintWriter(outFile)) {
            out.print(filledInventory.toCsv());
        }
        catch (FileNotFoundException fileNotFoundException) {
            System.out.println(fileNotFoundException.getMessage());
        }

        // Reading csv to inventory test
        File inFile = new File("src\\test\\resources\\testInventory.dat");
        try (Scanner in = new Scanner(inFile)) {
            Inventory csvRead = new Inventory(in);
            System.out.println(csvRead);
        }
        catch (FileNotFoundException | IndexOutOfBoundsException | NumberFormatException exception) {
            LOGGER.log(Level.SEVERE, exception.toString());
        }
    }
}
