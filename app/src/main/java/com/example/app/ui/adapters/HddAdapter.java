// HddAdapter.java

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
import com.example.app.ui.models.HddModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class HddAdapter extends RecyclerView.Adapter<HddAdapter.HddViewHolder> {

    private Context context;
    private ArrayList<HddModel> hddModels;
    private FirebaseFirestore firestore;

    public HddAdapter(Context context, ArrayList<HddModel> hddModels) {
        this.context = context;
        this.hddModels = hddModels;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public HddViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hdd, parent, false);
        return new HddViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HddViewHolder holder, int position) {
        HddModel hddModel = hddModels.get(position);
        NumberFormat formatter = new DecimalFormat("###,###,###");

        String hddName = (hddModel.getProductName());
        holder.txtProductName.setText(hddName);
        String formattedNumber = formatter.format(Long.valueOf(hddModel.getProductPrice()));
        holder.txtPrice.setText("P" + formattedNumber + ".00");
        holder.txtHddInterface.setText(hddModel.getHddInterface());
        holder.txtHddCapacity.setText(hddModel.getHddCapacity());

        Glide.with(context)
                .load(hddModel.getProductImage())
                .centerCrop()
                .into(holder.imgProduct);
        holder.add_item_button.setOnClickListener(v -> {
        firestore.collection("HDD_DB")
                .document(hddName)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        DocumentSnapshot ramDocument = documentSnapshot;
                        // Save the data to the NewBuild collection under the Motherboard document
                        firestore.collection("NewBuild")
                                .document("HDD")
                                .set(ramDocument.getData())
                                .addOnSuccessListener(aVoid -> {
                                    // Data saved successfully to NewBuild collection
                                    Toast.makeText(context, "HDD Selected", Toast.LENGTH_SHORT).show();
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
        return hddModels.size();
    }

    public static class HddViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtProductName, txtPrice, txtHddInterface, txtHddCapacity;
        ImageButton add_item_button;

        public HddViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.ProductName);
            txtPrice = itemView.findViewById(R.id.Price);
            txtHddInterface = itemView.findViewById(R.id.hddInterface);
            txtHddCapacity = itemView.findViewById(R.id.hddCapacity);
            add_item_button = itemView.findViewById(R.id.add_item_button);
        }
    }
}
