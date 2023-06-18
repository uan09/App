package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.RamAdapter;
import com.example.app.ui.models.RamModel;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RandomAccessMemoryActivity extends AppCompatActivity {

    private ArrayList<RamModel> ramModels = new ArrayList<>();
    private RamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_access_memory);

        String email = getIntent().getStringExtra("email");
        if (email != null) {
            Toast.makeText(RandomAccessMemoryActivity.this, "mail" + email, Toast.LENGTH_SHORT).show();
        }

        ImageView menuBack = findViewById(R.id.menu_back);
        menuBack.setOnClickListener(view -> {

            Intent intent = new Intent(this, NewBuildActivity.class);
            intent.putExtra("Email", email); // Pass the email value to NewBuildActivity
            startActivity(intent);

        });

        RecyclerView recyclerView = findViewById(R.id.ram_recyclerview_view);

        adapter = new RamAdapter(this, ramModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchRAMData();
    }

    private void fetchRAMData() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // Read the Temp document within the TempItems collection to get the Motherboard_Memory_Type
        firestore.collection("TempItems")
                .document("Temp")
                .get()
                .addOnSuccessListener(tempDocumentSnapshot -> {
                    if (tempDocumentSnapshot.exists()) {
                        String motherboardMemoryType = tempDocumentSnapshot.getString("Motherboard_Memory_Type");

                        // Get all documents in the GPU_DB collection with GPU_Memory_Type equal to motherboardMemoryType
                        firestore.collection("RAM_DB")
                                .whereEqualTo("Memory_Type", motherboardMemoryType)
                                .get()
                                .addOnSuccessListener(gpuDocumentSnapshots -> {
                                    List<Task<QuerySnapshot>> tasks = new ArrayList<>();
                                    for (DocumentSnapshot ramDocumentSnapshot : gpuDocumentSnapshots) {
                                        String ramId = ramDocumentSnapshot.getId();

                                        Task<QuerySnapshot> task = firestore.collection("Products")
                                                .whereEqualTo("product_name", ramId)
                                                .whereEqualTo("product_type", "Random Access Memory")
                                                .get();
                                        tasks.add(task);
                                    }

                                    // Wait for all the queries to complete
                                    Task<List<QuerySnapshot>> combinedTask = Tasks.whenAllSuccess(tasks);
                                    combinedTask.addOnSuccessListener(querySnapshotsList -> {
                                        ramModels.clear(); // Clear the previous data
                                        for (QuerySnapshot querySnapshot : querySnapshotsList) {
                                            if (!querySnapshot.isEmpty()) {
                                                for (DocumentSnapshot productDocumentSnapshot : querySnapshot.getDocuments()) {
                                                    String memoryName = productDocumentSnapshot.getString("product_name");

                                                    // Retrieve GPU_Memory_Type and GPU_Chipset
                                                    DocumentSnapshot gpuDocumentSnapshot = gpuDocumentSnapshots.getDocuments().stream()
                                                            .filter(doc -> doc.getId().equals(memoryName))
                                                            .findFirst()
                                                            .orElse(null);
                                                    if (gpuDocumentSnapshot != null) {
                                                        String memoryType = gpuDocumentSnapshot.getString("Memory_Type");
                                                        String memoryCapacity = gpuDocumentSnapshot.getString("Memory_Capacity");

                                                        String productPrice = productDocumentSnapshot.getString("product_price");
                                                        List<String> productImages = (List<String>) productDocumentSnapshot.get("product_image");
                                                        String productImage = null;
                                                        if (productImages != null && !productImages.isEmpty()) {
                                                            productImage = productImages.get(0);
                                                        }

                                                        RamModel ramModel = new RamModel( memoryName, memoryType, memoryCapacity, productImage, productPrice);
                                                        ramModels.add(ramModel);
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