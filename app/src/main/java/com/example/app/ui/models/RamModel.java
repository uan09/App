package com.example.app.ui.models;

public class RamModel {
    private String memoryName;
    private String memoryType;
    private String memoryCapacity;
    private String productImage;
    private String productPrice;

    public RamModel() {
    }

    public RamModel(String memoryName, String memoryType, String memoryCapacity, String productImage, String productPrice) {
        this.memoryName = memoryName;
        this.memoryType = memoryType;
        this.memoryCapacity = memoryCapacity;
        this.productImage = productImage;
        this.productPrice = productPrice;
    }

    public String getMemoryName() {
        return memoryName;
    }

    public void setMemoryName(String memoryName) {
        this.memoryName = memoryName;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }

    public String getMemoryCapacity() {
        return memoryCapacity;
    }

    public void setMemoryCapacity(String memoryCapacity) {
        this.memoryCapacity = memoryCapacity;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}