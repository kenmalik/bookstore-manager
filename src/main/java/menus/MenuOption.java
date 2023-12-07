package main.java.menus;

import main.java.objects.Inventory;

public enum MenuOption implements PromptSelection {
    ADMIN(new AdminMenu(), "Admin"),
    CUSTOMER(new CustomerMenu(), "Customer"),
    BOOK_SEARCH(new BookSearchMenu(), "Book Search"),
    VIEW_INVENTORY(new ViewInventoryMenu(), "View Inventory"),
    ADD_BOOK(new AddBookMenu(), "Add Book"),
    REMOVE_BOOK(new RemoveBookMenu(), "Remove Book"),
    CHECKOUT(new CheckoutMenu(), "Proceed to Checkout");

    private final ProgramMenu menu;
    private final String label;


    MenuOption(ProgramMenu menu, String label) {
        this.menu = menu;
        this.label = label;
    }


    public void display(Inventory inventory, UserType userType) {
        menu.display(inventory, userType);
    }


    public String getLabel() {
        return label;
    }


    public ProgramMenu getMenu() {
        return menu;
    }
}
