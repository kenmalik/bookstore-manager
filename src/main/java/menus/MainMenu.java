package main.java.menus;

import main.java.objects.Inventory;
import main.java.utilities.MenuOption;
import main.java.utilities.MenuUtil;
import main.java.utilities.UserType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The first menu of the program where user type is selected.
 */
public class MainMenu {
    /**
     * The main method of the program. Reads inventory file and prompts user to enter user type.
     *
     * @param args command line arguments to pass to program. (Not supported)
     */
    public static void main(String[] args) throws FileNotFoundException, IndexOutOfBoundsException, NumberFormatException {
        Inventory inventory = loadInventory();

        boolean done = false;
        while (!done) {
            System.out.println("\n--- BOOKSTORE MANAGER ---");

            // Prompt input for user type
            MenuOption userType = (MenuOption) MenuUtil.choicePrompt(
                    "Choose User Type:",
                    MenuOption.ADMIN,
                    MenuOption.CUSTOMER
            );

            // Open appropriate menu for user type
            if (userType == null) {
                done = true;
            } else if (userType == MenuOption.ADMIN) {
                MenuOption.ADMIN.display(inventory, UserType.ADMIN);
            } else if (userType == MenuOption.CUSTOMER) {
                MenuOption.CUSTOMER.display(inventory, UserType.CUSTOMER);
            }
        }
    }

    /**
     * Reads an inventory csv file and loads it into memory.
     *
     * @return the loaded inventory object.
     */
    private static Inventory loadInventory() throws FileNotFoundException, IndexOutOfBoundsException, NumberFormatException {
        File inFile = new File("src\\main\\resources\\inventory.dat");
        Scanner in = new Scanner(inFile);
        return new Inventory(in);
    }
}
