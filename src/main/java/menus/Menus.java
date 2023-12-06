package main.java.menus;

import main.java.objects.Inventory;

public enum Menus {
    ADMIN(new AdminMenu(), "Admin"),
    CUSTOMER(new CustomerMenu(), "Customer"),
    BOOK_SEARCH(new BookSearchMenu(), "Book Search"),
    VIEW_INVENTORY(new ViewInventoryMenu(), "View Inventory"),
    ADD_BOOK(new AddBookMenu(), "Add Book");

    public static final int DONE_DISPLAYING = -1;
    private final ProgramMenu menu;
    private final String label;


    Menus(ProgramMenu menu, String label) {
        this.menu = menu;
        this.label = label;
    }


    public void display(Inventory inventory) {
        menu.display(inventory);
    }


    public String getLabel() {
        return label;
    }
}
