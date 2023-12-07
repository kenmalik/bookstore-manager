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
            MenuOption userType = (MenuOption) MenuUtil.choicePrompt(
                    "Choose User Type:",
                    MenuOption.ADMIN,
                    MenuOption.CUSTOMER
            );

            if (userType == null) {
                done = true;
            }
            else if (userType == MenuOption.ADMIN) {
                MenuOption.ADMIN.display(inventory, UserType.ADMIN);
            }
            else if (userType == MenuOption.CUSTOMER) {
                MenuOption.CUSTOMER.display(inventory, UserType.CUSTOMER);
            }
        }
    }

    private static Inventory readInventoryCsv() {
        LOGGER.log(Level.INFO, "Loading testInventory.dat...\n");

        // Reading csv to inventory
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
