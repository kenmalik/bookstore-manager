package main.java.objects;

/**
 * A class that provides functionality for payment validation and processing.
 */
public class Payment {
    public enum PaymentType { CARD, CASH }

    /**
     * An enumeration that represents a credit/debit card type.
     */
    public enum CardType {
        VISA(4), MASTER_CARD(5), DISCOVER(6);
        private final int firstDigit;
        CardType(int firstDigit) { this.firstDigit = firstDigit; }
    }

    private final PaymentType paymentType;


    // For cards
    private CardType cardType;
    private String cardNumber;
    private String expirationDate;

    // For cash
    private double cashPaid;


    public Payment() {
        paymentType = null;
        cardType = null;
        cardNumber = "";
        cashPaid = -1;
    }


    public Payment(String cardNumber, String expirationDate) {
        paymentType = PaymentType.CARD;
        this.cardNumber = cardNumber;
        cardType = CardType.VISA;
        this.expirationDate = expirationDate;
        cashPaid = -1;
    }


    public Payment(double cashPaid) {
        paymentType = PaymentType.CASH;
        this.cashPaid = cashPaid;
    }


    public PaymentType getPaymentType() {
        return paymentType;
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
        return "X".repeat(cardNumber.length() - 4)
                +  cardNumber.substring(cardNumber.length() - 4);
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
        if (paymentType == PaymentType.CASH) {
            return false;
        }
        return isValidCardDigits() && isValidCardExpiration();
    }

    /**
     * Returns whether the card number is valid.
     * @return whether the card number is valid.
     */
    private boolean isValidCardDigits() {
        final int MIN_CARD_DIGIT_COUNT = 8;
        final int MAX_CARD_DIGIT_COUNT = 19;
        boolean isValidLength = cardNumber.length() >= MIN_CARD_DIGIT_COUNT
                && cardNumber.length() <= MAX_CARD_DIGIT_COUNT;

        boolean isValidFirstDigit = false;
        for (CardType type : CardType.values()) {
            if (Character.getNumericValue(cardNumber.charAt(0)) == type.firstDigit) {
                isValidFirstDigit = true;
                break;
            }
        }

        return isValidFirstDigit && isValidLength;
    }

    /**
     * Returns whether the card expiration is valid.
     * @return whether the card expiration is valid.
     */
    private boolean isValidCardExpiration() {
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
     * (If payment type is cash) returns whether the cash provided was adequate. -1 means cash paid was less than cost,
     * 0 means cash paid was equal to cost, 1 means cash paid was greater than cost.
     * @return whether the cash provided was adequate.
     */
    public int validateCash(double total) {
        if (paymentType == PaymentType.CARD) {
            return -1;
        }
        return Double.compare(cashPaid, total);
    }
}
