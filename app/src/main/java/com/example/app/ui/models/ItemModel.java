package com.example.app.ui.models;

import java.util.ArrayList;
import java.util.Map;

public class ItemModel {
    private String product_name, product_type, product_description, product_brand, product_category, product_price, Product_status, product_quantity, Product_id, store_name;
    private ArrayList<String> Product_image;
    private String cpu_name, cpu_socket, cpu_clock, cpu_turbo, cpu_cores, cpu_threads;

    public ItemModel(String product_name, String product_type, String product_description, String product_brand, String categoryText, String product_price, String statusText, String product_quantity, ArrayList<String> urlsList) {
    }

    public ItemModel(String store_name, String product_name, String product_type, String product_description, String product_brand, String product_category, String product_price, String product_status, String product_quantity, String product_id, ArrayList<String> product_image) {
        this.store_name = store_name;
        this.product_name = product_name;
        this.product_type = product_type;
        this.product_description = product_description;
        this.product_brand = product_brand;
        this.product_category = product_category;
        this.product_price = product_price;
        this.Product_status = product_status;
        this.product_quantity = product_quantity;
        Product_id = product_id;
        Product_image = product_image;
    }

    // Add a method to add all data fields from a Map to the ItemModel object
    public void addAllDataFields(Map<String, Object> data) {
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = (String) entry.getValue();

            // Set the value of the corresponding field in ItemModel
            switch (key) {
                case "product_name":
                    setProduct_name(value);
                    break;
                case "product_type":
                    setProduct_type(value);
                    break;
                case "product_description":
                    setProduct_description(value);
                    break;
                case "product_brand":
                    setProduct_brand(value);
                    break;
                case "product_category":
                    setProduct_category(value);
                    break;
                case "product_price":
                    setProduct_price(value);
                    break;
                case "product_status":
                    setProduct_status(value);
                    break;
                case "product_quantity":
                    setProduct_quantity(value);
                    break;
                case "cpu_name":
                    setCpu_name(value);
                    break;
                case "cpu_socket":
                    setCpu_socket(value);
                    break;
                case "cpu_clock":
                    setCpu_clock(value);
                    break;
                case "cpu_turbo":
                    setCpu_turbo(value);
                    break;
                case "cpu_cores":
                    setCpu_cores(value);
                    break;
                case "cpu_threads":
                    setCpu_threads(value);
                    break;
                // Add more cases for other fields if needed
                // ...
            }
        }
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
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

    public String getProduct_brand() {
        return product_brand;
    }

    public void setProduct_brand(String product_brand) {
        this.product_brand = product_brand;
    }
    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
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

    public String getCpu_name() {
        return cpu_name;
    }

    public void setCpu_name(String cpu_name) {
        this.cpu_name = cpu_name;
    }

    public String getCpu_socket() {
        return cpu_socket;
    }

    public void setCpu_socket(String cpu_socket) {
        this.cpu_socket = cpu_socket;
    }

    public String getCpu_clock() {
        return cpu_clock;
    }

    public void setCpu_clock(String cpu_clock) {
        this.cpu_clock = cpu_clock;
    }

    public String getCpu_turbo() {
        return cpu_turbo;
    }

    public void setCpu_turbo(String cpu_turbo) {
        this.cpu_turbo = cpu_turbo;
    }

    public String getCpu_cores() {
        return cpu_cores;
    }

    public void setCpu_cores(String cpu_cores) {
        this.cpu_cores = cpu_cores;
    }

    public String getCpu_threads() {
        return cpu_threads;
    }

    public void setCpu_threads(String cpu_threads) {
        this.cpu_threads = cpu_threads;
    }
}
