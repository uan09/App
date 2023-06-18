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
import com.example.app.ui.models.PsuModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class PsuAdapter extends RecyclerView.Adapter<PsuAdapter.PsuViewHolder> {
    private Context context;
    private List<PsuModel> psuModels;
    private FirebaseFirestore firestore;


    public PsuAdapter(Context context, List<PsuModel> psuModels) {
        this.context = context;
        this.psuModels = psuModels;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public PsuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_psu, parent, false);
        return new PsuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PsuViewHolder holder, int position) {
        PsuModel psuModel = psuModels.get(position);
        NumberFormat formatter = new DecimalFormat("###,###,###");
        String psuName = (psuModel.getProductName());
        holder.productNameTextView.setText(psuName);
        holder.psuFormFactorTextView.setText(psuModel.getPsuFormFactor());
        holder.psuWattageTextView.setText(psuModel.getPsuWattage());
        String formattedNumber = formatter.format(Long.valueOf(psuModel.getProductPrice()));
        holder.productPriceTextView.setText("P" + formattedNumber + ".00");

        // Load product image using Glide or any other image loading library
        Glide.with(context)
                .load(psuModel.getProductImage())
                .centerCrop() // Placeholder image resource
                .into(holder.productImageView);

        holder.add_item_button.setOnClickListener(v -> {
            firestore.collection("PSU_DB")
                    .document(psuName)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            DocumentSnapshot psuDocument = documentSnapshot;
                            // Save the data to the NewBuild collection under the Motherboard document
                            firestore.collection("NewBuild")
                                    .document("PSU")
                                    .set(psuDocument.getData())
                                    .addOnSuccessListener(aVoid -> {
                                        // Data saved successfully to NewBuild collection
                                        Toast.makeText(context, "PSU Selected", Toast.LENGTH_SHORT).show();
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
        return psuModels.size();
    }

    static class PsuViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView psuFormFactorTextView;
        TextView psuWattageTextView;
        TextView productPriceTextView;
        ImageView productImageView;
        ImageButton add_item_button;

        PsuViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.ProductName);
            psuFormFactorTextView = itemView.findViewById(R.id.PSUFormFactor);
            psuWattageTextView = itemView.findViewById(R.id.PSUWattage);
            productPriceTextView = itemView.findViewById(R.id.Price);
            productImageView = itemView.findViewById(R.id.imgProduct);
            add_item_button = itemView.findViewById(R.id.add_item_button);
        }
    }
}
