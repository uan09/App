    package com.example.app;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.MenuItem;
    import android.view.Window;
    import android.view.WindowManager;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.fragment.app.FragmentManager;
    import androidx.fragment.app.FragmentTransaction;

    import com.example.app.ui.fragments.ProfileFragment;
    import com.example.app.ui.fragments.Retail_DashboardFragment;
    import com.example.app.ui.fragments.Retail_ManagerFragment;
    import com.google.android.material.bottomnavigation.BottomNavigationView;
    import com.google.android.material.navigation.NavigationBarView;

    public class Retail_NavigationActivity extends AppCompatActivity {

        BottomNavigationView retail_bottomNavigation;

        Retail_DashboardFragment retail_dashboardFragment = new Retail_DashboardFragment();
        Retail_ManagerFragment retail_managerFragment = new Retail_ManagerFragment();
        ProfileFragment retail_profileFragment = new ProfileFragment();
        String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_retail_navigation);

        Intent intentReceived = getIntent();
        Bundle extras = intentReceived.getExtras();
        if (extras != null) {
            email = extras.getString("Value");
        }
        retail_bottomNavigation = findViewById(R.id.retail_bottomNavigation);

        Retail_DashboardFragment fragment = new Retail_DashboardFragment();
        Bundle args = new Bundle();
        args.putString("Email", email);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.retailframe_layout, new Retail_DashboardFragment()).commit();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.retailframe_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        retail_bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {
                    case R.id.dashboard_retail:
                        getSupportFragmentManager().beginTransaction().replace(R.id.retailframe_layout,retail_dashboardFragment).commit();
                        return true;

                    case R.id.userprofile_retail:
                        getSupportFragmentManager().beginTransaction().replace(R.id.retailframe_layout,retail_profileFragment).commit();
                        return true;

                    case R.id.manage_retail:
                        getSupportFragmentManager().beginTransaction().replace(R.id.retailframe_layout,retail_managerFragment).commit();
                        return true;
                }

                return false;
            }
        });
    }
}