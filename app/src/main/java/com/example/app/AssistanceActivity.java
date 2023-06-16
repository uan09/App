package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.app.ui.fragments.tech_NearMe_fragment;
import com.example.app.ui.fragments.DashboardFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AssistanceActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText input_assitance, input_description;
    Button showMap, showNearest;

    tech_NearMe_fragment tech_Fragment = new tech_NearMe_fragment();

    ImageView backbutton6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_assistance);

        input_assitance = (EditText) findViewById(R.id.input_assistance);
        input_description = (EditText) findViewById(R.id.input_description);

        backbutton6 = findViewById(R.id.backbutton6);
        backbutton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_dashboard, new DashboardFragment()).commit();
            }
        });

        showMap = (Button) findViewById(R.id.show_in_map_button);
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String assistance = input_assitance.getText().toString();
                String description = input_description.getText().toString();

                if (assistance.trim().isEmpty()) {
                    input_assitance.setError("Required");
                    input_assitance.requestFocus();
                    return;
                }
                if (description.trim().isEmpty()) {
                    input_description.setError("Required");
                    input_description.requestFocus();
                    return;
                }

                needAssistance(assistance, description);

                String uri = "http://maps.google.com/maps?q=" + assistance;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        showNearest = findViewById(R.id.show_nearest_button);
        showNearest.setOnClickListener(new View.OnClickListener() {
            final String technician = "Computer Technician";
            final int radius = 1000; // Radius in meters (adjust as needed)

            @Override
            public void onClick(View view) {
                // construct the Google Maps URI to search for nearby places
                String uri = "http://maps.google.com/maps/search/" + technician + "/nearby?location=" + getCurrentLocation() + "&radius=" + radius + "&rankby=distance";

                // create an intent to launch Google Maps with the constructed URI
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");

                // start the activity to open Google Maps and display the search results
                startActivity(intent);
            }

            private String getCurrentLocation() {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                String latitude = "";
                String longitude = "";

                if (ActivityCompat.checkSelfPermission(AssistanceActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // get the last known location from the GPS provider
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null) {
                        // retrieve the latitude and longitude values from the location object
                        latitude = String.valueOf(location.getLatitude());
                        longitude = String.valueOf(location.getLongitude());
                    }
                }

                // Return the current location as a string in the format "latitude,longitude"
                return latitude + "," + longitude;
            }
        });
    }

    private void needAssistance(String assistance, String description) {
        Map<String, Object> assistanceData = new HashMap<>();
        assistanceData.put("email", "");
        assistanceData.put("assistance", assistance);
        assistanceData.put("description", description);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference assistanceRef = db.collection("RequestAssistance").document();
        assistanceRef.set(assistanceData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot written with ID: ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }
}