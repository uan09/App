package com.example.app.ui.models;

public class HddModel {
    String hddProductName;
    String hddProductPrice;
    int image;

    public HddModel(String hddProductName, String hddProductPrice, int image) {
        this.hddProductName = hddProductName;
        this.hddProductPrice = hddProductPrice;
        this.image = image;
    }

    public String getHddProductName() {
        return hddProductName;
    }

    public String getHddProductPrice() {
        return hddProductPrice;
    }

    public int getImage() {
        return image;
    }
}
