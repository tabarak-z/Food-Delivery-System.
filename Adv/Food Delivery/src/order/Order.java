package order;

import java.util.ArrayList;
import java.util.List;

import DeliveryOption.DeliveryOption;
import payment.PaymentMethod;
import menu.MenuItem;

public class Order {

    private static int nextOrderNumber = 1001;

    private int orderNumber;
    private String customerName;
    private DeliveryOption deliveryOption;
    private OrderStatus status;
    private List<OrderLine> orderLines;

    public Order(String customerName, DeliveryOption deliveryOption) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }

        if (deliveryOption == null) {
            throw new IllegalArgumentException("Delivery option cannot be null");
        }

        this.orderNumber = nextOrderNumber++;
        this.customerName = customerName;
        this.deliveryOption = deliveryOption;
        this.status = OrderStatus.NEW;
        this.orderLines = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        if (status != OrderStatus.NEW) {
            return;
        }

        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        orderLines.add(new OrderLine(item));
    }

    public void addItem(MenuItem item, int quantity) {
        if (status != OrderStatus.NEW) {
            return;
        }

        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        orderLines.add(new OrderLine(item, quantity));
    }

    public double getSubtotal() {
        double subtotal = 0;

        for (OrderLine line : orderLines) {
            subtotal += line.getLineTotal();
        }

        return subtotal;
    }

    public double getTotal() {
        return getSubtotal() + deliveryOption.getFee();
    }

    public double payOrder(PaymentMethod paymentMethod) {
        if (status != OrderStatus.NEW) {
            return -1;
        }

        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }

        if (orderLines.isEmpty()) {
            throw new IllegalStateException("Cannot pay for an empty order");
        }

        double amountPaid = paymentMethod.pay(getTotal());

        if (amountPaid < 0) {
            return -1;
        }

        status = OrderStatus.PAID;

        return amountPaid;
    }

    public void deliverOrder() {
        if (status == OrderStatus.PAID) {
            status = OrderStatus.DELIVERED;
        }
    }

    public void cancelOrder() {
        if (status == OrderStatus.NEW) {
            status = OrderStatus.CANCELLED;
        }
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
