package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.NewBuildAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class CheckBuildActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewBuildAdapter newBuildAdapter;
    private List<DocumentSnapshot> documents;
    private Button buyBuild;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_build);

        email = getIntent().getStringExtra("value");
        if (email != null) {
            Toast.makeText(CheckBuildActivity.this, "mail" + email, Toast.LENGTH_SHORT).show();
        }

        ImageView back = findViewById(R.id.menu_back);
        back.setOnClickListener(view -> {

            Intent intent = new Intent(this, NewBuildActivity.class);
            intent.putExtra("Email", email); // Pass the email value to NewBuildActivity
            startActivity(intent);

        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buyBuild = findViewById(R.id.buyButton);
        buyBuild.setOnClickListener(view -> {
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            // Iterate over the documents in the RecyclerView
            for (DocumentSnapshot document : documents) {
                if (document.exists()) {
                    String productName = document.getString("product_name");
                    String productType = document.getId();
                    String productPrice = document.getString("product_price");
                    String productImage = document.getString("product_image");
                    String productNumber = "1"; // Set the default value for product_number as 1

                    // Get the store_name from the Products collection based on the product_name
                    firestore.collection("Products")
                            .whereEqualTo("product_name", productName) // Assuming "product_name" is the field name in the Products collection
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    // Assuming there's only one document with the matching product_name
                                    DocumentSnapshot productDocument = queryDocumentSnapshots.getDocuments().get(0);
                                    String storeName = productDocument.getString("store_name");
                                    String productId = productDocument.getString("product_id");

                                    // Create a new HashMap to store the cart item information
                                    HashMap<String, Object> cartItem = new HashMap<>();
                                    cartItem.put("store_name", storeName);
                                    cartItem.put("product_id", productId);
                                    cartItem.put("product_name", productName);
                                    cartItem.put("product_type", productType);
                                    cartItem.put("product_price", productPrice);
                                    cartItem.put("product_number", productNumber);
                                    cartItem.put("product_image_url", productImage);
                                    cartItem.put("user_Email", email);

                                    // Save the cart item in the Cart collection
                                    firestore.collection("Cart")
                                            .document()
                                            .set(cartItem)
                                            .addOnSuccessListener(aVoid -> {
                                                Toast.makeText(CheckBuildActivity.this, "Product added to cart", Toast.LENGTH_SHORT).show();
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(CheckBuildActivity.this, "Failed to add product to cart", Toast.LENGTH_SHORT).show();
                                            });
                                } else {
                                    // Handle the case where no matching document is found
                                    Toast.makeText(CheckBuildActivity.this, "Product not found", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(CheckBuildActivity.this, "Failed to retrieve product", Toast.LENGTH_SHORT).show();
                            });
                }
            }

            // Redirect the user back to the NavigationActivity
            Intent intent = new Intent(CheckBuildActivity.this, NavigationActivity.class);
            intent.putExtra("Value", email);
            startActivity(intent);
        });


        documents = new ArrayList<>();
        newBuildAdapter = new NewBuildAdapter(this, documents);
        recyclerView.setAdapter(newBuildAdapter);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        Query query = firestore.collection("NewBuild")
                .whereIn(FieldPath.documentId(), Arrays.asList("Processor", "Motherboard", "Graphics Card",
                        "RAM", "HDD", "SSD", "CPU Cooler", "PSU", "Computer Case"));

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null) {
                        documents.addAll(querySnapshot.getDocuments());

                        // Sort the documents based on document IDs
                        Collections.sort(documents, new Comparator<DocumentSnapshot>() {
                            @Override
                            public int compare(DocumentSnapshot doc1, DocumentSnapshot doc2) {
                                String id1 = doc1.getId();
                                String id2 = doc2.getId();
                                return getOrder(id1) - getOrder(id2);
                            }

                            private int getOrder(String documentId) {
                                // Define the order of the documents based on their document IDs
                                switch (documentId) {
                                    case "Processor":
                                        return 1;
                                    case "Motherboard":
                                        return 2;
                                    case "Graphics Card":
                                        return 3;
                                    case "RAM":
                                        return 4;
                                    case "HDD":
                                        return 5;
                                    case "SSD":
                                        return 6;
                                    case "CPU Cooler":
                                        return 7;
                                    case "PSU":
                                        return 8;
                                    case "Computer Case":
                                        return 9;
                                    default:
                                        return 0; // Default order for unrecognized document IDs
                                }
                            }
                        });

                        newBuildAdapter.notifyDataSetChanged();

                        double totalPrice = calculateTotalPrice(documents);

                        // Display the total price
                        NumberFormat formatter = new DecimalFormat("###,###,###");
                        TextView totalPriceTextView = findViewById(R.id.totalPrice);

                        String formattedNumber = formatter.format(totalPrice);
                        totalPriceTextView.setText("P " + formattedNumber +".00");
                    }
                } else {
                    // Handle the failure to retrieve the documents
                }
            }
        });
    }

    private String searchProductIdInProductsCollection(String productName) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // Search for the store_name in the Products collection based on the product_name
        Query query = firestore.collection("Products")
                .whereEqualTo("product_name", productName)
                .limit(1);

        Task<QuerySnapshot> task = query.get();
        try {
            QuerySnapshot querySnapshot = Tasks.await(task);
            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                return documentSnapshot.getString("product_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Return null if store_name is not found
    }

    private String searchStoreNameInProductsCollection(String productName) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // Search for the store_name in the Products collection based on the product_name
        Query query = firestore.collection("Products")
                .whereEqualTo("product_name", productName)
                .limit(1);

        Task<QuerySnapshot> task = query.get();
        try {
            QuerySnapshot querySnapshot = Tasks.await(task);
            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                return documentSnapshot.getString("store_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Return null if store_name is not found
    }

    private double calculateTotalPrice(List<DocumentSnapshot> documents) {
        double totalPrice = 0.00;
        for (DocumentSnapshot document : documents) {
            if (document.exists() && document.contains("product_price")) {
                String priceString = document.getString("product_price");
                try {
                    double price = Double.parseDouble(priceString);
                    totalPrice += price;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    // Handle the case where the priceString cannot be parsed as a double
                }
            }
        }
        return totalPrice;
    }
}
