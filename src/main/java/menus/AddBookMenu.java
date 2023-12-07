package main.java.menus;

import main.java.objects.Book;
import main.java.objects.Inventory;

public class AddBookMenu implements ProgramMenu {
    @Override
    public void display(Inventory inventory, UserType userType) {
        boolean done = false;
        while (!done) {
            System.out.println();
            Book newBook = createBook();

            System.out.println("\n" + newBook);
            int actionChoice = MenuUtil.choicePrompt(
                    "Confirm input:",
                    "Yes",
                    "No"
            );

            switch (actionChoice) {
                case 1 -> {
                    inventory.add(newBook);
                    done = true;
                }
                case MenuOption.DONE_DISPLAYING -> done = true;
            }
        }
    }


    public Book createBook() {
        return new Book(
                MenuUtil.getStringInput("Input title of book: "),
                MenuUtil.getStringInput("Input author of book: "),
                MenuUtil.getStringInput("Input genre of book: "),
                MenuUtil.getDoubleInput("Input price of book: "),
                MenuUtil.getIntInput("Input availability of book: ")
        );
    }
}
