package com.example.app.ui.models;

import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    public String order_id = "";
    public UserModel customer = new UserModel();
    public List<CartModel> cart = new ArrayList<>();
}
