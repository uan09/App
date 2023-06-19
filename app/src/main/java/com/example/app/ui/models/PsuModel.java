package com.example.app.ui.models;

public class PsuModel {
    private String product_name;
    private String PSU_Form_Factor;
    private String PSU_Wattage;
    private String product_price;
    private String product_image;

    public PsuModel(String product_name, String PSU_Form_Factor, String PSU_Wattage, String product_price, String product_image) {
        this.product_name = product_name;
        this.PSU_Form_Factor = PSU_Form_Factor;
        this.PSU_Wattage = PSU_Wattage;
        this.product_price = product_price;
        this.product_image = product_image;
    }

    public PsuModel() {
        // Default constructor required for Firebase Firestore deserialization
    }
    public String getProduct_name() {
        return product_name;
    }

    public String getPSU_Form_Factor() {
        return PSU_Form_Factor;
    }

    public String getPSU_Wattage() {
        return PSU_Wattage;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getProduct_image() {
        return product_image;
    }
}
