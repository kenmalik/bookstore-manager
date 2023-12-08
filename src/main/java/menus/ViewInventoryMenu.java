package main.java.menus;

import main.java.objects.Book;
import main.java.objects.Inventory;
import main.java.objects.ShoppingCart;
import main.java.utilities.*;

public class ViewInventoryMenu implements ProgramMenu {
    private ShoppingCart cart;
    private static final int CHUNK_SIZE = 10;

    private enum ViewAction implements PromptSelection {
        NEXT("View next " + CHUNK_SIZE), PREVIOUS("View previous " + CHUNK_SIZE),
        ADD_TO_CART("Add a book to cart"), EDIT_BOOK("Edit book properties");
        private final String label;
        ViewAction(String label) {
            this.label = label;
        }
        @Override
        public String getLabel() {
            return label;
        }
    }
    // Admin action sets
    private static final ViewAction[] ADMIN_NONE = new ViewAction[] { ViewAction.EDIT_BOOK,  };
    private static final ViewAction[] ADMIN_PREV = new ViewAction[] { ViewAction.EDIT_BOOK, ViewAction.PREVIOUS };
    private static final ViewAction[] ADMIN_NEXT = new ViewAction[] { ViewAction.EDIT_BOOK, ViewAction.NEXT };
    private static final ViewAction[] ADMIN_NEXT_AND_PREV
            = new ViewAction[] { ViewAction.EDIT_BOOK, ViewAction.NEXT, ViewAction.PREVIOUS };

    // Customer action sets
    private static final ViewAction[] CUSTOMER_NONE = new ViewAction[] { ViewAction.ADD_TO_CART,  };
    private static final ViewAction[] CUSTOMER_PREV = new ViewAction[] { ViewAction.ADD_TO_CART, ViewAction.PREVIOUS };
    private static final ViewAction[] CUSTOMER_NEXT = new ViewAction[] { ViewAction.ADD_TO_CART, ViewAction.NEXT };
    private static final ViewAction[] CUSTOMER_NEXT_AND_PREV
            = new ViewAction[] { ViewAction.ADD_TO_CART, ViewAction.NEXT, ViewAction.PREVIOUS };


    @Override
    public void display(Inventory inventory, UserType userType) {
        int viewStartIdx = 0;
        boolean done = false;
        while (!done) {
            System.out.println();
            Book[] displayChunk = displayInventoryChunk(inventory, viewStartIdx);
            System.out.println();

            // Determine display options
            ViewAction[] availableActions = getViewActions(inventory, viewStartIdx, userType);
            ViewAction actionChoice = (ViewAction) MenuUtil.choicePrompt("Choose Action:", availableActions);

            // Perform chosen action
            if (actionChoice == null) {
                done = true;
            }
            else if (actionChoice == ViewAction.NEXT) {
                viewStartIdx += CHUNK_SIZE;
            }
            else if (actionChoice == ViewAction.PREVIOUS) {
                viewStartIdx -= CHUNK_SIZE;
            }
            else if (actionChoice == ViewAction.ADD_TO_CART) {
                addBookToCartFrom(displayChunk);
            }
            else if (actionChoice == ViewAction.EDIT_BOOK) {
                editBookFrom(displayChunk);
            }
        }
    }

    private void addBookToCartFrom(Book[] chunk) {
        Book book = (Book) MenuUtil.choicePrompt(
                "\nSelect book: ",
                chunk
        );

        if (book == null) {
            return;
        }

        if (book.getAvailability() <= 0) {
            System.out.println("\nSelected book is sold out.");
        }
        else if (cart.getCart().contains(book)) {
            System.out.println("\nBook is already in cart.");
        }
        else {
            cart.add(book);
        }
    }


    private void editBookFrom(Book[] chunk) {
        Book book = (Book) MenuUtil.choicePrompt(
                "\nSelect Book to Edit:",
                chunk
        );
        if (book == null) {
            return;
        }

        boolean doneEditing = false;
        while (!doneEditing) {
            doneEditing = MenuUtil.makeBookEdit(book);
        }
    }


    private static ViewAction[] getViewActions(Inventory inventory, int viewStartIdx, UserType userType) {
        ViewAction[] availableActions;
        boolean noneAhead = viewStartIdx + CHUNK_SIZE >= inventory.getInventory().size();
        boolean noneBehind = viewStartIdx - CHUNK_SIZE < 0;
        if (noneAhead && noneBehind) {
            if (userType == UserType.CUSTOMER) {
                availableActions = CUSTOMER_NONE;
            }
            else {
                availableActions = ADMIN_NONE;
            }
        }
        else if (noneAhead) {
            if (userType == UserType.CUSTOMER) {
                availableActions = CUSTOMER_PREV;
            }
            else {
                availableActions = ADMIN_PREV;
            }
        }
        else if (noneBehind) {
            if (userType == UserType.CUSTOMER) {
                availableActions = CUSTOMER_NEXT;
            }
            else {
                availableActions = ADMIN_NEXT;
            }
        }
        else {
            if (userType == UserType.CUSTOMER) {
                availableActions = CUSTOMER_NEXT_AND_PREV;
            }
            else {
                availableActions = ADMIN_NEXT_AND_PREV;
            }
        }
        return availableActions;
    }

    public static Book[] displayInventoryChunk(Inventory inventory, int startIndex) {
        // Print header
        System.out.printf("%-30s%-30s%-30s%-10s%s\n", "Title", "Author", "Genre", "Price", "Availability");

        // Print rows
        Book[] chunk = new Book[Math.min(CHUNK_SIZE, inventory.getInventory().size() - startIndex)];
        for (int inventoryIndex = startIndex, chunkIndex = 0;
             inventoryIndex < inventory.getInventory().size() && inventoryIndex < startIndex + CHUNK_SIZE;
             inventoryIndex++, chunkIndex++)
        {
            chunk[chunkIndex] = inventory.getInventory().get(inventoryIndex);
            System.out.println(inventory.getInventory().get(inventoryIndex).toLineDisplay());
        }

        // Print page position
        System.out.printf("Viewing %d-%d (out of %d)\n",
                startIndex + 1,
                Math.min(startIndex + CHUNK_SIZE, inventory.getInventory().size()),
                inventory.getInventory().size());
        return chunk;
    }


    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }
}
