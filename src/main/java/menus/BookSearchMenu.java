package main.java.menus;

import main.java.objects.Book;
import main.java.objects.Inventory;
import main.java.objects.ShoppingCart;
import main.java.utilities.MenuUtil;
import main.java.utilities.ProgramMenu;
import main.java.utilities.PromptSelection;
import main.java.utilities.UserType;

import java.util.ArrayList;

public class BookSearchMenu implements ProgramMenu {
    private UserType userType;
    private ShoppingCart cart;
    private static final String NONE_FOUND_MESSAGE = "No matches found.";

    private enum SearchType implements PromptSelection {
        TITLE, AUTHOR, GENRE, PRICE;
        public String getLabel() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }


    @Override
    public void display(Inventory inventory, UserType userType) {
        this.userType = userType;
        boolean done = false;

        while (!done) {
            System.out.println();

            SearchType searchType = (SearchType) MenuUtil.choicePrompt(
                    "Search By:",
                    SearchType.TITLE,
                    SearchType.AUTHOR,
                    SearchType.GENRE,
                    SearchType.PRICE
            );

            if (searchType == null) {
                done = true;
            }
            else {
                performSearch(inventory, searchType);
            }

        }
    }


    private void performSearch(Inventory inventory, SearchType searchType) {
        switch (searchType) {
            case TITLE -> {
                System.out.println();
                titleSearch(inventory);
            }
            case AUTHOR -> {
                System.out.println();
                authorSearch(inventory);
            }
            case GENRE -> {
                System.out.println();
                genreSearch(inventory);
            }
            case PRICE -> {
                System.out.println();
                priceSearch(inventory);
            }
        }
    }


    enum PostSearchAction implements PromptSelection {
        ADD_TO_CART("Add a book to cart");
        private final String label;
        PostSearchAction(String label) { this.label = label; }
        @Override
        public String getLabel() {
            return label;
        }
    }


    private void titleSearch(Inventory inventory) {
        ArrayList<Book> matches = new ArrayList<>();
        String title = MenuUtil.getStringInput("Input Search Term: ");

        boolean bookFound = false;
        for (Book book : inventory.getInventory()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                bookFound = true;
                matches.add(book);
            }
        }

        if (bookFound) {
            displayBooks(matches);
        }
        else {
            System.out.println(NONE_FOUND_MESSAGE);
        }
    }


    private void authorSearch(Inventory inventory) {
        ArrayList<Book> matches = new ArrayList<>();
        String author = MenuUtil.getStringInput("Input Search Term: ");

        boolean bookFound = false;
        for (Book book : inventory.getInventory()) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                bookFound = true;
                matches.add(book);
            }
        }

        if (bookFound) {
            displayBooks(matches);
        }
        else {
            System.out.println(NONE_FOUND_MESSAGE);
        }
    }


    private void genreSearch(Inventory inventory) {
        ArrayList<Book> matches = new ArrayList<>();
        String genre = MenuUtil.getStringInput("Input Search Term: ");

        boolean bookFound = false;
        for (Book book : inventory.getInventory()) {
            if (book.getGenre().equalsIgnoreCase(genre)) {
                bookFound = true;
                matches.add(book);
            }
        }

        if (bookFound) {
            displayBooks(matches);
        }
        else {
            System.out.println(NONE_FOUND_MESSAGE);
        }
    }


    private void priceSearch(Inventory inventory) {
        ArrayList<Book> matches = new ArrayList<>();

        double minPrice = -1;
        double maxPrice = -1;
        boolean validRange = false;
        while (!validRange) {
            minPrice = MenuUtil.getDoubleInput("Input minimum price: ");
            maxPrice = MenuUtil.getDoubleInput("Input maximum price: ");
            if (minPrice <= maxPrice && minPrice >= 0 && maxPrice >= 0) {
                validRange = true;
            }
            else {
                System.out.println("Invalid range");
            }
        }

        boolean bookFound = false;
        for (Book book : inventory.getInventory()) {
            if (book.getPrice() >= minPrice && book.getPrice() <= maxPrice) {
                bookFound = true;
                matches.add(book);
            }
        }

        if (bookFound) {
            displayBooks(matches);
        }
        else {
            System.out.println(NONE_FOUND_MESSAGE);
        }
    }


    private void printBooks(ArrayList<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }


    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }


    private void displayBooks(ArrayList<Book> matches) {
        System.out.println("\nMatch Found:");
        printBooks(matches);

        if (userType == UserType.CUSTOMER) {
            PostSearchAction action = (PostSearchAction) MenuUtil.choicePrompt(
                    "\nChoose Action:",
                    PostSearchAction.ADD_TO_CART
            );
            if (action == PostSearchAction.ADD_TO_CART) {
                Book bookSelection = (Book) MenuUtil.choicePrompt(
                        "\nSelect Book to Add to Cart:",
                        matches.toArray(matches.toArray(new Book[0]))
                );
                if (bookSelection != null && bookSelection.getAvailability() <= 0) {
                    System.out.println("\nSelected book is sold out.");
                }
                else if (cart.getCart().contains(bookSelection)) {
                    System.out.println("\nCart already contains book.");
                }
                else {
                    cart.add(bookSelection);
                }
            }
        }
        else {
            MenuUtil.choicePrompt("\nDone Viewing?");
        }
    }
}
