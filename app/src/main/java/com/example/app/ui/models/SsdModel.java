// SsdModel.java

package com.example.app.ui.models;

public class SsdModel {
    private String productName;
    private String ssdInterface;
    private String ssdCapacity;
    private String productPrice;
    private String productImage;

    public SsdModel(String productName, String ssdInterface, String ssdCapacity, String productPrice, String productImage) {
        this.productName = productName;
        this.ssdInterface = ssdInterface;
        this.ssdCapacity = ssdCapacity;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public String getSsdInterface() {
        return ssdInterface;
    }

    public String getSsdCapacity() {
        return ssdCapacity;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductImage() {
        return productImage;
    }
}
