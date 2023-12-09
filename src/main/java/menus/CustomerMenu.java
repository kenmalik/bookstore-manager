package main.java.menus;

import main.java.objects.Inventory;
import main.java.objects.ShoppingCart;
import main.java.utilities.MenuOption;
import main.java.utilities.MenuUtil;
import main.java.utilities.ProgramMenu;
import main.java.utilities.UserType;

/**
 * The home menu for customer users.
 */
public class CustomerMenu implements ProgramMenu {
    @Override
    public void display(Inventory inventory, UserType userType) {
        System.out.println();
        ShoppingCart cart = new ShoppingCart();

        boolean done = false;
        while (!done) {
            System.out.println("\n--- CUSTOMER MENU ---");

            MenuOption actionChoice = (MenuOption) MenuUtil.choicePrompt(
                    "Choose Action:",
                    MenuOption.BOOK_SEARCH,
                    MenuOption.VIEW_INVENTORY,
                    MenuOption.VIEW_CART
            );

            if (actionChoice == null) {
                done = true;
            } else {
                performAction(actionChoice, userType, inventory, cart);
            }
        }
    }

    /**
     * Performs an action as determined by the action choice.
     *
     * @param actionChoice the chosen action to perform.
     * @param userType     the type of the user that requested this action.
     * @param inventory    the inventory to perform actions on.
     * @param cart         the customer's cart.
     */
    private void performAction(MenuOption actionChoice, UserType userType, Inventory inventory, ShoppingCart cart) {
        switch (actionChoice) {
            case BOOK_SEARCH -> {
                BookSearchMenu bookSearchMenu = (BookSearchMenu) MenuOption.BOOK_SEARCH.getMenu();
                bookSearchMenu.setCart(cart);
                bookSearchMenu.display(inventory, userType);
            }
            case VIEW_INVENTORY -> {
                ViewInventoryMenu viewInventoryMenu = (ViewInventoryMenu) MenuOption.VIEW_INVENTORY.getMenu();
                viewInventoryMenu.setCart(cart);
                viewInventoryMenu.display(inventory, userType);
            }
            case VIEW_CART -> {
                ViewCartMenu viewCartMenu = (ViewCartMenu) MenuOption.VIEW_CART.getMenu();
                viewCartMenu.setCart(cart);
                viewCartMenu.display(inventory, userType);
            }
        }
    }
}
