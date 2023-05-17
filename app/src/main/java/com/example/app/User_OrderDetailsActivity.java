package com.example.app;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.OrderItemsAdapter;
import com.example.app.ui.models.CartModel;
import com.example.app.ui.models.OrderModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class User_OrderDetailsActivity extends AppCompatActivity {
    private EditText order_number;
    private EditText order_email;
    private EditText order_contact_number;
    private EditText order_address;
    private EditText order_payment;
    private EditText order_status;
    private EditText order_total;
    private RecyclerView orderItemsRecyclerView;
    private OrderItemsAdapter orderItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_details);

        // Retrieve the order ID from the intent
        String orderId = getIntent().getStringExtra("orderID");

        // Initialize the views
        order_number = findViewById(R.id.order_number);
        order_email = findViewById(R.id.order_email);
        order_contact_number = findViewById(R.id.order_contact_number);
        order_address = findViewById(R.id.order_address);
        order_payment = findViewById(R.id.order_payment);
        order_status = findViewById(R.id.order_status);
        order_total = findViewById(R.id.order_total);

        // Retrieve the order details from the "Orders" collection in Firestore
        FirebaseFirestore.getInstance()
                .collection("Orders")
                .document(orderId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            OrderModel order = document.toObject(OrderModel.class);

                            // Set the order details in the TextViews
                            order_number.setText(orderId);
                            order_email.setText(order.getEmail());
                            order_contact_number.setText(order.getContact_number());
                            order_address.setText(order.getAddress());
                            order_payment.setText(order.getPayment_method());
                            order_status.setText(order.getStatus());
                            order_total.setText(order.getTotal_price());

                            // Retrieve the order items from the nested "items" collection
                            retrieveOrderItems(orderId);
                        }
                    }
                });
    }

    private void retrieveOrderItems(String orderId) {
        FirebaseFirestore.getInstance()
                .collection("Orders")
                .document(orderId)
                .collection("items")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            List<CartModel> orderItems = new ArrayList<>();
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                CartModel orderItem = document.toObject(CartModel.class);
                                orderItems.add(orderItem);
                            }

                            // Set up the RecyclerView for order items
                            orderItemsRecyclerView = findViewById(R.id.order_items_recyclerview);
                            orderItemsAdapter = new OrderItemsAdapter(orderItems, this);
                            orderItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                            orderItemsRecyclerView.setAdapter(orderItemsAdapter);
                        }
                    }
                });
    }
}
