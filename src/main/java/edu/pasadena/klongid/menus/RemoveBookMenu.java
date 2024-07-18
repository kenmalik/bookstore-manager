package edu.pasadena.klongid.menus;

import edu.pasadena.klongid.objects.Book;
import edu.pasadena.klongid.objects.Inventory;
import edu.pasadena.klongid.utilities.MenuUtil;
import edu.pasadena.klongid.utilities.ProgramMenu;
import edu.pasadena.klongid.utilities.PromptSelection;
import edu.pasadena.klongid.utilities.UserType;

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
