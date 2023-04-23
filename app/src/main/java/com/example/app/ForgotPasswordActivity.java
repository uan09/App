package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class ForgotPasswordActivity extends AppCompatActivity {

    ImageView backbutton2;
    Button sms_button, email_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_forgot_password);

        backbutton2 = findViewById(R.id.backbutton2);
        backbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openLoginActivity(); }
        });

        sms_button = findViewById(R.id.sms_button);
        sms_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openViaSmsActivity(); }
        });

        email_button = findViewById(R.id.email_button);
        email_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openViaEmailActivity(); }
        });
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openViaSmsActivity() {
        Intent intent = new Intent(this, ViaSms.class);
        startActivity(intent);
    }

    public void openViaEmailActivity() {
        Intent intent = new Intent(this, ViaEmailActivity.class);
        startActivity(intent);
    }
}