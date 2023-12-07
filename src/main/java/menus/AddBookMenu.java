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

            // Confirm choices
            System.out.println("\n" + newBook);
            MenuUtil.StandardOption actionChoice = (MenuUtil.StandardOption) MenuUtil.choicePrompt(
                    "Confirm input:",
                    MenuUtil.StandardOption.YES,
                    MenuUtil.StandardOption.NO
            );

            if (actionChoice == null) {
                done = true;
            }
            else if (actionChoice == MenuUtil.StandardOption.YES) {
                inventory.add(newBook);
                done = true;
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
