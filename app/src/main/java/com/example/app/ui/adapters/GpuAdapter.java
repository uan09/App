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
import com.example.app.ui.models.GpuModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GpuAdapter extends RecyclerView.Adapter<GpuAdapter.GpuViewHolder> {
    private Context context;
    private ArrayList<GpuModel> gpuModels;
    private FirebaseFirestore firestore;
    private List<GpuModel> gpus;

    public GpuAdapter(Context context, ArrayList<GpuModel> gpuModels) {
        this.context = context;
        this.gpuModels = gpuModels;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public GpuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gpu, parent, false);
        return new GpuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GpuViewHolder holder, int position) {
        GpuModel gpuModel = gpuModels.get(position);
        NumberFormat formatter = new DecimalFormat("###,###,###");

        // Set the data to the corresponding views in the item layout
        String gpuName = (gpuModel.getProduct_name());
        holder.txtProductName.setText(gpuName);
        holder.txtGpuMemoryType.setText(gpuModel.getGPU__Memory_Type());
        holder.txtGpuChipset.setText(gpuModel.getGPU_Chipset());
        String formattedNumber = formatter.format(Long.valueOf(gpuModel.getProduct_price()));
        holder.txtPrice.setText("P" + formattedNumber + ".00");
        Glide.with(context)
                .load(gpuModel.getProduct_image())
                .centerCrop()
                .into(holder.imgProduct);

        holder.add_item_button.setOnClickListener(v -> {
            Map<String, Object> productData = new HashMap<>();
            productData.put("product_name", gpuModel.getProduct_name());
            productData.put("product_image", gpuModel.getProduct_image());
            productData.put("product_price", gpuModel.getProduct_price());
            productData.put("GPU__Memory_Type", gpuModel.getGPU__Memory_Type());
            productData.put("GPU_Chipset", gpuModel.getGPU_Chipset());

            // Find the document in the Motherboard_DB collection using the document ID
            firestore.collection("GPU_DB")
                    .document(gpuName)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            DocumentSnapshot gpuDocument = documentSnapshot;
                            // Save the data to the NewBuild collection under the Motherboard document
                            firestore.collection("NewBuild")
                                    .document("Graphics Card")
                                    .set(productData)
                                    .addOnSuccessListener(aVoid -> {
                                        // Data saved successfully to NewBuild collection
                                        Toast.makeText(context, "Graphics Card Selected", Toast.LENGTH_SHORT).show();
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
        return gpuModels.size();
    }

    public void setGPUModels(List<GpuModel> gpus) {
        this.gpus = gpus;
        notifyDataSetChanged();
    }

    public static class GpuViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName;
        TextView txtGpuMemoryType;
        TextView txtGpuChipset;
        ImageView imgProduct;
        TextView txtPrice;
        ImageButton add_item_button;

        public GpuViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.ProductName);
            txtPrice = itemView.findViewById(R.id.Price);
            txtGpuMemoryType = itemView.findViewById(R.id.GpuMemoryType);
            txtGpuChipset = itemView.findViewById(R.id.GpuChipset);
            add_item_button = itemView.findViewById(R.id.add_item_button);
        }
    }
}