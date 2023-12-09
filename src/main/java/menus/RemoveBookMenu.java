package main.java.menus;

import main.java.objects.Book;
import main.java.objects.Inventory;
import main.java.utilities.MenuUtil;
import main.java.utilities.ProgramMenu;
import main.java.utilities.PromptSelection;
import main.java.utilities.UserType;

/**
 * A menu to remove books from inventory.
 */
public class RemoveBookMenu implements ProgramMenu {
    @Override
    public void display(Inventory inventory, UserType userType) {
        boolean done = false;
        while (!done) {
            System.out.println();
            Book removalChoice = (Book) MenuUtil.choicePrompt(
                    "Choose book to remove:",
                    inventory.getInventory().toArray(new Book[0])
            );

            if (removalChoice == null) {
                done = true;
            } else {
                System.out.println();
                done = confirmRemoval(inventory, removalChoice);
            }
        }
    }

    /**
     * Prompts user to confirm removal of a book.
     *
     * @param inventory the inventory to remove book from.
     * @param toRemove  the book to remove from inventory.
     * @return whether a book was removed.
     */
    private boolean confirmRemoval(Inventory inventory, Book toRemove) {
        System.out.println(toRemove);
        PromptSelection.StandardOption removalConfirmation = (PromptSelection.StandardOption) MenuUtil.choicePrompt(
                "Confirm Removal:",
                PromptSelection.StandardOption.YES,
                PromptSelection.StandardOption.NO
        );

        if (removalConfirmation == null) {
            return false;
        } else if (removalConfirmation == PromptSelection.StandardOption.YES) {
            inventory.remove(toRemove);
            return true;
        }

        return false;
    }
}
