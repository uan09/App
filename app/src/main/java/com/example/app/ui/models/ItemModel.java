package com.example.app.ui.models;

import java.util.ArrayList;

public class ItemModel {
    private String product_name, product_type, product_description, product_price, Product_status, product_quantity, Product_id;
    private ArrayList<String> Product_image;

    public ItemModel(String product_name, String product_type, String product_description, String product_price, String statusText, String product_quantity, ArrayList<String> urlsList) {
    }

    public ItemModel(String product_name, String product_type, String product_description, String product_price, String product_status, String product_quantity, String product_id, ArrayList<String> product_image) {
        this.product_name = product_name;
        this.product_type = product_type;
        this.product_description = product_description;
        this.product_price = product_price;
        this.Product_status = product_status;
        this.product_quantity = product_quantity;
        Product_id = product_id;
        Product_image = product_image;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_status() {
        return Product_status;
    }

    public void setProduct_status(String product_status) {
        this.Product_status = product_status;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_id() {
        return Product_id;
    }

    public void setProduct_id(String product_id) {
        Product_id = product_id;
    }

    public ArrayList<String> getProduct_image() {
        return Product_image;
    }

    public void setProduct_image(ArrayList<String> product_image) {
        Product_image = product_image;
    }
}
