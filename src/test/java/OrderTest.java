package test.java;

import main.java.objects.Book;
import main.java.objects.Customer;
import main.java.objects.Order;
import main.java.objects.ShoppingCart;

public class OrderTest {
    public static void main(String[] args) {
        Customer customer = new Customer(
                "Kenji",
                "5551231234",
                "email@email.com",
                "123 Street St",
                0
        );
        ShoppingCart cart = new ShoppingCart();
        cart.add(new Book("Title", "Author", "Genre", 10, 1 ));
        cart.add(new Book("AnotherTitle", "AnotherAuthor", "AnotherGenre", 20, 2 ));
        cart.add(new Book("YetAnotherTitle", "YetAnotherAuthor", "YetAnotherGenre", 30, 3 ));

        Order order = new Order(customer, cart);
        System.out.println(order.generateInvoice());
    }
}
