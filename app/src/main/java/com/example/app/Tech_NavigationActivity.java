package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.app.ui.fragments.Tech_DashboardFragment;
import com.example.app.ui.fragments.ProfileFragment;
import com.example.app.ui.fragments.Tech_ManagerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Tech_NavigationActivity extends AppCompatActivity {

    BottomNavigationView tech_bottomNavigation;

    Tech_DashboardFragment tech_dashboardFragment = new Tech_DashboardFragment();
    Tech_ManagerFragment tech_managerFragment = new Tech_ManagerFragment();
    ProfileFragment tech_profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_tech_navigation);

        tech_bottomNavigation = findViewById(R.id.tech_bottomNavigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.techframe_layout, new Tech_DashboardFragment()).commit();

        tech_bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {
                    case R.id.dashboard_tech:
                        getSupportFragmentManager().beginTransaction().replace(R.id.techframe_layout,tech_dashboardFragment).commit();
                        return true;

                    case R.id.userprofile_tech:
                        getSupportFragmentManager().beginTransaction().replace(R.id.techframe_layout,tech_profileFragment).commit();
                        return true;

                    case R.id.manage_tech:
                        getSupportFragmentManager().beginTransaction().replace(R.id.techframe_layout,tech_managerFragment).commit();
                        return true;
                }

                return false;
            }
        });
    }
}