package main.java;

public class Main {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        inventory.add(new Book());
        inventory.add(new Book("A Promised Land", "Barack Obama", "Biography", 30, 5));
        System.out.println("Before Removal:");
        displayInventory(inventory);

        inventory.remove(new Book());
        System.out.println("After Removal:");
        displayInventory(inventory);
    }


    public static void displayInventory(Inventory inventory) {
        for (Book book : inventory.getInventory()) {
            System.out.println(book);
        }
    }
}
