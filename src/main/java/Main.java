package main.java;

import main.java.menus.MenuOption;
import main.java.menus.MenuUtil;
import main.java.menus.UserType;
import main.java.objects.Inventory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // Logger config
        LOGGER.setLevel(Level.ALL);

        Inventory inventory = readInventoryCsv();
        if (inventory == null) { // TODO: EXCEPTION HANDLING FOR BAD CSV READ
            System.exit(1);
        }

        boolean done = false;
        while (!done) {
            System.out.println();

            int userType = MenuUtil.choicePrompt(
                    "Choose User Type:",
                    MenuOption.ADMIN.getLabel(),
                    MenuOption.CUSTOMER.getLabel()
            );

            switch (userType) {
                case 1 -> MenuOption.ADMIN.display(inventory, UserType.ADMIN);
                case 2 -> MenuOption.CUSTOMER.display(inventory, UserType.CUSTOMER);
                case MenuOption.DONE_DISPLAYING -> done = true;
            }
        }
    }

    private static Inventory readInventoryCsv() {
        LOGGER.log(Level.INFO, "Loading testInventory.dat...\n");

        // Reading csv to testInventory.dat test
        File inFile = new File("src\\main\\resources\\inventory.dat");
        try (Scanner in = new Scanner(inFile)) {
            return new Inventory(in);
        }
        catch (FileNotFoundException | IndexOutOfBoundsException | NumberFormatException exception) {
            LOGGER.log(Level.SEVERE, exception.toString());
            return null;
        }
    }
}
