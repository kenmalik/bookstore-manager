package edu.pasadena.klongid;

import edu.pasadena.klongid.objects.Book;
import edu.pasadena.klongid.objects.Inventory;

public class InventoryTest {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        inventory.add(new Book());
        inventory.add(new Book("A Promised Land", "Barack Obama", "Biography", 30, 5));
        System.out.println("Before Removal:");
        System.out.println(inventory);

        inventory.remove(new Book());
        System.out.println("After Removal:");
        System.out.println(inventory);
    }
}
