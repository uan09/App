package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.app.ui.fragments.ManagerFragment;

public class TermsOfServiceActivity extends AppCompatActivity {

    ImageView backbutton13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_terms_of_service);

        backbutton13 = findViewById(R.id.backbutton13);
        backbutton13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openManagerFragment(); }
        });
    }

    public void openManagerFragment() {
        Intent intent = new Intent(this, ManagerFragment.class);
        startActivity(intent);
    }
}