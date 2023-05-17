package com.example.app.ui.models;

import java.util.List;

public class OrderModel {
    private String orderId;
    private String email;
    private String address;
    private String contact_number;
    private String total_price;
    private String payment_method;
    private String status;
    private List<CartModel> orderItems;

    public OrderModel() {
        // Empty constructor needed for Firebase
    }
    public OrderModel(String orderId, String email, String address, String contact_number,
                      String total_price, String payment_method, String status) {
        this.orderId = orderId;
        this.email = email;
        this.address = address;
        this.contact_number = contact_number;
        this.total_price = total_price;
        this.payment_method = payment_method;
        this.status = status;
    }
    public List<CartModel> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<CartModel> orderItems) {
        this.orderItems = orderItems;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}