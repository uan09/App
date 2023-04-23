package com.example.app.ui.models;

public class CaseModel {
    String caseProductName;
    String caseProductPrice;
    int image;

    public CaseModel(String caseProductName, String caseProductPrice, int image) {
        this.caseProductName = caseProductName;
        this.caseProductPrice = caseProductPrice;
        this.image = image;
    }

    public String getCaseProductName() {
        return caseProductName;
    }

    public String getCaseProductPrice() {
        return caseProductPrice;
    }

    public int getImage() {
        return image;
    }
}
