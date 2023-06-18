package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.CpuAdapter;
import com.example.app.ui.models.CpuModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CentralProcessingUnitActivity extends AppCompatActivity {

    private static final String TAG = "CPUActivity";
    private FirebaseFirestore firestore;
    private ArrayList<CpuModel> cpuModels;

    private static final int REQUEST_CODE_BUILD = 1;

    Button backbutton;

    ImageView menu_back;
    String preferenceCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central_processing_unit);



        String email = getIntent().getStringExtra("email");
        if (email != null) {
            Toast.makeText(CentralProcessingUnitActivity.this, "mail" + email, Toast.LENGTH_SHORT).show();
        }

        ImageView menuBack = findViewById(R.id.menu_back);
        menuBack.setOnClickListener(view -> {

            Intent intent = new Intent(this, NewBuildActivity.class);
            intent.putExtra("Email", email); // Pass the email value to NewBuildActivity
            startActivity(intent);

        });

        firestore = FirebaseFirestore.getInstance();
        cpuModels = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.cpu_recyclerview_view);
        CpuAdapter adapter = new CpuAdapter(this, cpuModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
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
                                            firestore.collection("Products")
                                                    .whereEqualTo("product_type", "Processor")
                                                    .whereEqualTo("product_category", preferenceCategory)
                                                    .get()
                                                    .addOnCompleteListener(productTask -> {
                                                        if (productTask.isSuccessful() && !productTask.getResult().isEmpty()) {
                                                            cpuModels.clear();
                                                            for (DocumentSnapshot productDocument : productTask.getResult()) {
                                                                String product_name = productDocument.getString("product_name");
                                                                String product_price = productDocument.getString("product_price");

                                                                List<String> productImages = (List<String>) productDocument.get("product_image");
                                                                String product_image = null;
                                                                if (productImages != null && !productImages.isEmpty()) {
                                                                    product_image = productImages.get(0);
                                                                }

                                                                final String finalProductImage = product_image; // Declare final variable for product_image

                                                                firestore.collection("Processor_DB")
                                                                        .document(product_name)
                                                                        .get()
                                                                        .addOnCompleteListener(processorTask -> {
                                                                            if (processorTask.isSuccessful()) {
                                                                                DocumentSnapshot processorDocument = processorTask.getResult();
                                                                                if (processorDocument.exists()) {
                                                                                    String CPU_Cores = processorDocument.getString("CPU_Cores");
                                                                                    String CPU_Socket = processorDocument.getString("CPU_Socket");

                                                                                    CpuModel cpuModel = new CpuModel(product_name, finalProductImage, product_price, CPU_Cores, CPU_Socket);
                                                                                    cpuModels.add(cpuModel);

                                                                                    if (cpuModels.size() == productTask.getResult().size()) {
                                                                                        adapter.notifyDataSetChanged();
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                Log.d(TAG, "Error getting processor document: ", processorTask.getException());
                                                                            }
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

