// SsdModel.java

package com.example.app.ui.models;

public class SsdModel {
    private String product_name;
    private String SSD_Interface;
    private String SSD_Capacity;
    private String product_price;
    private String product_image;

    public SsdModel(String product_name, String SSD_Interface, String SSD_Capacity, String product_price, String product_image) {
        this.product_name = product_name;
        this.SSD_Interface = SSD_Interface;
        this.SSD_Capacity = SSD_Capacity;
        this.product_price = product_price;
        this.product_image = product_image;
    }

    public SsdModel() {
        // Default constructor required for Firebase Firestore deserialization
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getSSD_Interface() {
        return SSD_Interface;
    }

    public String getSSD_Capacity() {
        return SSD_Capacity;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getProduct_image() {
        return product_image;
    }
}
