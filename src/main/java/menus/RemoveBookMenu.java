package main.java.menus;

import main.java.objects.Book;
import main.java.objects.Inventory;

import java.util.ArrayList;

public class RemoveBookMenu implements ProgramMenu {
    @Override
    public void display(Inventory inventory, UserType userType) {
        boolean done = false;
        while (!done) {
            System.out.println();
            int removalChoice = MenuUtil.choicePrompt(
                    "Choose book to remove:",
                    getInventoryStrings(inventory)
            );

            if (removalChoice == MenuOption.DONE_DISPLAYING) {
                done = true;
            }
            else {
                System.out.println();
                done = confirmRemoval(inventory, inventory.getInventory().get(removalChoice - 1));
            }
        }
    }


    private String[] getInventoryStrings(Inventory inventory) {
        ArrayList<String> namesAndAuthors = new ArrayList<>();
        for (Book book : inventory.getInventory()) {
            namesAndAuthors.add(book.toString());
        }
        return namesAndAuthors.toArray(new String[0]);
    }


    private boolean confirmRemoval(Inventory inventory, Book toRemove) {
        int removalConfirmation = MenuUtil.choicePrompt(
                "Confirm Removal:\n" + toRemove,
                "Yes",
                "No"
        );

        if (removalConfirmation == MenuOption.DONE_DISPLAYING) {
            return true;
        }
        else if (removalConfirmation == 1) {
            inventory.remove(toRemove);
            return true;
        }

        return false;
    }
}
