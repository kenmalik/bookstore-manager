package main.java.menus;

import main.java.objects.Inventory;

public class BookSearchMenu implements ProgramMenu {
    private enum SearchType {
        TITLE(1), AUTHOR(2), GENRE(3), PRICE(4);
        private final int index;

        SearchType(int index) {
            this.index = index;
        }

        public String getLabel() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }

    @Override
    public void display(Inventory inventory) {
        boolean done = false;

        while (!done) {
            System.out.println();

            int searchType = MenuUtil.choicePrompt(
                    "Search By:",
                    SearchType.TITLE.getLabel(),
                    SearchType.AUTHOR.getLabel(),
                    SearchType.GENRE.getLabel(),
                    SearchType.PRICE.getLabel()
            );

            if (searchType == SearchType.TITLE.index) {
                System.out.println("\nTODO: TITLE SEARCH");
            }
            else if (searchType == SearchType.AUTHOR.index) {
                System.out.println("\nTODO: AUTHOR SEARCH");
            }
            else if (searchType == SearchType.GENRE.index) {
                System.out.println("\nTODO: GENRE SEARCH");
            }
            else if (searchType == SearchType.PRICE.index) {
                System.out.println("\nTODO: PRICE SEARCH");
            }
            else {
                done = true;
            }
        }
    }
}
