package main.java.menus;

import main.java.objects.Book;
import main.java.objects.Inventory;

import java.util.Scanner;

public class AddBookMenu implements ProgramMenu {
    @Override
    public void display(Inventory inventory) {
        boolean done = false;
        while (!done) {
            System.out.println();
            Book newBook = createBook();

            System.out.println();
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
                case Menus.DONE_DISPLAYING -> done = true;
            }
        }
    }


    public Book createBook() {
        return new Book(
                getStringInput("Input title of book: "),
                getStringInput("Input author of book: "),
                getStringInput("Input genre of book: "),
                getDoubleInput("Input price of book: "),
                getIntInput("Input availability of book: ")
        );
    }


    public String getStringInput(String message) {
        System.out.print(message);
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }


    public double getDoubleInput(String message) {
        Scanner in = new Scanner(System.in);

        boolean doubleInputted = false;
        while (!doubleInputted) {
            System.out.print(message);
            if (in.hasNextDouble()) {
                doubleInputted = true;
            }
            else {
                System.out.println(" ! Please enter a double.");
                in.nextLine();
            }
        }

        return in.nextDouble();
    }


    public int getIntInput(String message) {
        Scanner in = new Scanner(System.in);

        boolean integerInputted = false;
        while (!integerInputted) {
            System.out.print(message);
            if (in.hasNextInt()) {
                integerInputted = true;
            }
            else {
                System.out.println(" ! Please enter an integer.");
                in.nextLine();
            }
        }

        return in.nextInt();
    }
}
