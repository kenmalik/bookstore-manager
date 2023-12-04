package test.java;

import main.java.objects.Book;
import main.java.objects.ShoppingCart;

public class ShoppingCartTest {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        cart.add(new Book());
        cart.add(new Book("A Promised Land", "Barack Obama", "Biography", 30, 5));
        System.out.println("Before Removal:");
        System.out.println(cart);

        cart.remove(new Book());
        System.out.println("After Removal:");
        System.out.println(cart);
    }
}
