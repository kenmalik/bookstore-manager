package main.java.menus;

import main.java.objects.Customer;
import main.java.objects.Inventory;
import main.java.objects.ShoppingCart;

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
                    MenuOption.CHECKOUT
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
            case CHECKOUT -> {
                System.out.println();
                Customer customer = initializeCustomer();
                CheckoutMenu checkoutMenu = (CheckoutMenu) MenuOption.CHECKOUT.getMenu();
                checkoutMenu.setCustomer(customer);
                checkoutMenu.setCart(cart);
                checkoutMenu.display(inventory, userType);
            }
        }
    }


    private Customer initializeCustomer() {
        return new Customer(
                MenuUtil.getStringInput("Enter name: "),
                MenuUtil.getStringInput("Enter phone number: "),
                MenuUtil.getStringInput("Enter email: "),
                MenuUtil.getStringInput("Enter address: "),
                0
        );
    }
}
