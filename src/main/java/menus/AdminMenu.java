package main.java.menus;

import main.java.objects.Inventory;

public class AdminMenu implements ProgramMenu {
    public void display(Inventory inventory) {
        boolean done = false;

        while (!done) {
            System.out.println("\n--- ADMIN MENU ---");

            int actionChoice = MenuUtil.choicePrompt(
                    "Choose Action:",
                    Menus.BOOK_SEARCH.getLabel(),
                    Menus.ADD_BOOK.getLabel(),
                    "Remove from inventory",
                    Menus.VIEW_INVENTORY.getLabel()
            );

            switch (actionChoice) {
                case 1 -> Menus.BOOK_SEARCH.display(inventory);
                case 2 -> Menus.ADD_BOOK.display(inventory);
                case 4 -> Menus.VIEW_INVENTORY.display(inventory);
                case Menus.DONE_DISPLAYING -> done = true;
            }
        }
    }
}
