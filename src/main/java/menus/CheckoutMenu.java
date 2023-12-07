package main.java.menus;

import main.java.objects.*;

public class CheckoutMenu implements ProgramMenu {
    private ShoppingCart cart;
    private Customer customer;
    private Payment payment;

    @Override
    public void display(Inventory inventory, UserType userType) {
        Order order = new Order(customer, cart);

        boolean done = false;
        while (!done) {
            System.out.println();
            int actionChoice = MenuUtil.choicePrompt(
                    "Choose Action:",
                    "View Cart",
                    "Generate Invoice"
            );

            switch (actionChoice) {
                case 1 -> printCart();
                case 2 -> {
                    boolean paymentInfoReceived = setPaymentInfo(order);
                    if (paymentInfoReceived) {
                        order.setPayment(payment);
                        System.out.println("\n" + order.generateInvoice());
                    }
                    done = true;
                }
                case MenuOption.DONE_DISPLAYING -> done = true;
            }
        }
    }


    private void printCart() {
        System.out.println("\nCart:");
        for (Book book : cart.getCart()) {
            System.out.println(book);
        }
    }


    private boolean setPaymentInfo(Order order) {
        System.out.println();
        int paymentType = MenuUtil.choicePrompt(
                "Enter Payment Type:",
                "Cash",
                "Card"
        );

        switch (paymentType) {
            case 1 -> {
                return setCashPayment(order);
            }
            case 2 -> {
                return setCardPayment();
            }
            case MenuOption.DONE_DISPLAYING -> {
                return false;
            }
        }
        return false;
    }


    private boolean setCashPayment(Order order) {
        System.out.println();
        double cashPaid = MenuUtil.getDoubleInput("Input amount of cash paid: ");

        while (cashPaid - order.getTotalCost() < 0) {
            System.out.println("\nInsufficient cash.");
            int actionChoice = MenuUtil.choicePrompt(
                    "Reenter Cash Paid?",
                    "Yes"
            );

            if (actionChoice == MenuOption.DONE_DISPLAYING) {
                return false;
            }
            cashPaid = MenuUtil.getDoubleInput("Input amount of cash paid: ");
        }

        payment = new Payment(cashPaid);
        return true;
    }


    private boolean setCardPayment() {
        boolean done = false;
        while (!done) {
            System.out.println();

            // Get user input
            String cardNumber = MenuUtil.getStringInput("Input card number (No separators): ");
            String expirationDate = MenuUtil.getStringInput("Input expiration date (MMYY): ");

            // Validate card
            payment = new Payment(cardNumber, expirationDate);
            if (!payment.isValidCard()) {
                System.out.println("\nInvalid card.");
                int actionChoice = MenuUtil.choicePrompt(
                        "Reenter card info?",
                        "Yes"
                );

                if (actionChoice == MenuOption.DONE_DISPLAYING) {
                    done = true;
                }
            }
            else {
                done = true;
            }
        }

        return payment.isValidCard();
    }


    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
