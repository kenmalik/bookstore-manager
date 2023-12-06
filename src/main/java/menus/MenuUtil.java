package main.java.menus;

import main.java.objects.Book;
import main.java.objects.Inventory;
import java.util.Scanner;

public class MenuUtil {
    /**
     * Prints a menu of options and returns what the user inputs.
     * @param prompt the menu prompt.
     * @param options the options available to choose.
     * @return the integer that the user inputs.
     */
    public static int choicePrompt(String prompt, String... options) {
        System.out.println(prompt);
        printList(true, options);
        System.out.printf("%d. Quit\n", options.length + 1);

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print(">>> ");
            if (in.hasNextInt()) {
                int input = in.nextInt();
                if (input > options.length) {
                    return -1;
                }
                else if (input > 0) {
                    return input;
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
}
