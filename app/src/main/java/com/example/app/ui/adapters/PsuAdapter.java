package com.example.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.models.PsuModel;
import com.example.app.R;

import java.util.ArrayList;

public class PsuAdapter extends RecyclerView.Adapter<PsuAdapter.MyViewHolder>{

    Context context;
    ArrayList<PsuModel> psuModels;

    public PsuAdapter(Context context, ArrayList<PsuModel> psuModels) {
        this.context = context;
        this.psuModels = psuModels;
    }

    @NonNull
    @Override
    public PsuAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.psu_recyclerview_row, parent, false);

        return new PsuAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PsuAdapter.MyViewHolder holder, int position) {
        holder.psuName.setText(psuModels.get(position).getPsuProductName());
        holder.psuPrice.setText(psuModels.get(position).getPsuProductPrice());
        holder.imageView.setImageResource(psuModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return psuModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView psuName, psuPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.psu_modelImage_image);
            psuName = itemView.findViewById(R.id.psu_modelname_text);
            psuPrice = itemView.findViewById(R.id.psu_modelprice_text);
        }
    }
}
