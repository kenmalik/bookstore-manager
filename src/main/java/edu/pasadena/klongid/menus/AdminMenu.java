package edu.pasadena.klongid.menus;

import edu.pasadena.klongid.objects.Inventory;
import edu.pasadena.klongid.utilities.MenuOption;
import edu.pasadena.klongid.utilities.MenuUtil;
import edu.pasadena.klongid.utilities.ProgramMenu;
import edu.pasadena.klongid.utilities.UserType;

/**
 * The home menu for admin users.
 */
public class AdminMenu implements ProgramMenu {
    @Override
    public void display(Inventory inventory, UserType userType) {
        boolean done = false;

        while (!done) {
            System.out.println("\n--- ADMIN MENU ---");

            MenuOption actionChoice = (MenuOption) MenuUtil.choicePrompt(
                    "Choose Action:",
                    MenuOption.BOOK_SEARCH,
                    MenuOption.ADD_BOOK,
                    MenuOption.REMOVE_BOOK,
                    MenuOption.VIEW_INVENTORY
            );

            if (actionChoice == null) {
                done = true;
            } else {
                switch (actionChoice) {
                    case BOOK_SEARCH -> MenuOption.BOOK_SEARCH.display(inventory, UserType.ADMIN);
                    case ADD_BOOK -> MenuOption.ADD_BOOK.display(inventory, UserType.ADMIN);
                    case REMOVE_BOOK -> MenuOption.REMOVE_BOOK.display(inventory, UserType.ADMIN);
                    case VIEW_INVENTORY -> MenuOption.VIEW_INVENTORY.display(inventory, UserType.ADMIN);
                }
            }
        }
    }
}
