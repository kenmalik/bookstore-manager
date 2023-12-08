package main.java.menus;

import main.java.objects.Customer;
import main.java.objects.Inventory;
import main.java.objects.ShoppingCart;
import main.java.utilities.MenuOption;
import main.java.utilities.MenuUtil;
import main.java.utilities.ProgramMenu;
import main.java.utilities.UserType;

public class CustomerMenu implements ProgramMenu {
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
            }
            else {
                performAction(actionChoice, userType, inventory, cart);
            }
        }
    }

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
