package com.example.app.ui.fragments;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.example.app.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class tech_NearMe_fragment extends Fragment implements OnMapReadyCallback {

    //todo: this fragment will display pinned technician on a map 👎 away from location
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int DEFAULT_ZOOM = 15;
    private GoogleMap mMap;
    private MapView mMapView;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference techLocationRef = firebaseFirestore.collection("TechnicianAccounts");

    public tech_NearMe_fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tech_nearme, container, false);

        mMapView = rootView.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void moveCameraToLocation(Location location) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Check if the user has granted location permission
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Enable the location layer on the map
            mMap.setMyLocationEnabled(true);
            // Get the user's current location
            FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(getActivity());
            locationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null){
                        // Move the camera to the user's current location
                        moveCameraToLocation(location);
                        LatLng userLoc = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(userLoc).title("Your location"))
                                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_location_on_24));

                        techLocationRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    LatLng techLocationLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                                    GeoPoint geoPoint = documentSnapshot.getGeoPoint("geoLocation");
                                    String techName = documentSnapshot.getString("tech_name");

                                    if (geoPoint != null && techName != null) {
                                        double latitude = geoPoint.getLatitude();
                                        double longitude = geoPoint.getLongitude();
                                        LatLng latLng = new LatLng(latitude, longitude);

                                        Location userLocation = new Location("");
                                        userLocation.setLatitude(geoPoint.getLatitude());
                                        userLocation.setLongitude(geoPoint.getLongitude());

                                        float[] distance = new float[1];
                                        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                                                userLocation.getLatitude(), userLocation.getLongitude(),
                                                distance);
                                        // convert the distance to kilometers
                                        float distanceInKm = distance[0] / 1000;
                                        int km = Math.round(distanceInKm);
                                        //"km" as the calculated distance between the current user's location and the technician
                                        if (km <= 3) {
                                            mMap.addMarker(new MarkerOptions().position(latLng).title(techName)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_emoji_people_24));
                                        }
                                    } else {
                                        Log.d(TAG, "geoPoint or techName is null");
                                    }
                                }

                            }

                        });
                    }
                }
            });
        }else {
            // Request location permission
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
}