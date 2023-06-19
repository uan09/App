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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoolerAdapter extends RecyclerView.Adapter<CoolerAdapter.CoolerViewHolder> {
    private Context context;
    private ArrayList<CoolerModel> coolerModels;
    private FirebaseFirestore firestore;
    private List<CoolerModel> cpuCoolerModels;
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

        String coolerName = (coolerModel.getProduct_name());
        holder.coolerNameTextView.setText(coolerName);
        holder.coolerSocketTextView.setText(coolerModel.getCooler_Socket());
        holder.coolerRPMTextView.setText(coolerModel.getCooler_RPM());
        String formattedNumber = formatter.format(Long.valueOf(coolerModel.getProduct_price()));
        holder.productPriceTextView.setText("P" + formattedNumber + ".00");

        // Load product image using Glide or any other image loading library
        Glide.with(context)
                .load(coolerModel.getProduct_image())
                .centerCrop()
                .into(holder.productImageView);

        holder.add_item_button.setOnClickListener(v -> {
            Map<String, Object> productData = new HashMap<>();
            productData.put("product_name", coolerModel.getProduct_name());
            productData.put("product_image", coolerModel.getProduct_image());
            productData.put("product_price", coolerModel.getProduct_price());
            productData.put("Cooler_Socket", coolerModel.getCooler_Socket());
            productData.put("Cooler_RPM", coolerModel.getCooler_RPM());

            firestore.collection("CPUCoolersDB")
                    .document(coolerName)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            DocumentSnapshot coolerDocument = documentSnapshot;
                            // Save the data to the NewBuild collection under the Motherboard document
                            firestore.collection("NewBuild")
                                    .document("CPU Cooler")
                                    .set(productData)
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

    public void setCPUCoolerModels(List<CoolerModel> cpuCoolers) {
        this.cpuCoolerModels = cpuCoolers;
        notifyDataSetChanged();
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
