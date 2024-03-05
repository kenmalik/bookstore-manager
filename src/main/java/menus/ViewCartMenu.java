package main.java.menus;

import main.java.objects.Book;
import main.java.objects.Inventory;
import main.java.objects.ShoppingCart;
import main.java.utilities.*;

/**
 * A menu to view shopping cart from.
 */
public class ViewCartMenu implements ProgramMenu {
    private static final Action[] NONE = new Action[]{};
    private static final Action[] CHECKOUT_AND_REMOVE = new Action[]{Action.CHECKOUT, Action.REMOVE_ITEM};
    private ShoppingCart cart;

    @Override
    public void display(Inventory inventory, UserType userType) {
        Action[] availableActions;
        boolean done = false;
        while (!done) {
            // Display cart and set available actions
            if (!cart.getCart().isEmpty()) {
                System.out.println("Your cart");
                System.out.println(MenuUtil.lineDisplayFormat(cart.getCart()));
                availableActions = CHECKOUT_AND_REMOVE;
            } else {
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
            } else if (action == Action.CHECKOUT) {
                CheckoutMenu checkoutMenu = (CheckoutMenu) MenuOption.CHECKOUT.getMenu();
                checkoutMenu.setCart(cart);
                checkoutMenu.display(inventory, userType);
            } else if (action == Action.REMOVE_ITEM) {
                removeItemFromCart();
            }
        }
    }

    /**
     * Prompts removal of an item from cart.
     */
    private void removeItemFromCart() {
        Book toRemove = (Book) MenuUtil.choicePrompt(
                "\nChoose book to remove:",
                cart.getCart().toArray(new Book[0])
        );
        if (toRemove != null) {
            cart.remove(toRemove);
        }
    }

    /**
     * Sets the cart to view and act on.
     *
     * @param cart the cart to view and act on.
     */
    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    /**
     * An enumeration of the available actions in this menu.
     */
    private enum Action implements PromptSelection {
        CHECKOUT("Proceed to checkout"), REMOVE_ITEM("Remove book from cart");
        private final String label;

        Action(String label) {
            this.label = label;
        }

        @Override
        public String getLabel() {
            return label;
        }
    }
}
