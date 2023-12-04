package main.java.menus;

public class AdminMenu implements ProgramMenu {
    public void display() {
        boolean done = false;

        while (!done) {
            System.out.println("\n--- ADMIN MENU ---");

            int actionChoice = MenuUtil.choicePrompt(
                    "Choose Action:",
                    Menus.BOOK_SEARCH.getLabel(),
                    "Add to inventory",
                    "Remove from inventory"
            );

            switch (actionChoice) {
                case 1 -> Menus.BOOK_SEARCH.display();
                case Menus.DONE_DISPLAYING -> done = true;
            }
        }
    }
}
