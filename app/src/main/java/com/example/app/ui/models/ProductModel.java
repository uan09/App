package com.example.app.ui.models;

import android.app.Activity;
import android.content.Context;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

public class ProductModel {
    private String Store_name, Product_id, Product_name, Product_brand, Product_type, Product_description, Product_price, Product_quantity, Product_status, Product_category, First_image_url;
    private List<String> Product_image;

    private Spinner spinnerDropdown;

    private boolean selected;
    public ProductModel() {
    }

    public ProductModel(String store_name, String product_id, String product_name, String product_brand, String product_type, String product_description, String product_price, String product_quantity, String product_status, String product_category,List<String> product_image) {
        this.Store_name = store_name;
        this.Product_id = product_id;
        this.Product_name = product_name;
        this.Product_type = product_type;
        this.Product_brand = product_brand;
        this.Product_description = product_description;
        this.Product_price = product_price;
        this.Product_quantity = product_quantity;
        this.Product_status = product_status;
        this.Product_category= product_category;
        this.Product_image = product_image;
        if (product_image != null && !product_image.isEmpty()) {
            this.First_image_url = product_image.get(0);
        }
        this.selected = false;
    }
    public String getStore_name() {
        return Store_name;
    }

    public void setStore_name(String store_name) {
        Store_name = store_name;
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

    public Spinner getSpinnerDropdown() {
        return spinnerDropdown;
    }

    public void setSpinnerDropdown(Spinner spinnerDropdown) {
        this.spinnerDropdown = spinnerDropdown;
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

    public String getProduct_brand() {
        return Product_brand;
    }

    public void setProduct_brand (String product_brand) {
        Product_brand = product_brand;
    }

    public String getProduct_status() {
        return Product_status;
    }

    public void setProduct_status(String product_status) {
        Product_status = product_status;
    }

    public String getProduct_category() {
        return Product_category;
    }

    public void setProduct_category(String product_category) {
        Product_category= product_category;
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