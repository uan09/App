package com.example.app.ui.models;

public class CpuModel {
    private String product_name;
    private String product_image;
    private String product_price;
    private String CPU_Cores;
    private String CPU_Socket;

    public CpuModel(String product_name, String product_image, String product_price, String CPU_Cores, String CPU_Socket) {
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_price = product_price;
        this.CPU_Cores = CPU_Cores;
        this.CPU_Socket = CPU_Socket;
    }

    public String getProduct_name() {
        return product_name;
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

    public String getCpu_Cores() {
        return CPU_Cores;
    }

    public String getCpu_Socket() {
        return CPU_Socket;
    }
}
