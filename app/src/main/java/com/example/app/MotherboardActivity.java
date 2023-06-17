package com.example.app;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.MotherboardAdapter;
import com.example.app.ui.models.MotherboardModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MotherboardActivity extends AppCompatActivity {
    private static final String TAG = "CPUActivity";
    private FirebaseFirestore firestore;
    private ArrayList<MotherboardModel> motherboardModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motherboard);

        String email = getIntent().getStringExtra("email");
        if (email != null) {
            Toast.makeText(MotherboardActivity.this, "email" + email, Toast.LENGTH_SHORT).show();
        }

        RecyclerView recyclerView = findViewById(R.id.motherboard_recyclerview_view);

        MotherboardAdapter adapter = new MotherboardAdapter(this, motherboardModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String cpuSocket = ""; // Initialize the variable to store the CPU_Socket value

        firestore.collection("TempItems")
                .document("Temp")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        cpuSocket = documentSnapshot.getString("CPU_Socket");

                        // Call the next function to find matching motherboards
                        findMatchingMotherboards(cpuSocket);
                    } else {
                        // Handle the case when Temp document or CPU_Socket field doesn't exist
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle any errors that occurred while fetching the CPU_Socket value
                });
    }

    private void findMatchingMotherboards(String cpuSocket) {
        ArrayList<String> matchingMotherboardNames = new ArrayList<>(); // Store the names of matching motherboards

        firestore.collection("Motherboard_DB")
                .whereEqualTo("Motherboard_Socket", cpuSocket)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String motherboardName = documentSnapshot.getString("Motherboard_Name");
                        String motherboardSocket = documentSnapshot.getString("Motherboard_Socket");
                        String motherboardFormFactor = documentSnapshot.getString("Motherboard_Form_Factor");
                        String motherboardMemoryType = documentSnapshot.getString("Motherboard_Memory_Type");
                        matchingMotherboardNames.add(motherboardName);
                    }

                    // Call the next function to find matching products based on motherboard names
                    findMatchingProducts(matchingMotherboardNames);
                })
                .addOnFailureListener(e -> {
                    // Handle any errors that occurred while fetching matching motherboards
                });
    }

    private void findMatchingProducts(ArrayList<String> matchingMotherboardNames) {
        ArrayList<MotherboardModel> matchingProducts = new ArrayList<>(); // Store the matching products

        firestore.collection("Products")
                .whereIn("product_name", matchingMotherboardNames)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        // Retrieve the necessary data from the document and create a MotherboardModel object
                        String product_name = documentSnapshot.getString("product_name");
                        String product_price = documentSnapshot.getString("product_price");
                        List<String> productImages = (List<String>) documentSnapshot.get("product_image");
                        String product_image = null;
                        if (productImages != null && !productImages.isEmpty()) {
                            product_image = productImages.get(0);
                        }
                        final String finalProductImage = product_image;
                        // Add more fields as needed

                        MotherboardModel motherboardModel = new MotherboardModel(product_name, product_price, finalProductImage, motherboardSocket, motherboardFormFactor, motherboardMemoryType);
                        matchingProducts.add(motherboardModel);
                    }

                    // Display the matching products using the RecyclerView adapter
                    displayMatchingProducts(matchingProducts);
                })
                .addOnFailureListener(e -> {
                    // Handle any errors that occurred while fetching matching products
                });
    }

    private void displayMatchingProducts(ArrayList<MotherboardModel> matchingProducts) {
        RecyclerView recyclerView = findViewById(R.id.motherboard_recyclerview_view);
        MotherboardAdapter adapter = new MotherboardAdapter(this, matchingProducts);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}