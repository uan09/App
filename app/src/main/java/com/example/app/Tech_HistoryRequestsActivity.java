package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class Tech_HistoryRequestsActivity extends AppCompatActivity {

    ImageView backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_tech_history_requests);

        backbutton = findViewById(R.id.backbutton16);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openRequestsActivity(); }
        });
    }

    public void openRequestsActivity() {
        Intent intent = new Intent(this, Tech_RequestsActivity.class);
        startActivity(intent);
    }
}