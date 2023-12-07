package main.java.objects;

import main.java.menus.MenuUtil;

import java.util.ArrayList;

/**
 * A class that provides order processing and invoice generation functionality.
 */
public class Order {
    private final ArrayList<Book> order;
    private final Customer customer;


    /**
     * Constructs an order with an empty cart and customer object.
     */
    public Order() {
        this.order = new ArrayList<>();
        this.customer = new Customer();
    }


    /**
     * Constructs an order with a given set of data.
     * @param customer the customer who is making the purchase.
     * @param shoppingCart the customer's shopping cart.
     */
    public Order(Customer customer, ShoppingCart shoppingCart) {
        this.customer = customer;
        order = shoppingCart.getCart();
    }


    /**
     * Generates an invoice from the given customer and list of items.
     * @return the invoice formatted as a string.
     */
    public String generateInvoice() {
        StringBuilder output = new StringBuilder();

        output.append(String.format("Customer: %s\n", customer.getName()));

        output.append("\nItems Purchased:\n");
        for (Book book : order) {
            String line = MenuUtil.characterPad(book.getTitle(), 30, '.')
                    + String.format(".$%.2f", book.getPrice());
            output.append(line).append("\n");
        }

        output.append(String.format("\nTotal: $%.2f", getTotalCost()));

        return output.toString();
    }


    /**
     * Gets the total cost of the items in the order.
     * @return the total cost of the items in the order.
     */
    public double getTotalCost() {
        double total = 0;
        for (Book book : order) {
            total += book.getPrice();
        }
        return total;
    }


    /**
     * Gets discounts available from customer and applies them to order.
     * @param originalCost the cost of the order before applying discounts.
     * @return the cost of the order after applying discounts.
     */
    private double applyDiscounts(double originalCost) {
        double finalCost = originalCost;
        finalCost -= customer.getDiscountsAvailable();
        customer.setDiscountsAvailable(0);
        return finalCost;
    }


    /**
     * Updates the availabilities of the books that have been purchased.
     */
    private void updateBookAvailability() {
        for (Book book : order) {
            int currentAvailability = book.getAvailability();
            book.setAvailability(currentAvailability - 1);
        }
    }
}
