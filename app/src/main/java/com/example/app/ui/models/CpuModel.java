package com.example.app.ui.models;

public class CpuModel {
    String cpuProductName;
    String cpuProductPrice;
    int image;

    public CpuModel(String cpuProductName, String cpuProductPrice, int image) {
        this.cpuProductName = cpuProductName;
        this.cpuProductPrice = cpuProductPrice;
        this.image = image;
    }

    public String getCpuProductName() {
        return cpuProductName;
    }

    public String getCpuProductPrice() {
        return cpuProductPrice;
    }

    public int getImage() {
        return image;
    }
}
