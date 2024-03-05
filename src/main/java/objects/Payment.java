package main.java.objects;

import main.java.utilities.PromptSelection;

/**
 * A class that provides functionality for payment validation and processing.
 */
public class Payment {
    private final PaymentType paymentType;
    // For cash
    private final double cashPaid;
    // For cards
    private CardType cardType;
    private String cardNumber;
    private String expirationDate;

    /**
     * Constructs a default payment.
     */
    public Payment() {
        paymentType = null;
        cardType = null;
        cardNumber = "";
        cashPaid = -1;
    }

    /**
     * Constructs a card payment with a given number and expiration date.
     *
     * @param cardNumber     the card's number.
     * @param expirationDate the card's expiration date.
     */
    public Payment(String cardNumber, String expirationDate) {
        paymentType = PaymentType.CARD;
        this.cardNumber = cardNumber;
        setCardType();
        this.expirationDate = expirationDate;
        cashPaid = -1;
    }

    /**
     * Constructs a cash payment of a given amount.
     *
     * @param cashPaid the amount of cash paid.
     */
    public Payment(double cashPaid) {
        paymentType = PaymentType.CASH;
        this.cashPaid = cashPaid;
    }

    /**
     * Gets the payment type of the card.
     *
     * @return the payment type of the card.
     */
    public PaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * (If payment type is card) returns the type of card used.
     *
     * @return the type of card used.
     */
    public CardType getCardType() {
        return cardType;
    }

    public boolean setCardType() {
        for (CardType type : CardType.values()) {
            if (Character.getNumericValue(cardNumber.charAt(0)) == type.firstDigit) {
                cardType = type;
                return true;
            }
        }
        return false;
    }

    /**
     * (If payment type is card) returns the card number.
     *
     * @return the card number.
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * (If payment type is card) returns the card number censored except for the last four digits.
     *
     * @return the censored card number.
     */
    public String getCensoredCardNumber() {
        final int VISIBLE_NUMBER_COUNT = 4;
        return "X".repeat(cardNumber.length() - VISIBLE_NUMBER_COUNT)
                + cardNumber.substring(cardNumber.length() - VISIBLE_NUMBER_COUNT);
    }

    /**
     * (If payment type is cash) returns the amount of cash paid by the customer.
     *
     * @return the amount of cash paid by the customer.
     */
    public double getCashPaid() {
        return cashPaid;
    }

    /**
     * (If payment type is card) returns whether the card is valid.
     *
     * @return whether the card is valid.
     */
    public boolean isValidCard() {
        if (paymentType == PaymentType.CASH) {
            return false;
        }
        return isValidCardDigits() && isValidCardExpiration();
    }

    /**
     * Returns whether the card number is valid.
     *
     * @return whether the card number is valid.
     */
    private boolean isValidCardDigits() {
        final int MIN_CARD_DIGIT_COUNT = 8;
        final int MAX_CARD_DIGIT_COUNT = 19;
        boolean isValidLength = cardNumber.length() >= MIN_CARD_DIGIT_COUNT
                && cardNumber.length() <= MAX_CARD_DIGIT_COUNT;

        boolean isValidFirstDigit = setCardType();

        return isValidFirstDigit && isValidLength;
    }

    /**
     * Returns whether the card expiration is valid.
     *
     * @return whether the card expiration is valid.
     */
    private boolean isValidCardExpiration() {
        final int MAX_DATE_DIGIT_COUNT = 4;
        if (expirationDate.length() != MAX_DATE_DIGIT_COUNT) {
            return false;
        }

        final int YEAR_LAST_TWO_DIGITS = 100;
        final int MAX_MONTH = 12;

        // Extract month and year from expiration date string
        int expirationYear = Integer.parseInt(expirationDate.substring(2));
        int expirationMonth = Integer.parseInt(expirationDate.substring(0, 2));

        if (expirationMonth > MAX_MONTH || expirationMonth <= 0 || expirationYear <= 0) {
            return false;
        }

        // Get current month and year
        int currentYear = java.time.LocalDate.now().getYear() % YEAR_LAST_TWO_DIGITS;
        int currentMonth = java.time.LocalDate.now().getMonthValue();

        return expirationYear > currentYear || (expirationYear == currentYear && expirationMonth >= currentMonth);
    }

    /**
     * An enumeration of the different payment types.
     */
    public enum PaymentType implements PromptSelection {
        CARD, CASH;

        @Override
        public String getLabel() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }

    /**
     * An enumeration of the credit/debit card types.
     */
    public enum CardType implements PromptSelection {
        VISA(4), MASTER_CARD(5), DISCOVER(6);
        private final int firstDigit;

        CardType(int firstDigit) {
            this.firstDigit = firstDigit;
        }

        @Override
        public String getLabel() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }
}
