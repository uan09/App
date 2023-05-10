package com.example.app.ui.fragments;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.ui.adapters.CartAdapter;
import com.example.app.ui.models.CartModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference cartRef = db.collection("Cart");
    CartAdapter adapter;
    List<CartModel> cartItems = new ArrayList<>();
    TextView totalTextView;

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
        checkoutButton.setOnClickListener(view1 -> {
            // Handle checkout button click
        });

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
}
