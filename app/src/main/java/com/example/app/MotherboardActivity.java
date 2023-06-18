package com.example.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.MotherboardAdapter;
import com.example.app.ui.models.MotherboardModel;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MotherboardActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private ArrayList<MotherboardModel> motherboardModels;
    private MotherboardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motherboard);

        firestore = FirebaseFirestore.getInstance();
        motherboardModels = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.motherboard_recyclerview_view);
        adapter = new MotherboardAdapter(this, motherboardModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firestore.collection("TempItems")
                .document("Temp")
                .get()
                .addOnSuccessListener(tempDocumentSnapshot -> {
                    if (tempDocumentSnapshot.exists()) {
                        String cpuSocket = tempDocumentSnapshot.getString("Motherboard_Socket");

                        firestore.collection("Motherboard_DB")
                                .whereEqualTo("Motherboard_Socket", cpuSocket)
                                .get()
                                .addOnSuccessListener(queryDocumentSnapshots -> {
                                    List<Task<QuerySnapshot>> tasks = new ArrayList<>();
                                    for (DocumentSnapshot motherboardDocumentSnapshot : queryDocumentSnapshots) {
                                        String motherboardId = motherboardDocumentSnapshot.getId();

                                        Task<QuerySnapshot> task = firestore.collection("Products")
                                                .whereEqualTo("product_name", motherboardId)
                                                .whereEqualTo("product_type", "Motherboard")
                                                .get();
                                        tasks.add(task);
                                    }

                                    // Wait for all the queries to complete
                                    Task<List<QuerySnapshot>> combinedTask = Tasks.whenAllSuccess(tasks);
                                    combinedTask.addOnSuccessListener(querySnapshotsList -> {
                                        motherboardModels.clear(); // Clear the previous data
                                        for (QuerySnapshot querySnapshot : querySnapshotsList) {
                                            if (!querySnapshot.isEmpty()) {
                                                for (DocumentSnapshot productDocumentSnapshot : querySnapshot.getDocuments()) {
                                                    String productName = productDocumentSnapshot.getString("product_name");

                                                    // Retrieve motherboardSocket, motherboardFormFactor, and motherboardMemoryType
                                                    DocumentSnapshot motherboardDocumentSnapshot = queryDocumentSnapshots.getDocuments().stream()
                                                            .filter(doc -> doc.getId().equals(productName))
                                                            .findFirst()
                                                            .orElse(null);
                                                    if (motherboardDocumentSnapshot != null) {
                                                        String motherboardSocket = motherboardDocumentSnapshot.getString("Motherboard_Socket");
                                                        String motherboardFormFactor = motherboardDocumentSnapshot.getString("Motherboard_Form_Factor");
                                                        String motherboardMemoryType = motherboardDocumentSnapshot.getString("Motherboard_Memory_Type");

                                                        String productPrice = productDocumentSnapshot.getString("product_price");
                                                        List<String> productImages = (List<String>) productDocumentSnapshot.get("product_image");
                                                        String productImage = null;
                                                        if (productImages != null && !productImages.isEmpty()) {
                                                            productImage = productImages.get(0);
                                                        }

                                                        MotherboardModel motherboardModel = new MotherboardModel(productName, motherboardSocket, motherboardFormFactor, motherboardMemoryType, productPrice, productImage);
                                                        motherboardModels.add(motherboardModel);
                                                    }
                                                }
                                            }
                                        }
                                        adapter.notifyDataSetChanged();
                                        // Check if any products were found
                                        if (motherboardModels.isEmpty()) {
                                            // No products found, handle the case
                                        }
                                    }).addOnFailureListener(e -> {
                                        // Handle any errors that occurred while fetching matching products
                                    });
                                })
                                .addOnFailureListener(e -> {
                                    // Handle any errors that occurred while fetching matching motherboards
                                });
                    } else {
                        // Handle the case when Temp document or CPU_Socket field doesn't exist
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle any errors that occurred while fetching the CPU_Socket value
                });
    }
}
