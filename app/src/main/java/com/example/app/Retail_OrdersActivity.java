package com.example.app;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.RetailerOrdersAdapter;
import com.example.app.ui.models.OrderModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class Retail_OrdersActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ordersRef = db.collection("Orders");
    private RecyclerView recyclerView;
    private RetailerOrdersAdapter adapter;
    private List<OrderModel> ordersList;
    private String storeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retail_orders);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("storeName")) {
            storeName = bundle.getString("storeName");
        }

        recyclerView = findViewById(R.id.retail_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ordersList = new ArrayList<>();
        adapter = new RetailerOrdersAdapter(ordersList);
        recyclerView.setAdapter(adapter);

        // Retrieve orders from Firestore based on the store_name field
        Query query = ordersRef.whereEqualTo("store_name", storeName);
        query.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e(TAG, "Error fetching orders", error);
                return;
            }

            ordersList.clear();
            if (value != null && !value.isEmpty()) {
                // Iterate through each order document and convert it to an OrderModel object
                for (DocumentSnapshot doc : value.getDocuments()) {
                    OrderModel order = doc.toObject(OrderModel.class);
                    if (order != null) {
                        order.setOrderId(doc.getId());
                        ordersList.add(order);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
