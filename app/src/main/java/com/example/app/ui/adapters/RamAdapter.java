package com.example.app.ui.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.app.NewBuildActivity;
import com.example.app.R;
import com.example.app.ui.models.RamModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class RamAdapter extends RecyclerView.Adapter<RamAdapter.RamViewHolder> {
    private Context context;
    private ArrayList<RamModel> ramModels;
    private FirebaseFirestore firestore;
    public RamAdapter(Context context, ArrayList<RamModel> ramModels) {
        this.context = context;
        this.ramModels = ramModels;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public RamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ram, parent, false);
        return new RamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RamViewHolder holder, int position) {
        RamModel ramModel = ramModels.get(position);
        NumberFormat formatter = new DecimalFormat("###,###,###");

        // Set the data to the corresponding views in the item layout
        String ramName = (ramModel.getMemoryName());
        holder.txtMemoryName.setText(ramName);
        holder.txtMemoryType.setText(ramModel.getMemoryType());
        holder.txtMemoryCapacity.setText(ramModel.getMemoryCapacity());
        String formattedNumber = formatter.format(Long.valueOf(ramModel.getProductPrice()));
        holder.txtPrice.setText("P" + formattedNumber + ".00");
        Glide.with(context)
                .load(ramModel.getProductImage())
                .centerCrop()
                .into(holder.imgProduct);
        holder.add_item_button.setOnClickListener(v -> {
        firestore.collection("RAM_DB")
                .document(ramName)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        DocumentSnapshot ramDocument = documentSnapshot;
                        // Save the data to the NewBuild collection under the Motherboard document
                        firestore.collection("NewBuild")
                                .document("RAM")
                                .set(ramDocument.getData())
                                .addOnSuccessListener(aVoid -> {
                                    // Data saved successfully to NewBuild collection
                                    Toast.makeText(context, "RAM Selected", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, NewBuildActivity.class);
                                    context.startActivity(intent);
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
        return ramModels.size();
    }

    public static class RamViewHolder extends RecyclerView.ViewHolder {
        TextView txtMemoryName, txtMemoryType, txtMemoryCapacity, txtPrice;
        ImageView imgProduct;
        ImageButton add_item_button;

        public RamViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtMemoryName = itemView.findViewById(R.id.MemoryName);
            txtMemoryType = itemView.findViewById(R.id.MemoryType);
            txtMemoryCapacity = itemView.findViewById(R.id.RamCapacity);
            txtPrice = itemView.findViewById(R.id.Price);
            add_item_button = itemView.findViewById(R.id.add_item_button);
        }
    }
}