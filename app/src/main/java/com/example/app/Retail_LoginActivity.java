package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Retail_LoginActivity extends AppCompatActivity {

    TextView register_retail;
    Button registerbutton_retail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_retail_login);

        register_retail = findViewById(R.id.toregasretail);
        register_retail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });

        registerbutton_retail = findViewById(R.id.retail_loginbutton);
        registerbutton_retail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openNavigationActivity(); }
        });
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, Retail_RegisterActivity.class);
        startActivity(intent);
    }

    public void openNavigationActivity() {
        Intent intent = new Intent(this, Retail_ManageProductsActivity.class);
        startActivity(intent);
    }
}