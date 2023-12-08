package main.java.utilities;

import main.java.objects.Book;

import java.util.Scanner;

public class MenuUtil {
    /**
     * Prints a menu of options and returns what the user selects.
     * @param prompt the menu prompt.
     * @param options the options available to choose.
     * @return the prompt selection that the user chooses.
     */
    public static PromptSelection choicePrompt(String prompt, PromptSelection... options) {
        System.out.println(prompt);

        String[] labels = new String[options.length];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = options[i].getLabel();
        }

        printList(true, labels);
        System.out.printf("%d. %s\n", options.length + 1, PromptSelection.StandardOption.QUIT.getLabel());

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print(">>> ");
            if (in.hasNextInt()) {
                int input = in.nextInt();
                if (input == options.length + 1) {
                    return null;
                }
                else if (input > 0 && input <= options.length) {
                    return options[input - 1];
                }
                else {
                    System.out.println("Input out of range.");
                }
            }
            else {
                System.out.println("Please enter an integer.");
                in.nextLine();
            }
        }
    }


    /**
     * Prints a list of items of variable length.
     * @param withNumbers whether to include numbers before each list item.
     * @param items the items to print.
     */
    public static void printList(boolean withNumbers, String... items) {
        for (int i = 0; i < items.length; i++) {
            if (withNumbers) {
                System.out.printf("%d. ", i + 1);
            }
            System.out.println(items[i]);
        }
    }


    public static String characterPad(String message, int size, char padCharacter) {
        int padCount = size - message.length();
        return message + String.valueOf(padCharacter).repeat(Math.max(0, padCount));
    }

    public static double getDoubleInput(String message) {
        Scanner in = new Scanner(System.in);

        boolean doubleInputted = false;
        while (!doubleInputted) {
            System.out.print(message);
            if (in.hasNextDouble()) {
                doubleInputted = true;
            }
            else {
                System.out.println("Please enter a double.");
                in.nextLine();
            }
        }

        return in.nextDouble();
    }

    public static int getIntInput(String message) {
        Scanner in = new Scanner(System.in);

        boolean integerInputted = false;
        while (!integerInputted) {
            System.out.print(message);
            if (in.hasNextInt()) {
                integerInputted = true;
            }
            else {
                System.out.println("Please enter an integer.");
                in.nextLine();
            }
        }

        return in.nextInt();
    }

    public static String getStringInput(String message) {
        Scanner in = new Scanner(System.in);

        String input = "";
        boolean emptyString = true;
        while (emptyString) {
            System.out.print(message);
            input = in.nextLine();
            if (input.isEmpty()) {
                System.out.println("Please enter a nonempty string.");
            }
            else {
                emptyString = false;
            }
        }

        return input;
    }

    public static boolean makeBookEdit(Book book) {
        System.out.println("\n" + book);
        BookEditOption editSelection = (BookEditOption) choicePrompt(
                "Choose Property to Edit:",
                BookEditOption.TITLE,
                BookEditOption.AUTHOR,
                BookEditOption.GENRE,
                BookEditOption.PRICE,
                BookEditOption.AVAILABILITY
        );

        if (editSelection == null) {
            return true;
        }
        else {
            System.out.println();
            switch (editSelection) {
                case TITLE -> book.setTitle(getStringInput("Enter new title: "));
                case AUTHOR -> book.setAuthor(getStringInput("Enter new author: "));
                case GENRE -> book.setGenre(getStringInput("Enter new genre: "));
                case PRICE -> book.setPrice(getDoubleInput("Enter new price: $"));
                case AVAILABILITY -> book.setAvailability(getIntInput("Enter new availability: "));
            }
            return false;
        }
    }

    private enum BookEditOption implements PromptSelection {
        TITLE, AUTHOR, GENRE, PRICE, AVAILABILITY;
        public String getLabel() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }
}
