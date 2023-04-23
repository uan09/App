package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Tech_RegisterActivity extends AppCompatActivity {

    TextView login_tech;
    Button loginbutton_tech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_tech_register);

        login_tech = findViewById(R.id.tologinastech);
        login_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoBackActivity();
            }
        });

        loginbutton_tech = findViewById(R.id.tech_registerbutton);
        loginbutton_tech.setOnClickListener(new View.OnClickListener() {
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