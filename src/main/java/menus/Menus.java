package main.java.menus;

public enum Menus {
    ADMIN(new AdminMenu(), "Admin"),
    CUSTOMER(new CustomerMenu(), "Customer"),
    BOOK_SEARCH(new BookSearchMenu(), "Book Search");

    public static final int DONE_DISPLAYING = -1;
    private final ProgramMenu menu;
    private final String label;


    Menus(ProgramMenu menu, String label) {
        this.menu = menu;
        this.label = label;
    }


    public void display() {
        menu.display();
    }


    public String getLabel() {
        return label;
    }
}
