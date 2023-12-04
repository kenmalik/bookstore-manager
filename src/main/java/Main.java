package main.java;

import main.java.menus.Menus;
import main.java.menus.MenuUtil;
import main.java.objects.Inventory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // Logger config
        LOGGER.setLevel(Level.ALL);

        // TODO: READ INVENTORY FROM FILE
        LOGGER.log(Level.INFO, "Loading inventory...\n");
        Inventory inventory = new Inventory();

        boolean done = false;
        while (!done) {
            System.out.println();

            int userType = MenuUtil.choicePrompt(
                    "Choose User Type:",
                    Menus.ADMIN.getLabel(),
                    Menus.CUSTOMER.getLabel());

            switch (userType) {
                case 1 -> Menus.ADMIN.display();
                case 2 -> Menus.CUSTOMER.display();
                case Menus.DONE_DISPLAYING -> done = true;
            }
        }
    }
}
