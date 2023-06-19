package com.example.app.ui.models;

public class GpuModel {
    private String product_name;
    private String GPU__Memory_Type;
    private String GPU_Chipset;
    private String product_price;
    private String product_image;

    public GpuModel(String product_name, String GPU__Memory_Type, String GPU_Chipset, String product_price, String product_image) {
        this.product_name = product_name;
        this.GPU__Memory_Type = GPU__Memory_Type;
        this.GPU_Chipset = GPU_Chipset;
        this.product_price = product_price;
        this.product_image = product_image;
    }
    public GpuModel () {
        // Default constructor required for Firebase Firestore deserialization
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getGPU__Memory_Type() {
        return GPU__Memory_Type;
    }

    public String getGPU_Chipset() {
        return GPU_Chipset;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getProduct_image() {
        return product_image;
    }
}