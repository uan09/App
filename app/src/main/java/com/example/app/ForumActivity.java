package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.app.ui.fragments.DashboardFragment;

public class ForumActivity extends AppCompatActivity {

    ImageView backbutton19;
    ImageButton add_button;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_forum);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("Email")) {
            email = bundle.getString("Email");
        }
        Toast.makeText(ForumActivity.this, "Email"+email, Toast.LENGTH_SHORT).show();
        backbutton19 = findViewById(R.id.backbutton19);
        backbutton19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_dashboard, new DashboardFragment()).commit();
            }
        });

        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) { openNewForumActivity(); }
        });
    }

    public void openNewForumActivity() {
        Intent intent = new Intent(this, NewForumActivity.class);
        startActivity(intent);
    }
}