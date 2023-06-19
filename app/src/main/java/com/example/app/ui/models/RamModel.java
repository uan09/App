package com.example.app.ui.models;

public class RamModel {
    private String product_name;
    private String Memory_Type;
    private String Memory_Capacity;
    private String product_image;
    private String product_price;

    public RamModel(String product_name, String Memory_Type, String Memory_Capacity, String product_image, String product_price) {
        this.product_name = product_name;
        this.Memory_Type = Memory_Type;
        this.Memory_Capacity = Memory_Capacity;
        this.product_image = product_image;
        this.product_price = product_price;
    }

    public RamModel() {
        // Default constructor required for Firebase Firestore deserialization
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setMemoryName(String Memory_Type) {
        this.Memory_Type = Memory_Type;
    }

    public String getMemory_Type() {
        return Memory_Type;
    }

    public void setMemoryType(String memoryType) {
        this.Memory_Type = Memory_Type;
    }

    public String getMemory_Capacity() {
        return Memory_Capacity;
    }

    public void setMemoryCapacity(String memoryCapacity) {
        this.Memory_Capacity = Memory_Capacity;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }
}