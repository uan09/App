package com.example.app.ui.models;

import org.checkerframework.common.aliasing.qual.Unique;

public class UserModel {
    public String first_name = "";
    public String last_name = "";
    public String gender = "";
    public String email = "";
    public String password = "";
    public String phone_number = "";
    public String address = "";

    @Unique
    public String user_id = "";
}
