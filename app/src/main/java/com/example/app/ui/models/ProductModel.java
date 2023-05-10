package com.example.app.ui.models;

import java.util.List;

public class ProductModel {
    private String Product_id, Product_name, Product_type, Product_description, Product_price, Product_quantity, Product_status, First_image_url;
    private List<String> Product_image;
    private boolean selected;
    public ProductModel() {
    }

    public ProductModel(String product_id, String product_name, String product_type, String product_description, String product_price, String product_quantity, String product_status, List<String> product_image) {
        this.Product_id = product_id;
        this.Product_name = product_name;
        this.Product_type = product_type;
        this.Product_description = product_description;
        this.Product_price = product_price;
        this.Product_quantity = product_quantity;
        this.Product_status = product_status;
        this.Product_image = product_image;
        if (product_image != null && !product_image.isEmpty()) {
            this.First_image_url = product_image.get(0);
        }
        this.selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getProduct_id() {
        return Product_id;
    }

    public void setProduct_id(String product_id) {
        Product_id = product_id;
    }

    public String getProduct_name() {
        return Product_name;
    }

    public void setProduct_name(String product_name) {
        Product_name = product_name;
    }

    public String getProduct_type() {
        return Product_type;
    }

    public void setProduct_type(String product_type) {
        Product_type = product_type;
    }

    public String getProduct_description() {
        return Product_description;
    }

    public void setProduct_description(String product_description) {
        Product_description = product_description;
    }

    public String getProduct_price() {
        return Product_price;
    }

    public void setProduct_price(String product_price) {
        Product_price = product_price;
    }

    public String getProduct_quantity() {
        return Product_quantity;
    }

    public void setProduct_quantity(String product_quantity) { Product_quantity = product_quantity; }

    public String getProduct_status() {
        return Product_status;
    }

    public void setProduct_status(String product_status) {
        Product_status = product_status;
    }

    public List<String> getProduct_image() {
        return Product_image;
    }

    public void setProduct_image(List<String> product_image) {
        Product_image = product_image;
        if (product_image != null && !product_image.isEmpty()) {
            this.First_image_url = product_image.get(0);
        }
    }

    public String getFirst_image_url() {
        return First_image_url;
    }

    public void setFirst_image_url(String first_image_url) {
        First_image_url = first_image_url;
    }
}