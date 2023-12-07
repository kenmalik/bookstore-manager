package main.java.menus;

import main.java.objects.Customer;
import main.java.objects.Inventory;
import main.java.objects.ShoppingCart;

public class CustomerMenu implements ProgramMenu {
    public void display(Inventory inventory, UserType userType) {
        System.out.println();
        Customer customer = initializeCustomer();
        ShoppingCart cart = new ShoppingCart();

        boolean done = false;
        while (!done) {
            System.out.println("\n--- CUSTOMER MENU ---");

            int actionChoice = MenuUtil.choicePrompt(
                    "Choose Action:",
                    MenuOption.BOOK_SEARCH.getLabel(),
                    MenuOption.VIEW_INVENTORY.getLabel(),
                    MenuOption.CHECKOUT.getLabel()
            );

            switch (actionChoice) {
                case 1 -> MenuOption.BOOK_SEARCH.display(inventory, userType);
                case 2 -> MenuOption.VIEW_INVENTORY.display(inventory, userType);
                case 3 -> {
                    CheckoutMenu checkoutMenu = (CheckoutMenu) MenuOption.CHECKOUT.getMenu();
                    checkoutMenu.setCustomer(customer);
                    checkoutMenu.setCart(cart);
                    checkoutMenu.display(inventory, userType);
                }
                case MenuOption.DONE_DISPLAYING -> done = true;
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
