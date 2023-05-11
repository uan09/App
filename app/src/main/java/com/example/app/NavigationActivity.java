package com.example.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.app.ui.fragments.CartFragment;
import com.example.app.ui.fragments.DashboardFragment;
import com.example.app.ui.fragments.ManagerFragment;
import com.example.app.ui.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class NavigationActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    DashboardFragment dashboardFragment = new DashboardFragment();
    ManagerFragment managerFragment = new ManagerFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    CartFragment cartFragment = new CartFragment();
    TextView emailTextView;
    String email;

    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_navigation);

        emailTextView = findViewById(R.id.emailTextView);
        Intent intentReceived = getIntent();
        Bundle extras = intentReceived.getExtras();
        if (extras != null) {
            email = extras.getString("Value");
            emailTextView.setText(email);
        }

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new DashboardFragment()).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId()) {
                case R.id.dashboard:
                    DashboardFragment fragment2 = new DashboardFragment();
                    Bundle args2 = new Bundle();
                    args2.putString("Email", email);
                    fragment2.setArguments(args2);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,dashboardFragment).commit();

                    FragmentManager fragmentManager2 = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                    fragmentTransaction2.add(R.id.frame_layout, fragment2);
                    fragmentTransaction2.addToBackStack(null);
                    fragmentTransaction2.commit();
                    return true;

                case R.id.userprofile:

                    ProfileFragment fragment = new ProfileFragment();
                    Bundle args = new Bundle();
                    args.putString("Email", email);
                    fragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,profileFragment).commit();

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.frame_layout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    return true;

                case R.id.cart:

                    CartFragment fragment1 = new CartFragment();
                    Bundle args1 = new Bundle();
                    args1.putString("Email", email);
                    fragment1.setArguments(args1);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,cartFragment).commit();

                    FragmentManager fragmentManager1 = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                    fragmentTransaction1.add(R.id.frame_layout, fragment1);
                    fragmentTransaction1.addToBackStack(null);
                    fragmentTransaction1.commit();
                    return true;

                case R.id.manage:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,managerFragment).commit();
                    return true;
            }

            return false;
        });
    }
}