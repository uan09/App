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
import com.example.app.ui.models.SsdModel;

import java.util.ArrayList;

public class SsdAdapter extends RecyclerView.Adapter<SsdAdapter.MyViewHolder>{

    Context context;
    ArrayList<SsdModel> ssdModels;

    public SsdAdapter(Context context, ArrayList<SsdModel> ssdModels) {
        this.context = context;
        this.ssdModels = ssdModels;
    }

    @NonNull
    @Override
    public SsdAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ssd_recyclerview_row, parent, false);

        return new SsdAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SsdAdapter.MyViewHolder holder, int position) {
        holder.ssdName.setText(ssdModels.get(position).getSsdProductName());
        holder.ssdPrice.setText(ssdModels.get(position).getSsdProductPrice());
        holder.imageView.setImageResource(ssdModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return ssdModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView ssdName, ssdPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ssd_modelImage_image);
            ssdName = itemView.findViewById(R.id.ssd_modelname_text);
            ssdPrice = itemView.findViewById(R.id.ssd_modelprice_text);
        }
    }
}
