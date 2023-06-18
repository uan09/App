package com.example.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.models.CoolerModel;
import com.example.app.R;

import java.util.ArrayList;

public class    CoolerAdapter extends RecyclerView.Adapter<CoolerAdapter.MyViewHolder> {

    Context context;
    ArrayList<CoolerModel> coolerModels;

    public CoolerAdapter(Context context, ArrayList<CoolerModel> coolerModels) {
        this.context = context;
        this.coolerModels = coolerModels;
    }

    @NonNull
    @Override
    public CoolerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cooler_recyclerview_row, parent, false);

        return new CoolerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoolerAdapter.MyViewHolder holder, int position) {
        holder.coolerName.setText(coolerModels.get(position).getCoolerProductName());
        holder.coolerPrice.setText(coolerModels.get(position).getCoolerProductPrice());
        holder.imageView.setImageResource(coolerModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return coolerModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView coolerName, coolerPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.cooler_modelImage_image);
            coolerName = itemView.findViewById(R.id.cooler_modelname_text);
            coolerPrice = itemView.findViewById(R.id.cooler_modelprice_text);
        }
    }
}
