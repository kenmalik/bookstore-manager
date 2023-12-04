package main.java.objects;

/**
 * An enumeration that represents a credit/debit card type.
 */
public enum CardType {
    VISA(4), MASTER_CARD(5), DISCOVER(6);
    private final int firstDigit;

    CardType(int firstDigit) { this.firstDigit = firstDigit; }
    public int getFirstDigit() { return firstDigit; }
}
