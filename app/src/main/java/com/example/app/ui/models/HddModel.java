// HddModel.java

package com.example.app.ui.models;

public class HddModel {
    private String product_name;
    private String HDD_Interface;
    private String HDD_Capacity;
    private String product_price;
    private String product_image;

    public HddModel(String product_name, String HDD_Interface, String HDD_Capacity, String product_price, String product_image) {
        this.product_name = product_name;
        this.HDD_Interface = HDD_Interface;
        this.HDD_Capacity = HDD_Capacity;
        this.product_price = product_price;
        this.product_image = product_image;
    }

    public HddModel() {
        // Default constructor required for Firebase Firestore deserialization
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getHDD_Interface() {
        return HDD_Interface;
    }

    public String getHDD_Capacity() {
        return HDD_Capacity;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getProduct_image() {
        return product_image;
    }
}
