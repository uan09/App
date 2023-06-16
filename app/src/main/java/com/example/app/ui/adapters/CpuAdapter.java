package com.example.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app.R;
import com.example.app.ui.models.CpuModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CpuAdapter extends RecyclerView.Adapter<CpuAdapter.CpuViewHolder> {

    private Context context;
    private ArrayList<CpuModel> cpuModels;

    public CpuAdapter(Context context, ArrayList<CpuModel> cpuModels) {
        this.context = context;
        this.cpuModels = cpuModels;
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
    }

    @Override
    public int getItemCount() {
        return cpuModels.size();
    }

    public static class CpuViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtProductName, txtPrice, txtCpuCores, txtCpuSocket;

        public CpuViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.ProductName);
            txtPrice = itemView.findViewById(R.id.Price);
            txtCpuCores = itemView.findViewById(R.id.CpuCores);
            txtCpuSocket = itemView.findViewById(R.id.CpuSocket);
        }
    }
}