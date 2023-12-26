package main.java.menus;

import main.java.objects.Book;
import main.java.objects.Inventory;
import main.java.objects.ShoppingCart;
import main.java.utilities.MenuUtil;
import main.java.utilities.ProgramMenu;
import main.java.utilities.PromptSelection;
import main.java.utilities.UserType;

import java.util.ArrayList;

/**
 * A menu to search for books.
 */
public class BookSearchMenu implements ProgramMenu {
    private static final String NONE_FOUND_MESSAGE = "No matches found.";
    private UserType userType;
    private ShoppingCart cart;

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
            } else {
                performSearch(inventory, searchType);
            }

        }
    }

    /**
     * Calls the appropriate search method determined by a provided search type.
     *
     * @param inventory  the inventory to perform the search on.
     * @param searchType the type of search to perform.
     */
    private void performSearch(Inventory inventory, SearchType searchType) {
        switch (searchType) {
            case TITLE -> {
                System.out.println();
                search(inventory, (Object obj) -> ((Book) obj).getTitle());
            }
            case AUTHOR -> {
                System.out.println();
                search(inventory, (Object obj) -> ((Book) obj).getAuthor());
            }
            case GENRE -> {
                System.out.println();
                search(inventory, (Object obj) -> ((Book) obj).getGenre());
            }
            case PRICE -> {
                System.out.println();
                priceSearch(inventory);
            }
        }
    }

    private interface Checker {
        String check(Object object);
    }

    /**
     * Search for a book by genre.
     *
     * @param inventory the inventory to perform the search on.
     */
    private void search(Inventory inventory, Checker checker) {
        ArrayList<Book> matches = new ArrayList<>();
        String searchTerm = MenuUtil.getStringInput("Input Search Term: ");

        boolean bookFound = false;
        for (Book book : inventory.getInventory()) {
            if (checker.check(book).equalsIgnoreCase(searchTerm)) {
                bookFound = true;
                matches.add(book);
            }
        }

        if (bookFound) {
            displayBooks(matches);
        } else {
            System.out.println(NONE_FOUND_MESSAGE);
        }
    }

    /**
     * Search for a book by price.
     *
     * @param inventory the inventory to perform the search on.
     */
    private void priceSearch(Inventory inventory) {
        ArrayList<Book> matches = new ArrayList<>();

        // Get and validate input for price range
        double minPrice = -1;
        double maxPrice = -1;
        boolean validRange = false;
        while (!validRange) {
            minPrice = MenuUtil.getDoubleInput("Input minimum price: ");
            maxPrice = MenuUtil.getDoubleInput("Input maximum price: ");
            if (minPrice <= maxPrice && minPrice >= 0 && maxPrice >= 0) {
                validRange = true;
            } else {
                System.out.println("Invalid range");
            }
        }

        // Perform search
        boolean bookFound = false;
        for (Book book : inventory.getInventory()) {
            if (book.getPrice() >= minPrice && book.getPrice() <= maxPrice) {
                bookFound = true;
                matches.add(book);
            }
        }

        if (bookFound) {
            displayBooks(matches);
        } else {
            System.out.println(NONE_FOUND_MESSAGE);
        }
    }

    /**
     * Sets the shopping cart to add books to.
     *
     * @param cart the shopping cart to use.
     */
    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    /**
     * Displays the books that match search conditions and the actions available.
     *
     * @param matches the list of books to display and act on.
     */
    private void displayBooks(ArrayList<Book> matches) {
        System.out.println("\nMatch Found:");
        System.out.println(MenuUtil.lineDisplayFormat(matches));

        if (userType == UserType.CUSTOMER) {
            promptCustomerActions(matches);
        } else if (userType == UserType.ADMIN) {
            promptAdminActions(matches);
        }
    }

    /**
     * Prompts customers with the actions available to them after searching.
     *
     * @param matches the list of books that matched the search criteria.
     */
    private void promptCustomerActions(ArrayList<Book> matches) {
        PostSearchAction action = (PostSearchAction) MenuUtil.choicePrompt(
                "\nChoose Action:",
                PostSearchAction.ADD_TO_CART
        );
        if (action == PostSearchAction.ADD_TO_CART) {
            Book book = (Book) MenuUtil.choicePrompt(
                    "\nSelect Book to Add to Cart:",
                    matches.toArray(matches.toArray(new Book[0]))
            );
            if (book == null) {
                return;
            }

            if (book.getAvailability() <= 0) {
                System.out.println("\nSelected book is sold out.");
            } else if (cart.getCart().contains(book)) {
                System.out.println("\nCart already contains book.");
            } else {
                cart.add(book);
            }
        }
    }

    /**
     * Prompts admins with the actions available to them after searching.
     *
     * @param matches the list of books that matched the search criteria.
     */
    private void promptAdminActions(ArrayList<Book> matches) {
        PostSearchAction action = (PostSearchAction) MenuUtil.choicePrompt(
                "\nChoose Action:",
                PostSearchAction.EDIT_BOOK
        );
        if (action == PostSearchAction.EDIT_BOOK) {
            Book book = (Book) MenuUtil.choicePrompt(
                    "\nSelect Book to Edit:",
                    matches.toArray(matches.toArray(new Book[0]))
            );
            if (book != null) {
                boolean editing = true;
                while (editing) {
                    editing = MenuUtil.makeBookEdit(book);
                }
            }
        }
    }

    /**
     * An enumeration of the possible search types.
     */
    private enum SearchType implements PromptSelection {
        TITLE, AUTHOR, GENRE, PRICE;

        public String getLabel() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }

    /**
     * An enumeration of the possible actions after performing a search.
     */
    private enum PostSearchAction implements PromptSelection {
        ADD_TO_CART("Add a book to cart"), EDIT_BOOK("Edit a book");
        private final String label;

        PostSearchAction(String label) {
            this.label = label;
        }

        @Override
        public String getLabel() {
            return label;
        }
    }
}
