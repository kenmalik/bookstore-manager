package edu.pasadena.klongid.menus;

import edu.pasadena.klongid.objects.Inventory;
import edu.pasadena.klongid.utilities.MenuOption;
import edu.pasadena.klongid.utilities.MenuUtil;
import edu.pasadena.klongid.utilities.PromptSelection;
import edu.pasadena.klongid.utilities.UserType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The first menu of the program where user type is selected.
 */
public class MainMenu {
    /**
     * The edu.pasadena.klongid method of the program. Reads inventory file and prompts user to enter user type.
     *
     * @param args command line arguments to pass to program. (Not supported)
     */
    public static void main(String[] args) throws FileNotFoundException, IndexOutOfBoundsException, NumberFormatException {
        Inventory inventory = null;
        boolean validInventory = false;
        boolean inputtingCustomPath = false;

        // Attempt default inventory load
        try {
            inventory = getInventory("inventory.dat");
            validInventory = true;
        }
        catch (FileNotFoundException fileNotFoundException) {
            PromptSelection.StandardOption action = (PromptSelection.StandardOption) MenuUtil.choicePrompt(
                    "\nDefault inventory file not found. Input another file path?",
                    PromptSelection.StandardOption.YES
            );
            if (action == PromptSelection.StandardOption.YES) {
                inputtingCustomPath = true;
            }
        }

        // Attempt custom path input if default fails
        while (inputtingCustomPath) {
            try {
                inventory = getInventory(MenuUtil.getStringInput("\nEnter path: "));
                validInventory = true;
                inputtingCustomPath = false;
            }
            catch (FileNotFoundException fileNotFoundException) {
                PromptSelection.StandardOption action = (PromptSelection.StandardOption) MenuUtil.choicePrompt(
                        "\nFile not found. Input another file path?",
                        PromptSelection.StandardOption.YES
                );
                if (action != PromptSelection.StandardOption.YES) {
                    inputtingCustomPath = false;
                }
            }
        }

        boolean done = !validInventory;  // Only do edu.pasadena.klongid program loop if valid inventory is constructed
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
     * @param path the path of the inventory file to be loaded.
     * @return the loaded inventory object.
     */
    private static Inventory getInventory(String path) throws FileNotFoundException, IndexOutOfBoundsException, NumberFormatException {
        File inFile = new File(path);
        Scanner in = new Scanner(inFile);
        return new Inventory(in);
    }
}
