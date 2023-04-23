package com.example.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.models.GpuModel;
import com.example.app.R;

import java.util.ArrayList;

public class GpuAdapter extends RecyclerView.Adapter<GpuAdapter.MyViewHolder> {

    Context context;
    ArrayList<GpuModel> gpuModels;

    public GpuAdapter(Context context, ArrayList<GpuModel> gpuModels) {
        this.context = context;
        this.gpuModels = gpuModels;
    }

    @NonNull
    @Override
    public GpuAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.gpu_recyclerview_row, parent, false);

        return new GpuAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GpuAdapter.MyViewHolder holder, int position) {
        holder.gpuName.setText(gpuModels.get(position).getGpuProductName());
        holder.gpuPrice.setText(gpuModels.get(position).getGpuProductPrice());
        holder.imageView.setImageResource(gpuModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return gpuModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView gpuName, gpuPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.gpu_modelImage_image);
            gpuName = itemView.findViewById(R.id.gpu_modelname_text);
            gpuPrice = itemView.findViewById(R.id.gpu_modelprice_text);
        }
    }
}
