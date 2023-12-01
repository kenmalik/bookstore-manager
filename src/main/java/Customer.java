package main.java;

/**
 * A class that represents a customer at a store, storing their name, contact info, and loyalty discounts.
 */
public class Customer {
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private double discountsAvailable;


    /**
     * Constructs a customer with default values.
     */
    public Customer() {
        this("N/A", "N/A", "N/A", "N/A", -1);
    }


    /**
     * Constructs a customer with a given set of data.
     * @param name the customer's name.
     * @param phoneNumber the customer's phone number.
     * @param email the customer's email.
     * @param address the customer's address.
     * @param discountsAvailable the amount of discounts (in dollars) that the customer has.
     */
    public Customer(String name, String phoneNumber, String email, String address, double discountsAvailable) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.discountsAvailable = discountsAvailable;
    }

    /**
     * Returns the customer's name.
     * @return the customer's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the customer's phone number.
     * @return the customer's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the customer's email.
     * @return the customer's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the customer's address.
     * @return the customer's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the customer's amount of discounts (in dollars) available.
     * @return the customer's amount of discounts (in dollars) available.
     */
    public double getDiscountsAvailable() {
        return discountsAvailable;
    }

    /**
     * Sets the customer's name.
     * @param name the customer's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the customer's phone number.
     * @param phoneNumber the customer's phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets the customer's email.
     * @param email the customer's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the customer's address.
     * @param address the customer's address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets the customer's discounts available (in dollars).
     * @param discountsAvailable the customer's discounts available (in dollars).
     */
    public void setDiscountsAvailable(double discountsAvailable) {
        this.discountsAvailable = discountsAvailable;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", discountsAvailable=" + discountsAvailable +
                '}';
    }
}
