package com.example.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.models.HddModel;
import com.example.app.R;

import java.util.ArrayList;

public class HddAdapter extends RecyclerView.Adapter<HddAdapter.MyViewHolder> {

    Context context;
    ArrayList<HddModel> hddModels;

    public HddAdapter(Context context, ArrayList<HddModel> hddModels) {
        this.context = context;
        this.hddModels = hddModels;
    }

    @NonNull
    @Override
    public HddAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.hdd_recyclerview_row, parent, false);

        return new HddAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HddAdapter.MyViewHolder holder, int position) {
        holder.hddName.setText(hddModels.get(position).getHddProductName());
        holder.hddPrice.setText(hddModels.get(position).getHddProductPrice());
        holder.imageView.setImageResource(hddModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return hddModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView hddName, hddPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.hdd_modelImage_image);
            hddName = itemView.findViewById(R.id.hdd_modelname_text);
            hddPrice = itemView.findViewById(R.id.hdd_modelprice_text);
        }
    }
}
