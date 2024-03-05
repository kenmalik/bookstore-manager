package edu.pasadena.klongid;

import edu.pasadena.klongid.objects.*;

public class OrderTest {
    public static void main(String[] args) {
        // Initialize test customer
        Customer customer = new Customer(
                "Kenji",
                "5551231234",
                "email@email.com",
                "123 Street St",
                0
        );

        // Initialize test cart
        ShoppingCart cart = new ShoppingCart();
        cart.add(new Book("Title", "Author", "Genre", 10, 1 ));
        cart.add(new Book("AnotherTitle", "AnotherAuthor", "AnotherGenre", 20, 2 ));
        cart.add(new Book("YetAnotherTitle", "YetAnotherAuthor", "YetAnotherGenre", 30, 3 ));

        // Initialize test payment
        Payment payment = new Payment("612341234", "1224");
        Order order = new Order(customer, cart);
        order.setPayment(payment);
        System.out.println(order.generateInvoice());
    }
}
