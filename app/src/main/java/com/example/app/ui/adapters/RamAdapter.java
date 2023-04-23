package com.example.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.ui.models.RamModel;

import java.util.ArrayList;

public class RamAdapter extends RecyclerView.Adapter<RamAdapter.MyViewHolder> {

    Context context;
    ArrayList<RamModel> ramModels;

    public RamAdapter(Context context, ArrayList<RamModel> ramModels) {
        this.context = context;
        this.ramModels = ramModels;
    }

    @NonNull
    @Override
    public RamAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ram_recyclerview_row, parent, false);

        return new RamAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RamAdapter.MyViewHolder holder, int position) {
        holder.ramName.setText(ramModels.get(position).getRamProductName());
        holder.ramPrice.setText(ramModels.get(position).getRamProductPrice());
        holder.imageView.setImageResource(ramModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return ramModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView ramName, ramPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ram_modelImage_image);
            ramName = itemView.findViewById(R.id.ram_modelname_text);
            ramPrice = itemView.findViewById(R.id.ram_modelprice_text);
        }
    }
}
