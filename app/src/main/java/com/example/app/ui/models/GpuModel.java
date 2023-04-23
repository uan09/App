package com.example.app.ui.models;

public class GpuModel {
    String gpuProductName;
    String gpuProductPrice;
    int image;

    public GpuModel(String gpuProductName, String gpuProductPrice, int image) {
        this.gpuProductName = gpuProductName;
        this.gpuProductPrice = gpuProductPrice;
        this.image = image;
    }

    public String getGpuProductName() {
        return gpuProductName;
    }

    public String getGpuProductPrice() {
        return gpuProductPrice;
    }

    public int getImage() {
        return image;
    }
}
