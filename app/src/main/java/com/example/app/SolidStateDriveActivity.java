package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.SsdAdapter;
import com.example.app.ui.models.SsdModel;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SolidStateDriveActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private ArrayList<SsdModel> ssdModels;
    private SsdAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solid_state_drive);

        String email = getIntent().getStringExtra("email");
        if (email != null) {
            Toast.makeText(SolidStateDriveActivity.this, "mail" + email, Toast.LENGTH_SHORT).show();
        }

        ImageView menuBack = findViewById(R.id.menu_back);
        menuBack.setOnClickListener(view -> {

            Intent intent = new Intent(this, NewBuildActivity.class);
            intent.putExtra("Email", email); // Pass the email value to NewBuildActivity
            startActivity(intent);

        });

        firestore = FirebaseFirestore.getInstance();
        ssdModels = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.ssd_recyclerview_view);
        adapter = new SsdAdapter(this, ssdModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Read the Temp document within the TempItems collection
        firestore.collection("TempItems")
                .document("Temp")
                .get()
                .addOnSuccessListener(tempDocumentSnapshot -> {
                    if (tempDocumentSnapshot.exists()) {
                        String motherboardM2Port = tempDocumentSnapshot.getString("Motherboard_M2_Port");

                        // Get all documents in the GPU_DB collection with GPU_Memory_Type equal to motherboardMemoryType
                        firestore.collection("SSD_DB")
                                .whereEqualTo("SSD_Interface", motherboardM2Port)
                                .get()
                                .addOnSuccessListener(ssdDocumentSnapshots -> {
                                    List<Task<QuerySnapshot>> tasks = new ArrayList<>();
                                    for (DocumentSnapshot ssdDocumentSnapshot : ssdDocumentSnapshots) {
                                        String gpuId = ssdDocumentSnapshot.getId();

                                        Task<QuerySnapshot> task = firestore.collection("Products")
                                                .whereEqualTo("product_name", gpuId)
                                                .whereEqualTo("product_type", "Solid State Drive")
                                                .get();
                                        tasks.add(task);
                                    }

                                    // Wait for all the queries to complete
                                    Task<List<QuerySnapshot>> combinedTask = Tasks.whenAllSuccess(tasks);
                                    combinedTask.addOnSuccessListener(querySnapshotsList -> {
                                        ssdModels.clear(); // Clear the previous data
                                        for (QuerySnapshot querySnapshot : querySnapshotsList) {
                                            if (!querySnapshot.isEmpty()) {
                                                for (DocumentSnapshot productDocumentSnapshot : querySnapshot.getDocuments()) {
                                                    String productName = productDocumentSnapshot.getString("product_name");

                                                    // Retrieve GPU_Memory_Type and GPU_Chipset
                                                    DocumentSnapshot ssdDocumentSnapshot = ssdDocumentSnapshots.getDocuments().stream()
                                                            .filter(doc -> doc.getId().equals(productName))
                                                            .findFirst()
                                                            .orElse(null);
                                                    if (ssdDocumentSnapshot != null) {
                                                        String ssdCapacity = ssdDocumentSnapshot.getString("SSD_Capacity");
                                                        String ssdInterface = ssdDocumentSnapshot.getString("SSD_Interface");

                                                        String productPrice = productDocumentSnapshot.getString("product_price");
                                                        List<String> productImages = (List<String>) productDocumentSnapshot.get("product_image");
                                                        String productImage = null;
                                                        if (productImages != null && !productImages.isEmpty()) {
                                                            productImage = productImages.get(0);
                                                        }

                                                        SsdModel ssdModel = new SsdModel(productName, ssdInterface, ssdCapacity, productPrice, productImage);
                                                        ssdModels.add(ssdModel);
                                                    }
                                                }
                                            }
                                        }
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