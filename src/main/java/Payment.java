package main.java;

/**
 * A class that provides functionality for payment validation and processing.
 */
public class Payment {
    private enum PaymentType { CARD, CASH }

    private final PaymentType paymentType;
    private final Order order;

    // For cards
    private CardType cardType;
    private String cardNumber;
    private String expirationDate;

    // For cash
    private double cashPaid;


    public Payment() {
        paymentType = null;
        order = new Order();
        cardType = null;
        cardNumber = "";
        cashPaid = -1;
    }


    public Payment(Order order, String cardNumber) {
        this.order = order;
        paymentType = PaymentType.CARD;
        this.cardNumber = cardNumber;
        cashPaid = -1;
    }


    public Payment(Order order, double cashPaid) {
        this.order = order;
        paymentType = PaymentType.CASH;
        this.cashPaid = cashPaid;
    }


    /**
     * (If payment type is card) returns the type of card used.
     * @return the type of card used.
     */
    public CardType getCardType() {
        return cardType;
    }


    /**
     * (If payment type is card) returns the card number.
     * @return the card number.
     */
    public String getCardNumber() {
        return cardNumber;
    }


    /**
     * (If payment type is card) returns the card number censored except for the last four digits.
     * @return the censored card number.
     */
    public String getCensoredCardNumber() {
        return "";
    }


    /**
     * (If payment type is cash) returns the amount of cash paid by the customer.
     * @return the amount of cash paid by the customer.
     */
    public double getCashPaid() {
        return cashPaid;
    }


    /**
     * (If payment type is card) sets the card number.
     * @param cardNumber the card number.
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }


    /**
     * (If payment type is cash) sets the amount of cash paid by the customer.
     * @param cashPaid the amount of cash paid by the customer.
     */
    public void setCashPaid(double cashPaid) {
        this.cashPaid = cashPaid;
    }


    /**
     * (If payment type is card) returns whether the card is valid.
     * @return whether the card is valid.
     */
    public boolean isValidCard() {
        return isValidCardDigits() && isValidCardExpiration();
    }

    /**
     * Returns whether the card number is valid.
     * @return whether the card number is valid.
     */
    private boolean isValidCardDigits() {
        return false;
    }

    /**
     * Returns whether the card expiration is valid.
     * @return whether the card expiration is valid.
     */
    private boolean isValidCardExpiration() {
        return false;
    }


    /**
     * (If payment type is cash) returns whether the cash provided was adequate.
     * @return whether the cash provided was adequate.
     */
    public double validateCash() {
        return cashPaid - order.getTotalCost();
    }
}
