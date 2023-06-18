package com.example.app.ui.models;

public class GpuModel {
    private String productName;
    private String gpuMemoryType;
    private String gpuChipset;
    private String productPrice;
    private String productImage;

    public GpuModel(String productName, String gpuMemoryType, String gpuChipset, String productPrice, String productImage) {
        this.productName = productName;
        this.gpuMemoryType = gpuMemoryType;
        this.gpuChipset = gpuChipset;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public String getGpuMemoryType() {
        return gpuMemoryType;
    }

    public String getGpuChipset() {
        return gpuChipset;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductImage() {
        return productImage;
    }
}