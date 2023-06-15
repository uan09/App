package com.example.app.ui.models;

import java.util.ArrayList;
import java.util.Map;

public class ItemModel {
    private String product_name, product_type, product_description, product_brand, product_category, product_price, Product_status, product_quantity, Product_id, store_name;
    private ArrayList<String> Product_image;
    private String CPU_Name, CPU_Socket, CPU_Core_Clock, CPU_Cores;
    private String Motherboard_Name, Motherboard_Socket, Motherboard_Form_Factor, Motherboard_PCIe_Slot, Motherboard_SATA_Port, Motherboard_M2_Port, Motherboard_Memory_Type;

    public ItemModel(String product_name, String product_type, String product_description, String product_brand, String categoryText, String product_price, String statusText, String product_quantity, ArrayList<String> urlsList) {
    }

    public ItemModel(String store_name, String product_name, String product_type, String product_description, String product_brand, String product_category, String product_price, String product_status, String product_quantity, String product_id, ArrayList<String> product_image) {
        this.store_name = store_name;
        this.product_name = product_name;
        this.product_type = product_type;
        this.product_description = product_description;
        this.product_brand = product_brand;
        this.product_category = product_category;
        this.product_price = product_price;
        this.Product_status = product_status;
        this.product_quantity = product_quantity;
        Product_id = product_id;
        Product_image = product_image;
    }

    // Add a method to add all data fields from a Map to the ItemModel object
    public void addAllDataFields(Map<String, Object> data) {
        // Set the common fields of ItemModel
        setCommonFields(data);

        // Check the product type and call the corresponding method to add the fields
        String productType = (String) data.get("product_type");
        if (productType != null) {
            switch (productType) {
                case "processor":
                    processorAddAllDataFields(data);
                    break;
                case "motherboard":
                    motherboardAddAllDataFields(data);
                    break;
                // Add cases for other product types as needed
            }
        }
    }

    private void setCommonFields(Map<String, Object> data) {
        // Set the common fields of ItemModel
        if (data.containsKey("product_name")) {
            setProduct_name((String) data.get("product_name"));
        }
        if (data.containsKey("product_type")) {
            setProduct_type((String) data.get("product_type"));
        }
        if (data.containsKey("product_description")) {
            setProduct_description((String) data.get("product_description"));
        }
        if (data.containsKey("product_brand")) {
            setProduct_brand((String) data.get("product_brand"));
        }
        if (data.containsKey("product_category")) {
            setProduct_category((String) data.get("product_category"));
        }
        if (data.containsKey("product_price")) {
            setProduct_price((String) data.get("product_price"));
        }
        if (data.containsKey("product_status")) {
            setProduct_status((String) data.get("product_status"));
        }
        if (data.containsKey("product_quantity")) {
            setProduct_quantity((String) data.get("product_quantity"));
        }
        if (data.containsKey("Product_id")) {
            setProduct_id((String) data.get("Product_id"));
        }
        if (data.containsKey("Product_image")) {
            setProduct_image((ArrayList<String>) data.get("Product_image"));
        }
    }

    private void processorAddAllDataFields(Map<String, Object> data) {
        if (data.containsKey("CPU_Name")) {
            setCPU_Name((String) data.get("CPU_Name"));
        }
        if (data.containsKey("CPU_Socket")) {
            setCPU_Socket((String) data.get("CPU_Socket"));
        }
        if (data.containsKey("CPU_Core_Clock")) {
            setCPU_Core_Clock((String) data.get("CPU_Core_Clock"));
        }
        if (data.containsKey("CPU_Cores")) {
            setCPU_Cores((String) data.get("CPU_Cores"));
        }
        // Add other fields of the Processor model class here
    }

    private void motherboardAddAllDataFields(Map<String, Object> data) {
        if (data.containsKey("Motherboard_Name")) {
            setMotherboard_Name((String) data.get("Motherboard_Name"));
        }
        if (data.containsKey("Motherboard_Socket")) {
            setMotherboard_Socket((String) data.get("Motherboard_Socket"));
        }
        if (data.containsKey("Motherboard_Form_Factor")) {
            setMotherboard_Form_Factor((String) data.get("Motherboard_Form_Factor"));
        }
        if (data.containsKey("Motherboard_PCIe_Slot")) {
            setMotherboard_PCIe_Slot((String) data.get("Motherboard_PCIe_Slot"));
        }
        if (data.containsKey("Motherboard_SATA_Port")) {
            setMotherboard_SATA_Port((String) data.get("Motherboard_SATA_Port"));
        }
        if (data.containsKey("Motherboard_M2_Port")) {
            setMotherboard_M2_Port((String) data.get("Motherboard_M2_Port"));
        }
        if (data.containsKey("Motherboard_Memory_Type")) {
            setMotherboard_Memory_Type((String) data.get("Motherboard_Memory_Type"));
        }
        // Add other fields of the Motherboard model class here
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }
    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_brand() {
        return product_brand;
    }

    public void setProduct_brand(String product_brand) {
        this.product_brand = product_brand;
    }
    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_status() {
        return Product_status;
    }

    public void setProduct_status(String product_status) {
        this.Product_status = product_status;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_id() {
        return Product_id;
    }

    public void setProduct_id(String product_id) {
        Product_id = product_id;
    }

    public ArrayList<String> getProduct_image() {
        return Product_image;
    }

    public void setProduct_image(ArrayList<String> product_image) {
        Product_image = product_image;
    }

    public String getCPU_Name() {
        return CPU_Name;
    }

    public void setCPU_Name(String cpu_Name) {
        this.CPU_Name = cpu_Name;
    }

    public String getCPU_Socket() {
        return CPU_Socket;
    }

    public void setCPU_Socket(String cpu_Socket) {
        this.CPU_Socket = cpu_Socket;
    }

    public String getCPU_Core_Clock() {
        return CPU_Core_Clock;
    }

    public void setCPU_Core_Clock(String cpu_Core_Clock) {
        this.CPU_Core_Clock = cpu_Core_Clock;
    }

    public String getCPU_Cores() {
        return CPU_Cores;
    }

    public void setCPU_Cores(String cpu_Cores) {
        this.CPU_Cores = cpu_Cores;
    }


    // Getter and Setter methods for Motherboard fields
    public String getMotherboard_Name() {
        return Motherboard_Name;
    }

    public void setMotherboard_Name(String motherboard_Name) {
        this.Motherboard_Name = motherboard_Name;
    }

    public String getMotherboard_Socket() {
        return Motherboard_Socket;
    }

    public void setMotherboard_Socket(String motherboard_Socket) {
        this.Motherboard_Socket = motherboard_Socket;
    }

    public String getMotherboard_Form_Factor() {
        return Motherboard_Form_Factor;
    }

    public void setMotherboard_Form_Factor(String motherboard_Form_Factor) {
        this.Motherboard_Form_Factor = motherboard_Form_Factor;
    }

    public String getMotherboard_PCIe_Slot() {
        return Motherboard_PCIe_Slot;
    }

    public void setMotherboard_PCIe_Slot(String motherboard_PCIe_Slot) {
        this.Motherboard_PCIe_Slot = motherboard_PCIe_Slot;
    }

    public String getMotherboard_SATA_Port() {
        return Motherboard_SATA_Port;
    }

    public void setMotherboard_SATA_Port(String motherboard_SATA_Port) {
        this.Motherboard_SATA_Port = motherboard_SATA_Port;
    }

    public String getMotherboard_M2_Port() {
        return Motherboard_M2_Port;
    }

    public void setMotherboard_M2_Port(String motherboard_M2_Port) {
        this.Motherboard_M2_Port = motherboard_M2_Port;
    }

    public String getMotherboard_Memory_Type() {
        return Motherboard_Memory_Type;
    }

    public void setMotherboard_Memory_Type(String motherboard_Memory_Type) {
        this.Motherboard_Memory_Type = motherboard_Memory_Type;
    }
}
