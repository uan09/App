package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.PsuAdapter;
import com.example.app.ui.models.PsuModel;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PowerSupplyUnitActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private ArrayList<PsuModel> psuModels;
    private PsuAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_supply_unit);

        String email = getIntent().getStringExtra("email");
        if (email != null) {
            Toast.makeText(PowerSupplyUnitActivity.this, "mail" + email, Toast.LENGTH_SHORT).show();
        }

        ImageView menuBack = findViewById(R.id.menu_back);
        menuBack.setOnClickListener(view -> {

            Intent intent = new Intent(this, NewBuildActivity.class);
            intent.putExtra("Email", email); // Pass the email value to NewBuildActivity
            startActivity(intent);

        });

        firestore = FirebaseFirestore.getInstance();
        psuModels = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.psu_recyclerview_view);
        adapter = new PsuAdapter(this, psuModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firestore.collection("TempItems")
                .document("Temp")
                .get()
                .addOnSuccessListener(tempDocumentSnapshot -> {
                    if (tempDocumentSnapshot.exists()) {
                        String motherboardFormFactor = tempDocumentSnapshot.getString("Motherboard_Form_Factor");

                        // Get all documents in the GPU_DB collection with GPU_Memory_Type equal to motherboardMemoryType
                        firestore.collection("PSU_DB")
                                .whereEqualTo("PSU_Form_Factor", motherboardFormFactor)
                                .get()
                                .addOnSuccessListener(psuDocumentSnapshots -> {
                                    List<Task<QuerySnapshot>> tasks = new ArrayList<>();
                                    for (DocumentSnapshot psuDocumentSnapshot : psuDocumentSnapshots) {
                                        String psuId = psuDocumentSnapshot.getId();

                                        Task<QuerySnapshot> task = firestore.collection("Products")
                                                .whereEqualTo("product_name", psuId)
                                                .whereEqualTo("product_type", "Power Supply Unit")
                                                .get();
                                        tasks.add(task);
                                    }

                                    // Wait for all the queries to complete
                                    Task<List<QuerySnapshot>> combinedTask = Tasks.whenAllSuccess(tasks);
                                    combinedTask.addOnSuccessListener(querySnapshotsList -> {
                                        psuModels.clear(); // Clear the previous data
                                        for (QuerySnapshot querySnapshot : querySnapshotsList) {
                                            if (!querySnapshot.isEmpty()) {
                                                for (DocumentSnapshot productDocumentSnapshot : querySnapshot.getDocuments()) {
                                                    String productName = productDocumentSnapshot.getString("product_name");

                                                    // Retrieve GPU_Memory_Type and GPU_Chipset
                                                    DocumentSnapshot psuDocumentSnapshot = psuDocumentSnapshots.getDocuments().stream()
                                                            .filter(doc -> doc.getId().equals(productName))
                                                            .findFirst()
                                                            .orElse(null);
                                                    if (psuDocumentSnapshot != null) {
                                                        String psuFormFactor = psuDocumentSnapshot.getString("PSU_Form_Factor");
                                                        String psuWattage = psuDocumentSnapshot.getString("PSU_Wattage");

                                                        String productPrice = productDocumentSnapshot.getString("product_price");
                                                        List<String> productImages = (List<String>) productDocumentSnapshot.get("product_image");
                                                        String productImage = null;
                                                        if (productImages != null && !productImages.isEmpty()) {
                                                            productImage = productImages.get(0);
                                                        }

                                                        PsuModel psuModel = new PsuModel(productName, psuFormFactor, psuWattage, productPrice, productImage);
                                                        psuModels.add(psuModel);
                                                    }
                                                }
                                            }
                                        }

                                        // Sort the psuModels list based on product price in ascending order
                                        Collections.sort(psuModels, new Comparator<PsuModel>() {
                                            @Override
                                            public int compare(PsuModel psu1, PsuModel psu2) {
                                                // Parse the product prices as double values for comparison
                                                double price1 = Double.parseDouble(psu1.getProduct_price());
                                                double price2 = Double.parseDouble(psu2.getProduct_price());
                                                return Double.compare(price1, price2);
                                            }
                                        });

                                        adapter.notifyDataSetChanged();
                                        // Check if the list is empty and handle accordingly
                                    });
                                    combinedTask.addOnFailureListener(e -> {
                                        // Handle any errors that occurred while fetching products from the Products collection
                                    });
                                })
                                .addOnFailureListener(e -> {
                                    // Handle any errors that occurred while querying the GPU_DB collection
                                });
                    } else {
                        // Handle the case when the Temp document does not exist
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle any errors that occurred while reading the Temp document
                });
    }
}
