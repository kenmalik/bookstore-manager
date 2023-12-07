package test.java;

import main.java.objects.Payment;

public class PaymentTest {
    public static void main(String[] args) {
        System.out.println("Card Tests:");

        // Expiration date is current date
        Payment cardPayment = new Payment("4321432143214321", "1223");
        System.out.println(cardPayment.isValidCard());
        System.out.println("Expected: true");

        // Expiration year passed
        cardPayment = new Payment("4321432143214321", "1222");
        System.out.println(cardPayment.isValidCard());
        System.out.println("Expected: false");

        // Expiration month passed
        cardPayment = new Payment("4321432143214321", "1123");
        System.out.println(cardPayment.isValidCard());
        System.out.println("Expected: false");

        // Expiration date is next year
        cardPayment = new Payment("4321432143214321", "1224");
        System.out.println(cardPayment.isValidCard());
        System.out.println("Expected: true");

        // Invalid expiration month
        cardPayment = new Payment("4321432143214321", "0024");
        System.out.println(cardPayment.isValidCard());
        System.out.println("Expected: false");

        // Invalid length
        cardPayment = new Payment("4321432", "1224");
        System.out.println(cardPayment.isValidCard());
        System.out.println("Expected: false");
        cardPayment = new Payment("43214321432143214321", "1224");
        System.out.println(cardPayment.isValidCard());
        System.out.println("Expected: false");

        // Invalid first digit
        cardPayment = new Payment("2321432143214321", "0024");
        System.out.println(cardPayment.isValidCard());
        System.out.println("Expected: false");
    }
}
