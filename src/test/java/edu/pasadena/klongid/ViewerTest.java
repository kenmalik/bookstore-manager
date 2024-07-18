package edu.pasadena.klongid;

import edu.pasadena.klongid.objects.Book;
import edu.pasadena.klongid.objects.Inventory;
import edu.pasadena.klongid.objects.Viewer;

public class ViewerTest {
    public static void main(String[] args) {
        Inventory testInv = new Inventory();
        testInv.add(new Book("title 1", "author 1", "genre 1", 1, 5));
        testInv.add(new Book("title 2", "author 2", "genre 2", 2, 6));
        testInv.add(new Book("title 3", "author 3", "genre 3", 3, 7));
        testInv.add(new Book("title 4", "author 4", "genre 4", 4, 8));
        testInv.add(new Book("title 5", "author 5", "genre 5", 5, 9));
        testInv.add(new Book("title 6", "author 6", "genre 6", 1, 5));
        testInv.add(new Book("title 7", "author 7", "genre 7", 2, 6));
        testInv.add(new Book("title 8", "author 8", "genre 8", 3, 7));
        testInv.add(new Book("title 9", "author 9", "genre 9", 4, 8));
        testInv.add(new Book("title 10", "author 10", "genre 10", 5, 9));
        testInv.add(new Book("title 1", "author 1", "genre 1", 1, 5));
        testInv.add(new Book("title 2", "author 2", "genre 2", 2, 6));
        testInv.add(new Book("title 3", "author 3", "genre 3", 3, 7));
        testInv.add(new Book("title 4", "author 4", "genre 4", 4, 8));
        testInv.add(new Book("title 5", "author 5", "genre 5", 5, 9));
        testInv.add(new Book("title 6", "author 6", "genre 6", 1, 5));
        testInv.add(new Book("title 7", "author 7", "genre 7", 2, 6));
        testInv.add(new Book("title 8", "author 8", "genre 8", 3, 7));
        testInv.add(new Book("title 9", "author 9", "genre 9", 4, 8));
        testInv.add(new Book("title 10", "author 10", "genre 10", 5, 9));

        Viewer viewer = new Viewer(testInv.getInventory());
        viewer.displayInventoryChunk(0);
    }
}
