package main.java.menus;

import main.java.objects.Inventory;

import java.util.ArrayList;
import java.util.List;

public class ViewInventoryMenu implements ProgramMenu {
    private static final int CHUNK_SIZE = 10;

    private enum ViewAction {
        NEXT("View Next " + CHUNK_SIZE), PREVIOUS("View Previous" + CHUNK_SIZE);
        private final String label;

        ViewAction(String label) {
            this.label = label;
        }
    }

    @Override
    public void display(Inventory inventory) {
        boolean done = false;

        int viewStartIdx = 0;
        while (!done) {
            System.out.println();

            displayInventoryChunk(inventory, viewStartIdx);
            System.out.println();

            // Determine display options
            String[] availableActions;
            boolean noneAhead = viewStartIdx + CHUNK_SIZE >= inventory.getInventory().size();
            boolean noneBehind = viewStartIdx - CHUNK_SIZE < 0;
            
            if (noneAhead && noneBehind) {
                availableActions = new String[] {};
            }
            else if (noneAhead) {
                availableActions = new String[] { ViewAction.PREVIOUS.label };
            }
            else if (noneBehind) {
                availableActions = new String[] { ViewAction.NEXT.label };
            }
            else {
                availableActions = new String[] { ViewAction.NEXT.label, ViewAction.PREVIOUS.label };
            }
            int actionChoice = MenuUtil.choicePrompt("Choose Action:", availableActions);

            switch (actionChoice) {
                case 1 -> {

                }
                case Menus.DONE_DISPLAYING -> done = true;
            }
        }
    }

    public static void displayInventoryChunk(Inventory inventory, int startIndex) {
        for (int i = startIndex; i < inventory.getInventory().size() && i < CHUNK_SIZE; i++) {
            System.out.println(inventory.getInventory().get(i));
        }
    }
}
