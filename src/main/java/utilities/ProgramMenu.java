package main.java.utilities;

import main.java.objects.Inventory;

/**
 * An interface that defines the behavior of a menu in the program.
 */
public interface ProgramMenu {
    void display(Inventory inventory, UserType userType);
}
