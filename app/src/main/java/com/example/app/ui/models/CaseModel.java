package com.example.app.ui.models;

public class CaseModel {
    private String productName;
    private String caseType;
    private String caseFormFactor;
    private String productPrice;
    private String productImage;

    public CaseModel(String productName, String caseType, String caseFormFactor, String productPrice, String productImage) {
        this.productName = productName;
        this.caseType = caseType;
        this.caseFormFactor = caseFormFactor;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    // Getters and setters for the model attributes
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseFormFactor() {
        return caseFormFactor;
    }

    public void setCaseFormFactor(String caseFormFactor) {
        this.caseFormFactor = caseFormFactor;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
