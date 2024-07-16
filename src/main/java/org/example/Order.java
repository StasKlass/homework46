package org.example;

import java.time.LocalDate;
import java.util.List;

class Order {
    private final int id;
    private final Customer customer;
    private final List<OrderItem> items;
    private final LocalDate orderDate;
    private final double totalAmount;
    private String status;

    public Order(int id, Customer customer, List<OrderItem> items, LocalDate orderDate, double totalAmount, String status) {
        this.id = id;
        this.customer = customer;
        this.items = items;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID заказа: " + id + ", Клиент: " + customer.getName() + ", Дата заказа: " + orderDate + ", Общая стоимость: $" + totalAmount + ", Статус: " + status;
    }
}
