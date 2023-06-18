package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.CoolerAdapter;
import com.example.app.ui.models.CoolerModel;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CpuCoolerActivity extends AppCompatActivity {

    ArrayList<CoolerModel> coolerModels = new ArrayList<>();
    private FirebaseFirestore firestore;
    private CoolerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_cooler);

        String email = getIntent().getStringExtra("email");
        if (email != null) {
            Toast.makeText(CpuCoolerActivity.this, "mail" + email, Toast.LENGTH_SHORT).show();
        }

        ImageView menuBack = findViewById(R.id.menu_back);
        menuBack.setOnClickListener(view -> {

            Intent intent = new Intent(this, NewBuildActivity.class);
            intent.putExtra("Email", email); // Pass the email value to NewBuildActivity
            startActivity(intent);

        });

        firestore = FirebaseFirestore.getInstance();
        coolerModels = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.cooler_recyclerview_view);
        adapter = new CoolerAdapter(this, coolerModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firestore.collection("TempItems")
                .document("Temp")
                .get()
                .addOnSuccessListener(tempDocumentSnapshot -> {
                    if (tempDocumentSnapshot.exists()) {
                        String motherboardSocket = tempDocumentSnapshot.getString("Motherboard_Socket");

                        // Get all documents in the GPU_DB collection with GPU_Memory_Type equal to motherboardMemoryType
                        firestore.collection("CPUCoolersDB")
                                .whereIn("Cooler_Socket", Collections.singletonList(motherboardSocket))
                                .get()
                                .addOnSuccessListener(coolerDocumentSnapshots -> {
                                    List<Task<QuerySnapshot>> tasks = new ArrayList<>();
                                    for (DocumentSnapshot coolerDocumentSnapshot : coolerDocumentSnapshots) {
                                        String gpuId = coolerDocumentSnapshot.getId();

                                        Task<QuerySnapshot> task = firestore.collection("Products")
                                                .whereEqualTo("product_name", gpuId)
                                                .whereEqualTo("product_type", "CPU Cooler")
                                                .get();
                                        tasks.add(task);
                                    }

                                    // Wait for all the queries to complete
                                    Task<List<QuerySnapshot>> combinedTask = Tasks.whenAllSuccess(tasks);
                                    combinedTask.addOnSuccessListener(querySnapshotsList -> {
                                        coolerModels.clear(); // Clear the previous data
                                        for (QuerySnapshot querySnapshot : querySnapshotsList) {
                                            if (!querySnapshot.isEmpty()) {
                                                for (DocumentSnapshot productDocumentSnapshot : querySnapshot.getDocuments()) {
                                                    String productName = productDocumentSnapshot.getString("product_name");

                                                    // Retrieve GPU_Memory_Type and GPU_Chipset
                                                    DocumentSnapshot coolerDocumentSnapshot = coolerDocumentSnapshots.getDocuments().stream()
                                                            .filter(doc -> doc.getId().equals(productName))
                                                            .findFirst()
                                                            .orElse(null);
                                                    if (coolerDocumentSnapshot != null) {
                                                        String coolerSocket = coolerDocumentSnapshot.getString("Cooler_Socket");
                                                        String coolerRPM = coolerDocumentSnapshot.getString("Cooler_RPM");

                                                        String productPrice = productDocumentSnapshot.getString("product_price");
                                                        List<String> productImages = (List<String>) productDocumentSnapshot.get("product_image");
                                                        String productImage = null;
                                                        if (productImages != null && !productImages.isEmpty()) {
                                                            productImage = productImages.get(0);
                                                        }

                                                        CoolerModel coolerModel = new CoolerModel(productName, coolerSocket, coolerRPM, productPrice, productImage);
                                                        coolerModels.add(coolerModel);
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