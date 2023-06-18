// HddModel.java

package com.example.app.ui.models;

public class HddModel {
    private String productName;
    private String hddInterface;
    private String hddCapacity;
    private String productPrice;
    private String productImage;

    public HddModel(String productName, String hddInterface, String hddCapacity, String productPrice, String productImage) {
        this.productName = productName;
        this.hddInterface = hddInterface;
        this.hddCapacity = hddCapacity;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public String getHddInterface() {
        return hddInterface;
    }

    public String getHddCapacity() {
        return hddCapacity;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductImage() {
        return productImage;
    }
}
