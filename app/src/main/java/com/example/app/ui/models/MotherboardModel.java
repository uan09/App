package com.example.app.ui.models;

import java.util.Collections;

public class MotherboardModel {
    private String Motherboard_Name;
    private String Motherboard_Socket;
    private String Motherboard_Form_Factor;
    private String Motherboard_Memory_Type;
    private String product_name;
    private String product_price;
    private String product_image;

    public MotherboardModel(String product_name, String Motherboard_Socket, String Motherboard_Form_Factor, String Motherboard_Memory_Type, String product_price, String product_image) {
        this.product_name = product_name;
        this.Motherboard_Socket = Motherboard_Socket;
        this.Motherboard_Form_Factor = Motherboard_Form_Factor;
        this.Motherboard_Memory_Type = Motherboard_Memory_Type;
        this.product_price = product_price;
        this.product_image = product_image;
    }

    public MotherboardModel() {
        // Default constructor required for Firebase Firestore deserialization
    }

    public String getMotherboard_Name() {
        return Motherboard_Name;
    }

    public String getMotherboard_Socket() {
        return Motherboard_Socket;
    }

    public String getMotherboard_Form_Factor() {
        return Motherboard_Form_Factor;
    }

    public String getMotherboard_Memory_Type() {
        return Motherboard_Memory_Type;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getProduct_image() {
        return product_image;
    }


}
