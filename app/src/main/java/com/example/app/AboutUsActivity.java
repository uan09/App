package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.app.ui.fragments.ManagerFragment;

public class AboutUsActivity extends AppCompatActivity {

    ImageView backbutton14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_about_us);

        backbutton14 = findViewById(R.id.backbutton14);
        backbutton14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openManagerFragment(); }
        });
    }

    public void openManagerFragment() {
        Intent intent = new Intent(this, ManagerFragment.class);
        startActivity(intent);
    }
}