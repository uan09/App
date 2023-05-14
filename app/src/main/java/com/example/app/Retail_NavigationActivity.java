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
            email = extras.getString("storeGet");
        }

        retail_bottomNavigation = findViewById(R.id.retail_bottomNavigation);

        Retail_DashboardFragment fragment = new Retail_DashboardFragment();
        Bundle args = new Bundle();
        args.putString("storeName", email);
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
                        Retail_DashboardFragment fragment1 = new Retail_DashboardFragment();
                        Bundle args1 = new Bundle();
                        args1.putString("storeName", email);
                        fragment1.setArguments(args1);
                        getSupportFragmentManager().beginTransaction().replace(R.id.retailframe_layout,retail_dashboardFragment).commit();

                        FragmentManager fragmentManager1 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.add(R.id.retailframe_layout, fragment1);
                        fragmentTransaction1.addToBackStack(null);
                        fragmentTransaction1.commit();
                        return true;

                    case R.id.userprofile_retail:
                        ProfileFragment fragment2 = new ProfileFragment();
                        Bundle args2 = new Bundle();
                        args2.putString("storeName", email);
                        fragment2.setArguments(args2);
                        getSupportFragmentManager().beginTransaction().replace(R.id.retailframe_layout,retail_profileFragment).commit();

                        FragmentManager fragmentManager2 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        fragmentTransaction2.add(R.id.retailframe_layout, fragment2);
                        fragmentTransaction2.addToBackStack(null);
                        fragmentTransaction2.commit();
                        return true;

                    case R.id.manage_retail:
                        Retail_ManagerFragment fragment3 = new Retail_ManagerFragment();
                        Bundle args3 = new Bundle();
                        args3.putString("storeName", email);
                        fragment3.setArguments(args3);
                        getSupportFragmentManager().beginTransaction().replace(R.id.retailframe_layout,retail_managerFragment).commit();

                        FragmentManager fragmentManager3 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                        fragmentTransaction3.add(R.id.retailframe_layout, fragment3);
                        fragmentTransaction3.addToBackStack(null);
                        fragmentTransaction3.commit();
                        return true;
                }

                return false;
            }
        });
    }
}