package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Retail_RegisterActivity extends AppCompatActivity {

    TextView login_retail;
    Button loginbutton_retail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_retail_register);

        login_retail = findViewById(R.id.tologinasretail);
        login_retail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoBackActivity();
            }
        });

        loginbutton_retail = findViewById(R.id.retail_registerbutton);
        loginbutton_retail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });
    }

    public void openGoBackActivity() {
        Intent intent = new Intent(this, Tech_LoginActivity.class);
        startActivity(intent);
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, Tech_LoginActivity.class);
        startActivity(intent);
    }
}