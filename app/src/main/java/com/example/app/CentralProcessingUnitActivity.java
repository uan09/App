package com.example.app;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.CpuAdapter;
import com.example.app.ui.models.CpuModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CentralProcessingUnitActivity extends AppCompatActivity {

    private static final String TAG = "CPUActivity";
    private FirebaseFirestore firestore;
    private String email;
    private ArrayList<CpuModel> cpuModels;
    String product_name, product_image, product_price, CPU_Cores, CPU_Socket, preferenceCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central_processing_unit);

        email = getIntent().getStringExtra("email");
        if (email != null) {
            Toast.makeText(CentralProcessingUnitActivity.this, "email" + email, Toast.LENGTH_SHORT).show();
        }

        firestore = FirebaseFirestore.getInstance();
        cpuModels = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.cpu_recyclerview_view);
        CpuAdapter adapter = new CpuAdapter(this, cpuModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firestore.collection("UserAccounts")
                .whereEqualTo("user_Email", email)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot userDocument = task.getResult().getDocuments().get(0);
                        String userId = userDocument.getId();

                        // Retrieve the preference category from the UserPreference collection
                        firestore.collection("UserAccounts")
                                .document(userId)
                                .collection("UserPreference")
                                .limit(1)
                                .get()
                                .addOnCompleteListener(preferenceTask -> {
                                    if (preferenceTask.isSuccessful() && !preferenceTask.getResult().isEmpty()) {
                                        DocumentSnapshot preferenceDocument = preferenceTask.getResult().getDocuments().get(0);
                                        preferenceCategory = preferenceDocument.getString("preference_category");
                                        if (preferenceCategory != null) {
                                            // Query Firestore to fetch the desired products
                                            Query query = firestore.collection("Products")
                                                    .whereEqualTo("product_type", "Processor")
                                                    .whereEqualTo("product_category", preferenceCategory);

                                            query.get().addOnCompleteListener(productTask -> {
                                                if (productTask.isSuccessful()) {
                                                    cpuModels.clear();
                                                    for (QueryDocumentSnapshot productDocument : productTask.getResult()) {
                                                        // Extract the necessary product information from the document
                                                        product_name = productDocument.getString("product_name");
                                                        product_price = productDocument.getString("product_price");

                                                        List<String> productImages = (List<String>) productDocument.get("product_image");
                                                        if (productImages != null && !productImages.isEmpty()) {
                                                            // Retrieve the first element of the array
                                                            product_image = productImages.get(0);
                                                        }

                                                        // Query the Processor_DB collection based on product_name
                                                        firestore.collection("Processor_DB")
                                                                .document(product_name)
                                                                .get()
                                                                .addOnCompleteListener(processorTask -> {
                                                                    if (processorTask.isSuccessful()) {
                                                                        DocumentSnapshot processorDocument = processorTask.getResult();
                                                                        if (processorDocument.exists()) {
                                                                            // Retrieve the CPU_Cores and CPU_Socket fields
                                                                            CPU_Cores = processorDocument.getString("CPU_Cores");
                                                                            CPU_Socket = processorDocument.getString("CPU_Socket");

                                                                            // Create a new CpuModel object with the extracted information
                                                                            CpuModel cpuModel = new CpuModel(product_name, product_image, product_price, CPU_Cores, CPU_Socket);
                                                                            cpuModels.add(cpuModel);
                                                                        }
                                                                    } else {
                                                                        Log.d(TAG, "Error getting processor document: ", processorTask.getException());
                                                                    }

                                                                    if (cpuModels.size() == productTask.getResult().size()) {
                                                                        adapter.notifyDataSetChanged();
                                                                    }// Notify the adapter about the updated data
                                                                });
                                                    }
                                                } else {
                                                    Log.d(TAG, "Error getting products: ", productTask.getException());
                                                }
                                            });
                                        } else {
                                            Log.d(TAG, "User preference category is null");
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting preference document: ", preferenceTask.getException());
                                    }
                                });
                    } else {
                        Log.d(TAG, "Error getting user account: ", task.getException());
                    }
                });
        }
    }