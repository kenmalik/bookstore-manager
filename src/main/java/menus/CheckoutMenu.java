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

        // To determine available actions
        boolean customerInfoSet = false;
        boolean paymentInfoSet = false;
        Action[] availableActions;

        boolean done = false;
        while (!done) {
            printCart();

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
                customerInfoSet = setCustomerInfo();
            }
            else if (actionChoice == Action.SET_PAYMENT_INFO) {
                paymentInfoSet = setPaymentInfo(order);
            }
            else if (actionChoice == Action.GENERATE_INVOICE) {
                order.setCustomer(customer);
                order.setPayment(payment);
                order.setCart(cart);
                System.out.println("\n" + order.generateInvoice());
                cart.getCart().clear();
                done = true;
            }
        }
    }


    private void printCart() {
        System.out.println("\nYour Cart:");
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


    private boolean setCustomerInfo() {
        customer = new Customer(
                MenuUtil.getStringInput("Enter name: "),
                MenuUtil.getStringInput("Enter phone number: "),
                MenuUtil.getStringInput("Enter email: "),
                MenuUtil.getStringInput("Enter address: "),
                0
        );
        return true;
    }
}
