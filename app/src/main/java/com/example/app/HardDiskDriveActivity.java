package com.example.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.HddAdapter;
import com.example.app.ui.models.HddModel;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HardDiskDriveActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private ArrayList<HddModel> hddModels;
    private HddAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_disk_drive);

        firestore = FirebaseFirestore.getInstance();
        hddModels = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.hdd_recyclerview_view);
        adapter = new HddAdapter(this, hddModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Read the Temp document within the TempItems collection
        firestore.collection("TempItems")
                .document("Temp")
                .get()
                .addOnSuccessListener(tempDocumentSnapshot -> {
                    if (tempDocumentSnapshot.exists()) {
                        String motherboardSataPort = tempDocumentSnapshot.getString("Motherboard_SATA_Port");

                        // Get all documents in the GPU_DB collection with GPU_Memory_Type equal to motherboardMemoryType
                        firestore.collection("HDD_DB")
                                .whereEqualTo("HDD_Interface", motherboardSataPort)
                                .get()
                                .addOnSuccessListener(hddDocumentSnapshots -> {
                                    List<Task<QuerySnapshot>> tasks = new ArrayList<>();
                                    for (DocumentSnapshot hddDocumentSnapshot : hddDocumentSnapshots) {
                                        String gpuId = hddDocumentSnapshot.getId();

                                        Task<QuerySnapshot> task = firestore.collection("Products")
                                                .whereEqualTo("product_name", gpuId)
                                                .whereEqualTo("product_type", "Hard Disk Drive")
                                                .get();
                                        tasks.add(task);
                                    }

                                    // Wait for all the queries to complete
                                    Task<List<QuerySnapshot>> combinedTask = Tasks.whenAllSuccess(tasks);
                                    combinedTask.addOnSuccessListener(querySnapshotsList -> {
                                        hddModels.clear(); // Clear the previous data
                                        for (QuerySnapshot querySnapshot : querySnapshotsList) {
                                            if (!querySnapshot.isEmpty()) {
                                                for (DocumentSnapshot productDocumentSnapshot : querySnapshot.getDocuments()) {
                                                    String productName = productDocumentSnapshot.getString("product_name");

                                                    // Retrieve GPU_Memory_Type and GPU_Chipset
                                                    DocumentSnapshot hddDocumentSnapshot = hddDocumentSnapshots.getDocuments().stream()
                                                            .filter(doc -> doc.getId().equals(productName))
                                                            .findFirst()
                                                            .orElse(null);
                                                    if (hddDocumentSnapshot != null) {
                                                        String hddCapacity = hddDocumentSnapshot.getString("HDD_Capacity");
                                                        String hddInterface = hddDocumentSnapshot.getString("HDD_Interface");

                                                        String productPrice = productDocumentSnapshot.getString("product_price");
                                                        List<String> productImages = (List<String>) productDocumentSnapshot.get("product_image");
                                                        String productImage = null;
                                                        if (productImages != null && !productImages.isEmpty()) {
                                                            productImage = productImages.get(0);
                                                        }

                                                        HddModel hddModel = new HddModel(productName, hddInterface, hddCapacity, productPrice, productImage);
                                                        hddModels.add(hddModel);
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