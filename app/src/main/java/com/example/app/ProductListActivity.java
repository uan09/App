package com.example.app;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.ProductsAdapter;
import com.example.app.ui.models.ProductModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView productListRecyclerView;
    private ProductsAdapter productsAdapter;
    private List<ProductModel> productList = new ArrayList<>();
    TextView product_empty;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference productsRef= db.collection("Products");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        String categoryType = getIntent().getStringExtra("CategoryType");

        productListRecyclerView = findViewById(R.id.product_list_recyclerView);
        productListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProductsAdapter productsAdapter = new ProductsAdapter(productList, this, product_empty, productsRef);
        productListRecyclerView.setAdapter(productsAdapter);

        // Query Firestore to get the products with the matching categoryType
        productsRef.whereEqualTo("product_type", categoryType)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        ProductModel product = documentSnapshot.toObject(ProductModel.class);
                        productList.add(product);
                    }
                    productsAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error retrieving products", Toast.LENGTH_SHORT).show();
                });
    }
}
