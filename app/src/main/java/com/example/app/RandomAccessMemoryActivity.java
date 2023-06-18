package com.example.app;

import android.os.Bundle;

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

                        // Get all documents in the RAM_DB collection where Memory_Type is equal to Motherboard_Memory_Type
                        firestore.collection("RAM_DB")
                                .whereEqualTo("Memory_Type", motherboardMemoryType)
                                .get()
                                .addOnSuccessListener(ramQuerySnapshot -> {
                                    List<Task<QuerySnapshot>> tasks = new ArrayList<>();
                                    for (DocumentSnapshot ramDocument : ramQuerySnapshot.getDocuments()) {
                                        String memoryName = ramDocument.getString("Memory_Name");

                                        // Get all products from the Products collection where product_name equals RAM_DB document ID
                                        Task<QuerySnapshot> task = firestore.collection("Products")
                                                .whereEqualTo("product_name", memoryName)
                                                .whereEqualTo("product_type", "Random Access Memory")
                                                .whereEqualTo("store_name", "BitoyPc")
                                                .get()
                                                .addOnSuccessListener(productQuerySnapshot -> {
                                                    for (DocumentSnapshot productDocument : productQuerySnapshot.getDocuments()) {
                                                        String memoryType = ramDocument.getString("Memory_Type");
                                                        String memoryCapacity = ramDocument.getString("Memory_Capacity");
                                                        String productPrice = productDocument.getString("product_price");

                                                        List<String> productImages = (List<String>) productDocument.get("product_image");
                                                        String productImage = null;
                                                        if (productImages != null && !productImages.isEmpty()) {
                                                            productImage = productImages.get(0);
                                                        }

                                                        // Create RamModel instance and add it to the ArrayList
                                                        RamModel ramModel = new RamModel(memoryName, memoryType, memoryCapacity, productImage, productPrice);
                                                        ramModels.add(ramModel);
                                                    }

                                                    // Notify the adapter about the data change
                                                    adapter.notifyDataSetChanged();
                                                })
                                                .addOnFailureListener(e -> {
                                                    // Handle any errors that occurred while fetching the product documents
                                                });

                                        tasks.add(task);
                                    }

                                    // Wait for all tasks to complete before notifying the adapter
                                    Tasks.whenAllComplete(tasks)
                                            .addOnCompleteListener(task -> {
                                                // Notify the adapter about the data change
                                                adapter.notifyDataSetChanged();
                                            });
                                })
                                .addOnFailureListener(e -> {
                                    // Handle any errors that occurred while fetching the RAM_DB documents
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle any errors that occurred while fetching the Temp document
                });
    }

}