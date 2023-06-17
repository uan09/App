package com.example.app.ui.adapters;

import android.content.Context;
import android.util.Log;
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
import com.example.app.ui.models.CpuModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CpuAdapter extends RecyclerView.Adapter<CpuAdapter.CpuViewHolder> {

    private Context context;
    private ArrayList<CpuModel> cpuModels;
    private FirebaseFirestore firestore;
    private String email;

    public CpuAdapter(Context context, ArrayList<CpuModel> cpuModels) {
        this.context = context;
        this.cpuModels = cpuModels;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public CpuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cpu, parent, false);
        return new CpuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CpuViewHolder holder, int position) {
        CpuModel cpuModel = cpuModels.get(position);

        NumberFormat formatter = new DecimalFormat("###,###,###");
        holder.txtProductName.setText(cpuModel.getProduct_name());
        String formattedNumber = formatter.format(Long.valueOf(cpuModel.getProduct_price()));
        holder.txtPrice.setText("P"+formattedNumber+".00");
        holder.txtCpuCores.setText(cpuModel.getCpu_Cores());
        holder.txtCpuSocket.setText(cpuModel.getCpu_Socket());

        // Load the product image using Picasso or any other image loading library
        Glide.with(context)
                .load(cpuModel.getProduct_image())
                .centerCrop()
                .into(holder.imgProduct);

        holder.add_item_button.setOnClickListener(v -> {
            // Save CPU_Socket to TempItems collection
            saveCpuSocketToTempItems(cpuModel.getCpu_Socket());

            // Save the entire product under the Processor document in NewBuild collection
            saveProductToNewBuild(cpuModel);
        });
    }

    @Override
    public int getItemCount() {
        return cpuModels.size();
    }
    private void saveProductToNewBuild(CpuModel cpuModel) {
        Map<String, Object> productData = new HashMap<>();
        productData.put("product_name", cpuModel.getProduct_name());
        productData.put("product_image", cpuModel.getProduct_image());
        productData.put("product_price", cpuModel.getProduct_price());
        productData.put("CPU_Cores", cpuModel.getCpu_Cores());
        productData.put("CPU_Socket", cpuModel.getCpu_Socket());

        firestore.collection("NewBuild")
                .document("Processor")
                .set(productData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Product saved to NewBuild", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to save product to NewBuild", Toast.LENGTH_SHORT).show();
                    Log.e("CpuAdapter", "Error saving product to NewBuild", e);
                });
    }


    private void saveCpuSocketToTempItems(String cpuSocket) {
        Map<String, Object> tempItemData = new HashMap<>();
        tempItemData.put("CPU_Socket", cpuSocket);

        firestore.collection("TempItems")
                .document("Temp")
                .set(tempItemData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "CPU Socket saved to TempItems", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to save CPU Socket to TempItems", Toast.LENGTH_SHORT).show();
                    Log.e("CpuAdapter", "Error saving CPU Socket to TempItems", e);
                });
    }
    public static class CpuViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtProductName, txtPrice, txtCpuCores, txtCpuSocket;
        ImageButton add_item_button;

        public CpuViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.ProductName);
            txtPrice = itemView.findViewById(R.id.Price);
            txtCpuCores = itemView.findViewById(R.id.CpuCores);
            txtCpuSocket = itemView.findViewById(R.id.CpuSocket);
            add_item_button = itemView.findViewById(R.id.add_item_button);
        }
    }
}