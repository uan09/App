package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.app.ui.fragments.DashboardFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ForumActivity extends AppCompatActivity {

    ImageView backbutton10;
    FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_forum);

        backbutton10 = findViewById(R.id.backbutton10);
        backbutton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_dashboard, new DashboardFragment()).commit();
            }
        });

        button = findViewById(R.id.floating_add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) { openNewForumActivity(); }
        });
    }

    public void openNewForumActivity() {
        Intent intent = new Intent(this, NewForumActivity.class);
        startActivity(intent);
    }
}