package main.java.menus;

import main.java.objects.*;
import main.java.utilities.MenuUtil;
import main.java.utilities.ProgramMenu;
import main.java.utilities.PromptSelection;
import main.java.utilities.UserType;

public class CheckoutMenu implements ProgramMenu {
    private ShoppingCart cart;
    private Customer customer;
    private Payment payment;

    private enum Action implements PromptSelection {
        SET_CUSTOMER_INFO, SET_PAYMENT_INFO, GENERATE_INVOICE;

        @Override
        public String getLabel() {
            return (name().charAt(0) + name().substring(1).toLowerCase()).replace("_", " ");
        }
    }
    private static final Action[] SETTERS_ONLY
            = new Action[] { Action.SET_CUSTOMER_INFO, Action.SET_PAYMENT_INFO };
    private static final Action[] ALL_ACTIONS
            = new Action[] { Action.SET_CUSTOMER_INFO, Action.SET_PAYMENT_INFO, Action.GENERATE_INVOICE };


    @Override
    public void display(Inventory inventory, UserType userType) {
        Order order = new Order();
        order.setCart(cart);

        // To determine available actions
        boolean customerInfoSet = false;
        boolean paymentInfoSet = false;
        Action[] availableActions;

        boolean done = false;
        while (!done) {
            printCart();
            printCheckBoxes(customerInfoSet, paymentInfoSet);

            if (!(customerInfoSet && paymentInfoSet)) {
                availableActions = SETTERS_ONLY;
            }
            else {
                availableActions = ALL_ACTIONS;
            }

            Action actionChoice = (Action) MenuUtil.choicePrompt(
                    "\nChoose Action:",
                    availableActions
            );

            if (actionChoice == null) {
                done = true;
            }
            else if (actionChoice == Action.SET_CUSTOMER_INFO) {
                System.out.println();
                customerInfoSet = setCustomerInfo();
                order.setCustomer(customer);
            }
            else if (actionChoice == Action.SET_PAYMENT_INFO) {
                System.out.println();
                paymentInfoSet = setPaymentInfo(order);
                order.setPayment(payment);
            }
            else if (actionChoice == Action.GENERATE_INVOICE) {
                System.out.println("\n" + order.generateInvoice());
                cart.getCart().clear();
                done = true;
            }
        }
    }


    private void printCart() {
        System.out.println("\nYour Cart:");
        for (Book book : cart.getCart()) {
            System.out.println(book.toLineDisplay());
        }
    }


    private void printCheckBoxes(boolean customerInfoSet, boolean paymentInfoSet) {
        final char COMPLETED = 'X';
        final char NOT_COMPLETED = ' ';

        System.out.println("\nRequired for checkout:");
        System.out.printf("[%c] Enter customer information\n", customerInfoSet? COMPLETED : NOT_COMPLETED);
        System.out.printf("[%c] Enter payment information\n", paymentInfoSet? COMPLETED : NOT_COMPLETED);
    }


    private boolean setPaymentInfo(Order order) {
        System.out.printf("Total: $%.2f\n", order.getTotalCost());
        System.out.printf("Total (After Loyalty Discounts): $%.2f\n", order.getTotalCostAfterDiscounts());
        Payment.PaymentType paymentType = (Payment.PaymentType) MenuUtil.choicePrompt(
                "\nEnter Payment Type:",
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
        double totalCost = order.getTotalCostAfterDiscounts();
        double cashPaid = MenuUtil.getDoubleInput("Input amount of cash paid: $");

        while (cashPaid - totalCost < 0) {
            System.out.printf("\nInsufficient cash. $%.2f needed.\n", totalCost - cashPaid);
            PromptSelection.StandardOption actionChoice = (PromptSelection.StandardOption) MenuUtil.choicePrompt(
                    "\nReenter Cash Paid?",
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


    private boolean setCustomerInfo() {
        customer = new Customer(
                MenuUtil.getStringInput("Enter name: "),
                MenuUtil.getStringInput("Enter phone number: "),
                MenuUtil.getStringInput("Enter email: "),
                MenuUtil.getStringInput("Enter address: "),
                MenuUtil.getDoubleInput("Enter discounts available: ")
        );
        return true;
    }
}
