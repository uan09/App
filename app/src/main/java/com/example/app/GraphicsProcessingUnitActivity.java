package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app.ui.adapters.GpuAdapter;
import com.example.app.ui.models.GpuModel;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GraphicsProcessingUnitActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private ArrayList<GpuModel> gpuModels;
    private GpuAdapter adapter;

    ImageView menu_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_processing_unit);

        String email = getIntent().getStringExtra("email");
        if (email != null) {
            Toast.makeText(GraphicsProcessingUnitActivity.this, "mail" + email, Toast.LENGTH_SHORT).show();
        }

        ImageView menuBack = findViewById(R.id.menu_back);
        menuBack.setOnClickListener(view -> {

            Intent intent = new Intent(this, NewBuildActivity.class);
            intent.putExtra("Email", email); // Pass the email value to NewBuildActivity
            startActivity(intent);

        });

        firestore = FirebaseFirestore.getInstance();
        gpuModels = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.gpu_recyclerview_view);
        adapter = new GpuAdapter(this, gpuModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Read the Temp document within the TempItems collection
        firestore.collection("TempItems")
                .document("Temp")
                .get()
                .addOnSuccessListener(tempDocumentSnapshot -> {
                    if (tempDocumentSnapshot.exists()) {
                        String motherboardMemoryType = tempDocumentSnapshot.getString("Motherboard_Memory_Type");

                        // Get all documents in the GPU_DB collection with GPU_Memory_Type equal to motherboardMemoryType
                        firestore.collection("GPU_DB")
                                .whereEqualTo("GPU__Memory_Type", motherboardMemoryType)
                                .get()
                                .addOnSuccessListener(gpuDocumentSnapshots -> {
                                    List<Task<QuerySnapshot>> tasks = new ArrayList<>();
                                    for (DocumentSnapshot gpuDocumentSnapshot : gpuDocumentSnapshots) {
                                        String gpuId = gpuDocumentSnapshot.getId();

                                        Task<QuerySnapshot> task = firestore.collection("Products")
                                                .whereEqualTo("product_name", gpuId)
                                                .whereEqualTo("product_type", "Graphics Card")
                                                .get();
                                        tasks.add(task);
                                    }

                                    // Wait for all the queries to complete
                                    Task<List<QuerySnapshot>> combinedTask = Tasks.whenAllSuccess(tasks);
                                    combinedTask.addOnSuccessListener(querySnapshotsList -> {
                                        gpuModels.clear(); // Clear the previous data
                                        for (QuerySnapshot querySnapshot : querySnapshotsList) {
                                            if (!querySnapshot.isEmpty()) {
                                                for (DocumentSnapshot productDocumentSnapshot : querySnapshot.getDocuments()) {
                                                    String productName = productDocumentSnapshot.getString("product_name");

                                                    // Retrieve GPU_Memory_Type and GPU_Chipset
                                                    DocumentSnapshot gpuDocumentSnapshot = gpuDocumentSnapshots.getDocuments().stream()
                                                            .filter(doc -> doc.getId().equals(productName))
                                                            .findFirst()
                                                            .orElse(null);
                                                    if (gpuDocumentSnapshot != null) {
                                                        String gpuMemoryType = gpuDocumentSnapshot.getString("GPU__Memory_Type");
                                                        String gpuChipset = gpuDocumentSnapshot.getString("GPU_Chipset");

                                                        String productPrice = productDocumentSnapshot.getString("product_price");
                                                        List<String> productImages = (List<String>) productDocumentSnapshot.get("product_image");
                                                        String productImage = null;
                                                        if (productImages != null && !productImages.isEmpty()) {
                                                            productImage = productImages.get(0);
                                                        }

                                                        GpuModel gpuModel = new GpuModel(productName, gpuMemoryType, gpuChipset, productPrice, productImage);
                                                        gpuModels.add(gpuModel);
                                                    }
                                                }
                                            }
                                        }

                                        // Sort the gpuModels based on price in ascending order
                                        Collections.sort(gpuModels, new Comparator<GpuModel>() {
                                            @Override
                                            public int compare(GpuModel gpu1, GpuModel gpu2) {
                                                double price1 = Double.parseDouble(gpu1.getProduct_price().replaceAll("[^\\d.]", ""));
                                                double price2 = Double.parseDouble(gpu2.getProduct_price().replaceAll("[^\\d.]", ""));
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
