package main.java.utilities;

import main.java.menus.*;
import main.java.objects.Inventory;

/**
 * An enumeration of the major program menus.
 */
public enum MenuOption implements PromptSelection {
    ADMIN(new AdminMenu(), "Admin"),
    CUSTOMER(new CustomerMenu(), "Customer"),
    BOOK_SEARCH(new BookSearchMenu(), "Book Search"),
    VIEW_INVENTORY(new ViewInventoryMenu(), "View Inventory"),
    ADD_BOOK(new AddBookMenu(), "Add Book"),
    REMOVE_BOOK(new RemoveBookMenu(), "Remove Book"),
    CHECKOUT(new CheckoutMenu(), "Proceed to Checkout"),
    VIEW_CART(new ViewCartMenu(), "View Cart");

    private final ProgramMenu menu;
    private final String label;

    MenuOption(ProgramMenu menu, String label) {
        this.menu = menu;
        this.label = label;
    }

    /**
     * Calls the display method on the associated menu.
     *
     * @param inventory the inventory to display.
     * @param userType  the type of user requesting the display.
     */
    public void display(Inventory inventory, UserType userType) {
        menu.display(inventory, userType);
    }

    /**
     * Gets the display label associated with the menu.
     *
     * @return the display label associated with the menu.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the menu object.
     *
     * @return the menu object.
     */
    public ProgramMenu getMenu() {
        return menu;
    }
}
