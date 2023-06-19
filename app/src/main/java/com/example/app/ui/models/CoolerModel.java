package com.example.app.ui.models;

public class CoolerModel {
    private String product_name;
    private String Cooler_Socket;
    private String product_price;
    private String product_image;
    private String Cooler_RPM;

    public CoolerModel(String product_name, String Cooler_Socket, String product_price, String product_image, String Cooler_RPM) {
        this.product_name = product_name;
        this.Cooler_Socket = Cooler_Socket;
        this.product_price = product_price;
        this.product_image = product_image;
        this.Cooler_RPM = Cooler_RPM;
    }
    public CoolerModel() {
        // Default constructor required for Firebase Firestore deserialization
    }
    public String getProduct_name() {
        return product_name;
    }

    public String getCooler_Socket() {
        return Cooler_Socket;
    }
    public String getCooler_RPM() {
        return Cooler_RPM;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getProduct_image() {
        return product_image;
    }
}
