package main.java.menus;

import main.java.objects.Book;
import main.java.objects.Inventory;
import main.java.utilities.MenuUtil;
import main.java.utilities.ProgramMenu;
import main.java.utilities.PromptSelection;
import main.java.utilities.UserType;

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
            }
            else {
                System.out.println();
                done = confirmRemoval(inventory, removalChoice);
            }
        }
    }


    private boolean confirmRemoval(Inventory inventory, Book toRemove) {
        PromptSelection.StandardOption removalConfirmation = (PromptSelection.StandardOption) MenuUtil.choicePrompt(
                "Confirm Removal:\n" + toRemove,
                PromptSelection.StandardOption.YES,
                PromptSelection.StandardOption.NO
        );


        if (removalConfirmation == null) {
            return true;
        }
        else if (removalConfirmation == PromptSelection.StandardOption.YES) {
            inventory.remove(toRemove);
            return true;
        }

        return false;
    }
}
