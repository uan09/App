// SsdAdapter.java

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
import com.example.app.ui.models.SsdModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SsdAdapter extends RecyclerView.Adapter<SsdAdapter.SsdViewHolder> {

    private Context context;
    private ArrayList<SsdModel> ssdModels;
    private FirebaseFirestore firestore;
    private List<SsdModel> ssds;

    public SsdAdapter(Context context, ArrayList<SsdModel> ssdModels) {
        this.context = context;
        this.ssdModels = ssdModels;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public SsdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ssd, parent, false);
        return new SsdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SsdViewHolder holder, int position) {
        SsdModel ssdModel = ssdModels.get(position);

        NumberFormat formatter = new DecimalFormat("###,###,###");
        String ssdName = (ssdModel.getProduct_name());
        holder.txtProductName.setText(ssdName);
        String formattedNumber = formatter.format(Long.valueOf(ssdModel.getProduct_price()));
        holder.txtPrice.setText("P" + formattedNumber + ".00");
        holder.txtSsdInterface.setText(ssdModel.getSSD_Interface());
        holder.txtSsdCapacity.setText(ssdModel.getSSD_Capacity());

        Glide.with(context)
                .load(ssdModel.getProduct_image())
                .centerCrop()
                .into(holder.imgProduct);

        holder.add_item_button.setOnClickListener(v -> {

            Map<String, Object> productData = new HashMap<>();
            productData.put("product_name", ssdModel.getProduct_name());
            productData.put("product_image", ssdModel.getProduct_image());
            productData.put("product_price", ssdModel.getProduct_price());
            productData.put("SSD_Interface", ssdModel.getSSD_Interface());
            productData.put("SSD_Capacity", ssdModel.getSSD_Capacity());

            firestore.collection("SSD_DB")
                    .document(ssdName)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            DocumentSnapshot ramDocument = documentSnapshot;
                            // Save the data to the NewBuild collection under the Motherboard document
                            firestore.collection("NewBuild")
                                    .document("SSD")
                                    .set(productData)
                                    .addOnSuccessListener(aVoid -> {
                                        // Data saved successfully to NewBuild collection
                                        Toast.makeText(context, "SSD Selected", Toast.LENGTH_SHORT).show();
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
        return ssdModels.size();
    }

    public void setSSDModels(List<SsdModel> ssds) {
        this.ssds = ssds;
        notifyDataSetChanged();
    }

    public static class SsdViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtProductName, txtPrice, txtSsdInterface, txtSsdCapacity;
        ImageButton add_item_button;

        public SsdViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.ProductName);
            txtPrice = itemView.findViewById(R.id.Price);
            txtSsdInterface = itemView.findViewById(R.id.SSDInterface);
            txtSsdCapacity = itemView.findViewById(R.id.SSDCapacity);
            add_item_button = itemView.findViewById(R.id.add_item_button);
        }
    }
}
