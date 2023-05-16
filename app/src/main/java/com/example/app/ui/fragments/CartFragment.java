package com.example.app.ui.fragments;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.OrdersActivity;
import com.example.app.R;
import com.example.app.ui.adapters.CartAdapter;
import com.example.app.ui.models.CartModel;
import com.example.app.ui.models.ProductModel;
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
import java.util.Map;

public class CartFragment extends Fragment implements CartAdapter.OnDeleteItemClickListener{

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference cartRef = db.collection("Cart");
    CollectionReference ordersRef = db.collection("Orders");
    CartAdapter adapter;
    List<CartModel> cartItems = new ArrayList<>();
    List<ProductModel> products = new ArrayList<>();
    TextView totalTextView, menu_options, products_empty;
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
        adapter.setDeleteItemClickListener(this);

        menu_options = view.findViewById(R.id.menu_options);
        menu_options.setOnClickListener(view1 -> check_order());

        Button checkoutButton = view.findViewById(R.id.place_order_button);
        checkoutButton.setOnClickListener(view1 -> checkout_popup());

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("Email")) {
            email = bundle.getString("Email");
        }

        cartRef.whereEqualTo("user_Email", email).addSnapshotListener((value, error) -> {
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
            if (total > 0) {
                totalTextView.setText(String.format(Locale.getDefault(), "Total: P%s.00", formattedNumber));
            } else {
                totalTextView.setText("Total: P0.00");
            }
            View productsEmptyView = view.findViewById(R.id.products_empty);
            if (cartItems.isEmpty()) {
                productsEmptyView.setVisibility(View.VISIBLE);
            } else {
                productsEmptyView.setVisibility(View.GONE);
            }
        });
        return view;
    }

    public void onDeleteItemClick(CartModel cart) {
        // Handle the delete item click event here
        deleteCartItem(cart);
    }

    private void deleteCartItem(CartModel cart) {
        String cartId = cart.getCart_id();
        // Remove the item from the RecyclerView
        int position = cartItems.indexOf(cart);
        cartItems.remove(position);
        adapter.notifyItemRemoved(position);

        // Remove the item from the Firestore collection
        cartRef.document(cartId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Item deleted successfully from Firestore
                    Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Error occurred while deleting the item from Firestore
                    Toast.makeText(getContext(), "Error deleting item", Toast.LENGTH_SHORT).show();
                    // Add the item back to the RecyclerView (in case of deletion failure)
                    cartItems.add(position, cart);
                    adapter.notifyItemInserted(position);
                });
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


        cartRef.whereEqualTo("user_Email", email).addSnapshotListener((value, error) -> {
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
            if (total > 0) {
                checkout_totalPrice.setText(String.format(Locale.getDefault(), "P%s.00", formattedNumber));
            } else {
                checkout_totalPrice.setText("Total: P0.00");
            }
        });

        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    private void place_order() {
        progressDialog.show();
        checkout_payment_method = contactPopupView.findViewById(R.id.checkout_payment_method);

        // Get the selected payment method
        int selectedId = checkout_payment_method.getCheckedRadioButtonId();
        String paymentMethod = paymentMethods[selectedId];

        // Set the data to the fields of the new order document
        Map<String, Object> orderData = new HashMap<>();
        orderData.put("email", checkout_email.getText().toString());
        orderData.put("address", checkout_address.getText().toString());
        orderData.put("contact_number", checkout_contact_number.getText().toString());
        orderData.put("total_price", checkout_totalPrice.getText().toString());
        orderData.put("payment_method", paymentMethod);
        orderData.put("status", "pending");

        // Save the order data in the Orders collection using an auto-generated document ID
        ordersRef.add(orderData)
                .addOnCompleteListener(orderTask -> {
                    progressDialog.dismiss();
                    if (orderTask.isSuccessful()) {
                        DocumentReference orderRef = orderTask.getResult();
                        String orderId = orderRef.getId(); // get the auto-generated document ID
                        orderRef.update("order_id", orderId);
                        // Get all items from the Cart collection under the user's email
                        cartRef.whereEqualTo("user_Email", checkout_email.getText().toString())
                                .get()
                                .addOnCompleteListener(cartTask -> {
                                    if (cartTask.isSuccessful()) {
                                        WriteBatch batch = db.batch();
                                        for (DocumentSnapshot doc : cartTask.getResult()) {
                                            // Get the item data and set it to a new document in the 'items' subcollection of the order document
                                            Map<String, Object> itemData = doc.getData();
                                            String productId = doc.getString("product_id");
                                            batch.set(orderRef.collection("items").document(productId), itemData);
                                            // Delete the item from the Cart collection
                                            batch.delete(doc.getReference());
                                        }
                                        batch.commit().addOnCompleteListener(deleteTask -> {
                                            if (deleteTask.isSuccessful()) {
                                                Toast.makeText(getContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getContext(), "Error deleting cart items", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        Toast.makeText(getContext(), "Error getting cart items", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Error placing order", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void check_order() {
        Intent intent = new Intent(getContext(), OrdersActivity.class);
        startActivity(intent);
    }
}
