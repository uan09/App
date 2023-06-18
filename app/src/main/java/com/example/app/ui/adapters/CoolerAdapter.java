package com.example.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app.R;
import com.example.app.ui.models.CoolerModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CoolerAdapter extends RecyclerView.Adapter<CoolerAdapter.CoolerViewHolder> {
    private Context context;
    private ArrayList<CoolerModel> coolerModels;
    private FirebaseFirestore firestore;

    public CoolerAdapter(Context context, ArrayList<CoolerModel> coolerModels) {
        this.context = context;
        this.coolerModels = coolerModels;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public CoolerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cooler, parent, false);
        return new CoolerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoolerViewHolder holder, int position) {
        CoolerModel coolerModel = coolerModels.get(position);

        NumberFormat formatter = new DecimalFormat("###,###,###");

        String coolerName = (coolerModel.getProductName());
        holder.coolerNameTextView.setText(coolerName);
        String formattedNumber = formatter.format(Long.valueOf(coolerModel.getProductPrice()));
        holder.coolerRPMTextView.setText(coolerModel.getCoolerRPM());
        holder.coolerNameTextView.setText(coolerModel.getProductName());
        holder.coolerSocketTextView.setText(coolerModel.getCoolerSocket());
        holder.productPriceTextView.setText("P" + formattedNumber + ".00");

        // Load product image using Glide or any other image loading library
        Glide.with(context)
                .load(coolerModel.getProductImage())
                .centerCrop()
                .into(holder.productImageView);
        holder.add_item_button.setOnClickListener(v -> {
            firestore.collection("CPUCoolersDB")
                    .document(coolerName)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            DocumentSnapshot ramDocument = documentSnapshot;
                            // Save the data to the NewBuild collection under the Motherboard document
                            firestore.collection("NewBuild")
                                    .document("CPU Cooler")
                                    .set(ramDocument.getData())
                                    .addOnSuccessListener(aVoid -> {
                                        // Data saved successfully to NewBuild collection
                                        Toast.makeText(context, "CPU Cooler Selected", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Handle any errors that occurred while saving data to NewBuild collection
                                    });
                        } else {
                            // No matching document found in the Motherboard_DB collection
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Handle any errors that occurred while fetching the document from Motherboard_DB collection
                    });
        });
    }

    @Override
    public int getItemCount() {
        return coolerModels.size();
    }

    static class CoolerViewHolder extends RecyclerView.ViewHolder {
        TextView coolerNameTextView;
        TextView coolerSocketTextView;
        TextView coolerRPMTextView;
        TextView productPriceTextView;
        ImageView productImageView;
        ImageButton add_item_button;

        CoolerViewHolder(@NonNull View itemView) {
            super(itemView);
            coolerNameTextView = itemView.findViewById(R.id.ProductName);
            coolerSocketTextView = itemView.findViewById(R.id.CoolerSocket);
            coolerRPMTextView = itemView.findViewById(R.id.CoolerRPM);
            productPriceTextView = itemView.findViewById(R.id.Price);
            productImageView = itemView.findViewById(R.id.imgProduct);
            add_item_button = itemView.findViewById(R.id.add_item_button);
        }
    }
}
