package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Tech_LoginActivity extends AppCompatActivity {

    TextView register_tech;
    Button loginbutton_tech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_tech_login);

        register_tech = findViewById(R.id.toregastech);
        register_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });

        loginbutton_tech = findViewById(R.id.tech_loginbutton);
        loginbutton_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openNavigationActivity(); }
        });
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, Tech_RegisterActivity.class);
        startActivity(intent);
    }

    public void openNavigationActivity() {
        Intent intent = new Intent(this, Tech_NavigationActivity.class);
        startActivity(intent);
    }
}