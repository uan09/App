package com.example.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.models.MotherboardModel;
import com.example.app.R;

import java.util.ArrayList;

public class MotherboardAdapter extends RecyclerView.Adapter<MotherboardAdapter.MyViewHolder> {

    Context context;
    ArrayList<MotherboardModel> motherboardModels;

    public MotherboardAdapter(Context context, ArrayList<MotherboardModel> motherboardModels) {
        this.context = context;
        this.motherboardModels = motherboardModels;
    }

    @NonNull
    @Override
    public MotherboardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.motherboard_recyclerview_row, parent, false);

        return new MotherboardAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MotherboardAdapter.MyViewHolder holder, int position) {
        holder.motherboardName.setText(motherboardModels.get(position).getMotherboardProductName());
        holder.motherboardPrice.setText(motherboardModels.get(position).getMotherboardProductPrice());
        holder.imageView.setImageResource(motherboardModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return motherboardModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView motherboardName, motherboardPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.motherboard_modelImage_image);
            motherboardName = itemView.findViewById(R.id.motherboard_modelname_text);
            motherboardPrice = itemView.findViewById(R.id.motherboard_modelprice_text);
        }
    }
}
