package edu.pasadena.klongid.menus;

import edu.pasadena.klongid.objects.Book;
import edu.pasadena.klongid.objects.Inventory;
import edu.pasadena.klongid.utilities.MenuUtil;
import edu.pasadena.klongid.utilities.ProgramMenu;
import edu.pasadena.klongid.utilities.PromptSelection;
import edu.pasadena.klongid.utilities.UserType;

/**
 * A menu to add books to inventory.
 */
public class AddBookMenu implements ProgramMenu {
    @Override
    public void display(Inventory inventory, UserType userType) {
        boolean done = false;
        while (!done) {
            System.out.println();
            Book newBook = createBook();

            // Confirm choices
            System.out.println("\n" + newBook);
            PromptSelection.StandardOption actionChoice = (PromptSelection.StandardOption) MenuUtil.choicePrompt(
                    "Confirm input:",
                    PromptSelection.StandardOption.YES,
                    PromptSelection.StandardOption.NO
            );

            if (actionChoice == null) {
                done = true;
            } else if (actionChoice == PromptSelection.StandardOption.YES) {
                inventory.add(newBook);
                done = true;
            }
        }
    }

    /**
     * Prompts input for the construction of a new book object.
     *
     * @return the new book object.
     */
    private Book createBook() {
        return new Book(
                MenuUtil.getStringInput("Input title of book: "),
                MenuUtil.getStringInput("Input author of book: "),
                MenuUtil.getStringInput("Input genre of book: "),
                MenuUtil.getDoubleInput("Input price of book: "),
                MenuUtil.getIntInput("Input availability of book: ")
        );
    }
}
