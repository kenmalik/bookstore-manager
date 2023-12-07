package main.java.menus;

import main.java.objects.*;
import main.java.utilities.MenuUtil;
import main.java.utilities.ProgramMenu;
import main.java.utilities.PromptSelection;
import main.java.utilities.UserType;

public class CheckoutMenu implements ProgramMenu {
    private enum CheckoutOption implements PromptSelection {
        VIEW_CART, GENERATE_INVOICE;

        @Override
        public String getLabel() {
            return (name().charAt(0) + name().substring(1).toLowerCase()).replace("_", " ");
        }
    }

    private ShoppingCart cart;
    private Customer customer;
    private Payment payment;

    @Override
    public void display(Inventory inventory, UserType userType) {
        Order order = new Order(customer, cart);

        boolean done = false;
        while (!done) {
            System.out.println();
            CheckoutOption actionChoice = (CheckoutOption) MenuUtil.choicePrompt(
                    "Choose Action:",
                    CheckoutOption.VIEW_CART,
                    CheckoutOption.GENERATE_INVOICE
            );

            if (actionChoice == null) {
                done = true;
            }
            else if (actionChoice == CheckoutOption.VIEW_CART) {
                printCart();
            }
            else if (actionChoice == CheckoutOption.GENERATE_INVOICE) {
                boolean paymentInfoReceived = setPaymentInfo(order);
                if (paymentInfoReceived) {
                    order.setPayment(payment);
                    System.out.println("\n" + order.generateInvoice());
                }
                done = true;
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
        Payment.PaymentType paymentType = (Payment.PaymentType) MenuUtil.choicePrompt(
                "Enter Payment Type:",
                Payment.PaymentType.CASH,
                Payment.PaymentType.CARD
        );

        if (paymentType == null) {
            return false;
        }

        switch (paymentType) {
            case CASH -> {
                return setCashPayment(order);
            }
            case CARD -> {
                return setCardPayment();
            }
            default -> {
                return false;
            }
        }
    }


    private boolean setCashPayment(Order order) {
        System.out.println();
        double cashPaid = MenuUtil.getDoubleInput("Input amount of cash paid: ");

        while (cashPaid - order.getTotalCost() < 0) {
            System.out.println("\nInsufficient cash.");
            PromptSelection.StandardOption actionChoice = (PromptSelection.StandardOption) MenuUtil.choicePrompt(
                    "Reenter Cash Paid?",
                    PromptSelection.StandardOption.YES
            );

            if (actionChoice == null) {
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
                PromptSelection.StandardOption actionChoice = (PromptSelection.StandardOption) MenuUtil.choicePrompt(
                        "Reenter card info?",
                        PromptSelection.StandardOption.YES
                );

                if (actionChoice == null) {
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
