package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class NewForumActivity extends AppCompatActivity {

    ImageView backbutton11;
    Button create_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_new_forum);

        backbutton11 = findViewById(R.id.backbutton11);

        backbutton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openForumActivity(); }
        });

        create_button = findViewById(R.id.create_button);

        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openForumActivity(); }
        });
    }

    public void openForumActivity() {
        Intent intent = new Intent(this, ForumActivity.class);
        startActivity(intent);
    }
}