package main.java.menus;

import main.java.objects.Inventory;

public class AdminMenu implements ProgramMenu {
    public void display(Inventory inventory, UserType userType) {
        boolean done = false;

        while (!done) {
            System.out.println("\n--- ADMIN MENU ---");

            int actionChoice = MenuUtil.choicePrompt(
                    "Choose Action:",
                    MenuOption.BOOK_SEARCH.getLabel(),
                    MenuOption.ADD_BOOK.getLabel(),
                    MenuOption.REMOVE_BOOK.getLabel(),
                    MenuOption.VIEW_INVENTORY.getLabel()
            );

            switch (actionChoice) {
                case 1 -> MenuOption.BOOK_SEARCH.display(inventory, UserType.ADMIN);
                case 2 -> MenuOption.ADD_BOOK.display(inventory, UserType.ADMIN);
                case 3 -> MenuOption.REMOVE_BOOK.display(inventory, UserType.ADMIN);
                case 4 -> MenuOption.VIEW_INVENTORY.display(inventory, UserType.ADMIN);
                case MenuOption.DONE_DISPLAYING -> done = true;
            }
        }
    }
}
