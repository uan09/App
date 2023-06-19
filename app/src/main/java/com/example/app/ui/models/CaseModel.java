package com.example.app.ui.models;

public class CaseModel {
    private String product_name;
    private String Case_Type;
    private String Case_Form_Factor;
    private String product_price;
    private String product_image;

    public CaseModel(String product_name, String Case_Type, String Case_Form_Factor, String product_price, String product_image) {
        this.product_name = product_name;
        this.Case_Type = Case_Type;
        this.Case_Form_Factor = Case_Form_Factor;
        this.product_price = product_price;
        this.product_image = product_image;
    }
    public CaseModel() {
        // Default constructor required for Firebase Firestore deserialization
    }

    // Getters and setters for the model attributes
    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCase_Type() {
        return Case_Type;
    }

    public void setCase_Type(String Case_Type) {
        this.Case_Type = Case_Type;
    }

    public String getCase_Form_Factor() {
        return Case_Form_Factor;
    }

    public void setCase_Form_Factor(String Case_Form_Factor) {
        this.Case_Form_Factor = Case_Form_Factor;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }
}
