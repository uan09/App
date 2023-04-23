package com.example.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.models.CpuModel;
import com.example.app.R;

import java.util.ArrayList;

public class CpuAdapter extends RecyclerView.Adapter<CpuAdapter.MyViewHolder> {

    Context context;
    ArrayList<CpuModel> cpuModels;

    public CpuAdapter(Context context, ArrayList<CpuModel> cpuModels) {
        this.context = context;
        this.cpuModels = cpuModels;
    }

    @NonNull
    @Override
    public CpuAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cpu_recyclerview_row, parent, false);

        return new CpuAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CpuAdapter.MyViewHolder holder, int position) {
        holder.cpuName.setText(cpuModels.get(position).getCpuProductName());
        holder.cpuPrice.setText(cpuModels.get(position).getCpuProductPrice());
        holder.imageView.setImageResource(cpuModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return cpuModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView cpuName, cpuPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.cpu_modelImage_image);
            cpuName = itemView.findViewById(R.id.cpu_modelname_text);
            cpuPrice = itemView.findViewById(R.id.cpu_modelprice_text);
        }
    }
}
