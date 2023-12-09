package main.java.utilities;

import main.java.objects.Inventory;

/**
 * An interface that defines the behavior of a menu in the program.
 */
public interface ProgramMenu {
    /**
     * Displays the menu.
     * @param inventory the inventory to associate with the menu.
     * @param userType the type of user opening the menu.
     */
    void display(Inventory inventory, UserType userType);
}
