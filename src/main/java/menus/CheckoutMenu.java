package main.java.menus;

import main.java.objects.Customer;
import main.java.objects.Inventory;
import main.java.objects.Order;
import main.java.objects.ShoppingCart;

public class CheckoutMenu implements ProgramMenu {
    private ShoppingCart cart;
    private Customer customer;

    @Override
    public void display(Inventory inventory, UserType userType) {
        Order order = new Order(customer, cart);

        boolean done = false;
        while (!done) {
            System.out.println();
            int actionChoice = MenuUtil.choicePrompt(
                    "Choose Action:",
                    "Generate Invoice"
            );

            switch (actionChoice) {
                case 1 -> generateInvoice();
                case MenuOption.DONE_DISPLAYING -> done = true;
            }
        }
    }


    private void generateInvoice() {

    }


    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
