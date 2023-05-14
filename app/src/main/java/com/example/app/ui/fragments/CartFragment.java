package com.example.app.ui.fragments;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.ui.adapters.CartAdapter;
import com.example.app.ui.models.CartModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference cartRef = db.collection("Cart");
    CollectionReference ordersRef = db.collection("Orders");
    CartAdapter adapter;
    List<CartModel> cartItems = new ArrayList<>();
    TextView totalTextView;
    EditText checkout_email, checkout_address, checkout_contact_number, checkout_totalPrice;
    Button checkout_cancel_order, checkout_place_order;
    RadioGroup checkout_payment_method;
    String email;
    ProgressDialog progressDialog;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    View contactPopupView;
    String[] paymentMethods = {"Credit/Debit Card", "Online Banking", "Gcash", "Paymaya", "Cash on Delivery"};

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        NumberFormat formatter = new DecimalFormat("###,###,###");
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.cart_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartAdapter(cartItems, getContext());
        recyclerView.setAdapter(adapter);

        totalTextView = view.findViewById(R.id.total_price);
        Button checkoutButton = view.findViewById(R.id.place_order_button);
        checkoutButton.setOnClickListener(view1 -> checkout_popup());

        cartRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e(TAG, "Error fetching cart items", error);
                return;
            }

            cartItems.clear();
            int total = 0;
            assert value != null;

            String formattedNumber = null;
            for (DocumentSnapshot doc : value) {
                CartModel cartItem = doc.toObject(CartModel.class);
                assert cartItem != null;
                cartItem.setCart_id(doc.getId());
                cartItems.add(cartItem);
                cartItem.setProduct_image_url(doc.getString("product_image_url"));
                String priceString = cartItem.getProduct_price().replaceAll("\\D", "");
                if (!priceString.isEmpty()) {
                    try {
                        int price = Integer.parseInt(priceString);
                        total += price * Integer.parseInt(cartItem.getProduct_number());
                        formattedNumber = formatter.format(Long.valueOf(total));
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "Error parsing price string: " + priceString, e);
                    }
                }
            }
            adapter.notifyDataSetChanged();
            totalTextView.setText(String.format(Locale.getDefault(), "Total: P%s.00", formattedNumber));
        });

        return view;
    }

    private void checkout_popup() {
        NumberFormat formatter = new DecimalFormat("###,###,###");
        dialogBuilder = new AlertDialog.Builder(getContext());
        contactPopupView = getLayoutInflater().inflate(R.layout.activity_checkout_popup, null);

        RadioGroup checkout_payment_method = contactPopupView.findViewById(R.id.checkout_payment_method);
        for (int i = 0; i < paymentMethods.length; i++) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(paymentMethods[i]);
            radioButton.setId(i);
            checkout_payment_method.addView(radioButton);
        }

        db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView1 = contactPopupView.findViewById(R.id.checkout_recyclerView);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartAdapter(cartItems, getContext());
        recyclerView1.setAdapter(adapter);

        checkout_email = contactPopupView.findViewById(R.id.checkout_email);
        checkout_contact_number = contactPopupView.findViewById(R.id.checkout_contact_number);
        checkout_address = contactPopupView.findViewById(R.id.checkout_address);
        checkout_totalPrice = contactPopupView.findViewById(R.id.checkout_totalPrice);
        checkout_payment_method = contactPopupView.findViewById(R.id.checkout_payment_method);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("Email")) {
            email = bundle.getString("Email");
        }

        db.collection("UserAccounts").whereEqualTo("user_Email", email).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                DocumentSnapshot document = task.getResult().getDocuments().get(0);
                String contactNumber = document.getString("user_contactNum");
                String address = document.getString("user_Address");

                checkout_email.setText(email);
                checkout_contact_number.setText(contactNumber);
                checkout_address.setText(address);
            }
        });

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading Data");
        progressDialog.setMessage("Please Wait While Uploading Your data...");

        checkout_cancel_order = contactPopupView.findViewById(R.id.checkout_cancel_order);
        checkout_cancel_order.setOnClickListener(view -> dialog.dismiss());

        checkout_place_order = contactPopupView.findViewById(R.id.checkout_place_order);
        checkout_place_order.setOnClickListener(view -> place_order());

        cartRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e(TAG, "Error fetching cart items", error);
                return;
            }

            cartItems.clear();
            int total = 0;
            assert value != null;

            String formattedNumber = null;
            for (DocumentSnapshot doc : value) {
                CartModel cartItem = doc.toObject(CartModel.class);
                assert cartItem != null;
                cartItem.setCart_id(doc.getId());
                cartItems.add(cartItem);
                cartItem.setProduct_image_url(doc.getString("product_image_url"));
                String priceString = cartItem.getProduct_price().replaceAll("\\D", "");
                if (!priceString.isEmpty()) {
                    try {
                        int price = Integer.parseInt(priceString);
                        total += price * Integer.parseInt(cartItem.getProduct_number());
                        formattedNumber = formatter.format(Long.valueOf(total));
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "Error parsing price string: " + priceString, e);
                    }
                }
            }
            adapter.notifyDataSetChanged();
            checkout_totalPrice.setText(String.format(Locale.getDefault(), "P%s.00", formattedNumber));
        });

        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    private void place_order() {
        progressDialog.show();

        // Create a new document in the Orders collection
        DocumentReference newOrderRef = ordersRef.document();
        int selectedId = checkout_payment_method.getCheckedRadioButtonId();
        String paymentMethod = paymentMethods[selectedId];
        // Set the data to the fields of the new document
        newOrderRef.set(new HashMap<String, Object>() {{
            put("email", checkout_email.getText().toString());
            put("address", checkout_address.getText().toString());
            put("contact_number", checkout_contact_number.getText().toString());
            put("total_price", checkout_totalPrice.getText().toString());
            put("payment_method", paymentMethod);
            put("products", cartItems); // Assuming you want to save the list of products
        }}).addOnCompleteListener(task -> {
            progressDialog.dismiss();
            if (task.isSuccessful()) {
                cartRef.get().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        // Delete all items in the Cart collection
                        WriteBatch batch = db.batch();
                        for (DocumentSnapshot doc : task1.getResult()) {
                            batch.delete(doc.getReference());
                        }
                        batch.commit();
                    } else {
                        Log.e(TAG, "Error getting cart items", task1.getException());
                    }
                });
                dialog.dismiss();
            } else {
                Log.e(TAG, "Error placing order", task.getException());
            }
        });
    }
}
