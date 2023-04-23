package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.app.ui.fragments.Tech_DashboardFragment;

public class Tech_RequestsActivity extends AppCompatActivity {

    ImageView backbutton;
    Button request_pendingbutton, request_finishbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_tech_requests);

        request_pendingbutton = findViewById(R.id.pending_request_button);
        request_pendingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openPendingRequestsActivity(); }
        });

        request_finishbutton = findViewById(R.id.history_request_button);
        request_finishbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistoryRequestsActivity();
            }
        });

        backbutton = findViewById(R.id.backbutton7);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.techmain_dashboard, new Tech_DashboardFragment()).commit();
            }
        });
    }

    public void openPendingRequestsActivity() {
        Intent intent = new Intent(this, Tech_PendingRequestsActivity.class);
        startActivity(intent);
    }

    public void openHistoryRequestsActivity() {
        Intent intent = new Intent(this, Tech_HistoryRequestsActivity.class);
        startActivity(intent);
    }
}