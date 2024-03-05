package edu.pasadena.klongid.objects;

import java.util.ArrayList;

public class Viewer {
    private static final int CHUNK_SIZE = 10;
    private final ArrayList<Book> books;

    public Viewer(ArrayList<Book> books) {
        this.books = books;
    }

    /**
     * Displays a chunk of an inventory starting from a given index.
     *
     * @param inventory  the inventory to view.
     * @param startIndex the index from which the view is starting.
     * @return an array of the books visible in the current chunk.
     */
    public Book[] displayInventoryChunk(int startIndex) {
        String HEADER = String.format("%-30s%-30s%-30s%-10s%s\n", "Title", "Author", "Genre", "Price", "Availability");
        System.out.print(HEADER);

        // Print rows
        Book[] chunk = new Book[Math.min(CHUNK_SIZE, books.size() - startIndex)];
        for (int inventoryIndex = startIndex, chunkIndex = 0;
             inventoryIndex < books.size() && inventoryIndex < startIndex + CHUNK_SIZE;
             inventoryIndex++, chunkIndex++) {
            chunk[chunkIndex] = books.get(inventoryIndex);
            System.out.println(books.get(inventoryIndex).toLineDisplay());
        }

        // Print page position
        System.out.printf("Viewing %d-%d (out of %d)\n",
                startIndex + 1,
                Math.min(startIndex + CHUNK_SIZE, books.size()),
                books.size());
        return chunk;
    }
}
