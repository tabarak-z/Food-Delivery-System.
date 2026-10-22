
import java.util.Scanner;

import menu.MenuItem;
import menu.Meal;
import menu.Drink;

import order.Order;
import order.OrderStatus;

import DeliveryOption.DeliveryOption;
import DeliveryOption.StandardDelivery;
import DeliveryOption.ExpressDelivery;

import payment.PaymentMethod;
import payment.CashPayment;
import payment.CardPayment;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("       FOOD DELIVERY SYSTEM");
        
        String customerName;

        while (true) {
            System.out.print("Enter customer name: ");
            customerName = input.nextLine().trim();

            if (!customerName.isEmpty()) {
                break;
            }

            System.out.println("Error: Name cannot be empty.");
        }
        System.out.println("\nChoose delivery option:");
        System.out.println("1. Standard Delivery - $2.00");
        System.out.println("2. Express Delivery  - $5.00");

        int deliveryChoice = readInt(input, "Your choice: ", 1, 2);
        DeliveryOption deliveryOption;
        if (deliveryChoice == 1) {
            deliveryOption = new StandardDelivery();
        } else {
            deliveryOption = new ExpressDelivery();
        }
        Order order = new Order(customerName, deliveryOption);
        System.out.println("\nOrder created successfully");
        System.out.println("Order number: " + order.getOrderNumber());
        while (true) {

            System.out.println("\n========== MENU ==========");
            System.out.println("1. Burger Meal  - $9.50");
            System.out.println("2. Pizza Meal   - $13.00");
            System.out.println("3. Small Juice  - $3.00");
            System.out.println("4. Large Juice  - $4.00");
            System.out.println("5. Finish adding items");
            System.out.println("6. Cancel order");

            int choice = readInt(input, "Choose an option: ", 1, 6);
            if (choice == 5) {
                if (order.getSubtotal() == 0) {
                    System.out.println(
                        "Your order is empty. Please add an item."
                    );
                    continue;
                }

                break;
            }

            if (choice == 6) {
                order.cancelOrder();
                System.out.println("Order cancelled.");
                System.out.println("Order status: " + order.getStatus());
                input.close();
                return;}

            MenuItem item;

            switch (choice) {
                case 1:
                    item = new Meal("Burger", 8.0, 1);
                    break;

                case 2:
                    item = new Meal("Pizza", 10.0, 2);
                    break;

                case 3:
                    item = new Drink("Juice", 3.0, false);
                    break;

                default:
                    item = new Drink("Juice", 3.0, true);
                    break;}

            int quantity = readInt(
                input, "Enter quantity: ", 1, 100
            );

            order.addItem(item, quantity);

            System.out.println(
                quantity + " x " + item.getName()
                + " added to your order."
            );

            System.out.printf(
                "Current subtotal: $%.2f%n",
                order.getSubtotal()
            );
        }
        System.out.println("\n========== The BILL ==========");
        System.out.println("Order number: " + order.getOrderNumber());
        System.out.println("Customer: " + order.getCustomerName());

        System.out.printf(
            "Subtotal: $%.2f%n", order.getSubtotal()
        );

        System.out.printf(
            "Delivery fee: $%.2f%n",
            order.getTotal() - order.getSubtotal()
        );

        System.out.printf(
            "Total: $%.2f%n", order.getTotal()
        );
        System.out.println("\n1. Pay for order");
        System.out.println("2. Cancel order");

        int checkoutChoice = readInt(input, "Choose an option: ", 1, 2);

        if (checkoutChoice == 2) {
            order.cancelOrder();

            System.out.println("Order cancelled.");
            System.out.println("Order status: " + order.getStatus());

            input.close();
            return;
        }
        System.out.println("\nChoose payment method:");
        System.out.println("1. Cash");
        System.out.println("2. Card (2% fee)");

        int paymentChoice = readInt(input, "Your choice: ", 1, 2);
        PaymentMethod paymentMethod;
        if (paymentChoice == 1) {
            paymentMethod = new CashPayment();
        } else {
            paymentMethod = new CardPayment();
        }
        double amountPaid = order.payOrder(paymentMethod);
        if (amountPaid < 0) {
            System.out.println("Payment failed.");
            System.out.println("Order status: " + order.getStatus());
        } else {
            System.out.printf(
                "Amount paid: $%.2f%n", amountPaid
            );
            System.out.println("Payment successful!");
            System.out.println("Order status: " + order.getStatus());
            order.deliverOrder();

            System.out.println("Order delivered successfully!");
            System.out.println(
                "Final order status: " + order.getStatus()
            );}

        System.out.println("\nThank you for ordering!");
        input.close();
    }
    public static int readInt(
        Scanner input, String message, int min, int max
    ) {
        while (true) {
            System.out.print(message);

            String value = input.nextLine().trim();

            try {
                int number = Integer.parseInt(value);

                if (number >= min && number <= max) {
                    return number;
                }

                System.out.println(
                    "Please enter a number from "
                    + min + " to " + max + "."
                );

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }
    }
}
