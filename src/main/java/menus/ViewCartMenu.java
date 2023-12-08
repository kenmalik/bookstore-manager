package main.java.menus;

import main.java.objects.Book;
import main.java.objects.Inventory;
import main.java.objects.ShoppingCart;
import main.java.utilities.*;

public class ViewCartMenu implements ProgramMenu {
    private ShoppingCart cart;

    private enum Action implements PromptSelection {
        CHECKOUT("Proceed to checkout"), REMOVE_ITEM("Remove book from cart");
        private final String label;
        Action(String label) { this.label = label; }

        @Override
        public String getLabel() {
            return label;
        }
    }
    private static final Action[] NONE = new Action[] {};
    private static final Action[] CHECKOUT_AND_REMOVE = new Action[] {Action.CHECKOUT, Action.REMOVE_ITEM};

    @Override
    public void display(Inventory inventory, UserType userType) {
        Action[] availableActions;
        boolean done = false;
        while (!done) {
            // Display cart and set available actions
            if (!cart.getCart().isEmpty()) {
                displayCart();
                availableActions = CHECKOUT_AND_REMOVE;
            }
            else {
                System.out.println("\nYour cart is empty.");
                availableActions = NONE;
            }

            // Prompt user actions
            Action action = (Action) MenuUtil.choicePrompt(
                    "\nChoose Action:",
                    availableActions
            );

            // Perform selected action
            if (action == null) {
                done = true;
            }
            else if (action == Action.CHECKOUT) {
                CheckoutMenu checkoutMenu = (CheckoutMenu) MenuOption.CHECKOUT.getMenu();
                checkoutMenu.setCart(cart);
                checkoutMenu.display(inventory, userType);
            }
            else if (action == Action.REMOVE_ITEM) {
                removeItemFromCart();
            }
        }
    }


    private void displayCart() {
        System.out.println("\nYour Cart:");
        for (Book book : cart.getCart()) {
            System.out.println(book);
        }
    }


    private void removeItemFromCart() {
        Book toRemove = (Book) MenuUtil.choicePrompt(
                "\nChoose book to remove:",
                cart.getCart().toArray(new Book[0])
        );
        if (toRemove != null) {
            cart.remove(toRemove);
        }
    }


    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }
}
