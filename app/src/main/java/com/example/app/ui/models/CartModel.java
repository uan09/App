package com.example.app.ui.models;

public class CartModel {
    private String cart_id;
    private String product_name;
    private String product_type;
    private String product_price;
    private String product_number;
    private String product_image_url;
    public CartModel() {}


    public CartModel(String cart_id, String product_name, String product_type, String product_price, String product_number, String product_image_url) {
        this.cart_id = cart_id;
        this.product_name = product_name;
        this.product_type = product_type;
        this.product_price = product_price;
        this.product_number = product_number;
        this.product_image_url = product_image_url;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_number() {
        return product_number;
    }

    public void setProduct_number(String product_number) {
        this.product_number = product_number;
    }

    public String getProduct_image_url() {
        return product_image_url;
    }

    public void setProduct_image_url(String product_image_url) {
        this.product_image_url = product_image_url;
    }
}