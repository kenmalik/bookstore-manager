package main.java.objects;

import main.java.utilities.MenuUtil;

import java.util.ArrayList;

/**
 * A class that provides order processing and invoice generation functionality.
 */
public class Order {
    private ArrayList<Book> order;
    private Customer customer;
    private Payment payment;


    /**
     * Constructs an order with an empty cart and customer object.
     */
    public Order() {
        this.order = new ArrayList<>();
        this.customer = new Customer();
        this.payment = new Payment();
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


    public void setPayment(Payment payment) {
        this.payment = payment;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public void setCart(ShoppingCart cart) {
        this.order = cart.getCart();
    }


    /**
     * Generates an invoice from the given customer and list of items.
     * @return the invoice formatted as a string.
     */
    public String generateInvoice() {
        StringBuilder output = new StringBuilder("--- INVOICE ---\n");

        output.append(String.format("\nCustomer: %s\n", customer.getName()));

        output.append("\nItems Purchased:\n");
        for (Book book : order) {
            String line = MenuUtil.characterPad(book.getTitle(), 30, '.')
                    + String.format(".$%.2f", book.getPrice());
            output.append(line).append("\n");
        }

        double total = getTotalCost();
        double totalAfterDiscounts = getTotalCostAfterDiscounts();
        double discountsAvailable = customer.getDiscountsAvailable();
        double discountsAvailableAfterPurchase = Math.max(0, discountsAvailable - total);
        output.append(String.format("\nTotal: $%.2f", total));

        if (discountsAvailable > 0) {
            output.append(String.format(
                    """

                            Discounts Available: $%.2f
                            Total After Discounts: $%.2f
                            
                            You have $%.2f in discounts remaining.
                            Thank you for being a loyal customer!
                            """,
                    discountsAvailable, totalAfterDiscounts, discountsAvailableAfterPurchase
            ));
        }
        customer.setDiscountsAvailable(discountsAvailableAfterPurchase);

        output.append(String.format("\nPayment Type: %s", payment.getPaymentType()));
        if (payment.getPaymentType() == Payment.PaymentType.CARD) {
            output.append(String.format(" (%s)", payment.getCardType().toString().replace("_", " ")));
            output.append(String.format("\nCard Number: %s", payment.getCensoredCardNumber()));
        }
        else if (payment.getPaymentType() == Payment.PaymentType.CASH) {
            output.append(String.format("\nCash Paid: $%.2f", payment.getCashPaid()));
            output.append(String.format("\nChange Due: $%.2f", payment.getCashPaid() - totalAfterDiscounts));
        }

        output.append("\n\n---------------");

        updateBookAvailability();

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
     * @return the cost of the order after applying discounts.
     */
    public double getTotalCostAfterDiscounts() {
        double finalCost = getTotalCost();
        if (customer.getDiscountsAvailable() > 0) {
            finalCost -= customer.getDiscountsAvailable();
        }
        return Math.max(finalCost, 0);
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
