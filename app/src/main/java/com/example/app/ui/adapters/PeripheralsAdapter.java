package com.example.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.models.PeripheralsModel;
import com.example.app.R;

import java.util.ArrayList;

public class PeripheralsAdapter extends RecyclerView.Adapter<PeripheralsAdapter.MyViewHolder> {

    Context context;
    ArrayList<PeripheralsModel> peripheralsModels;

    public PeripheralsAdapter(Context context, ArrayList<PeripheralsModel> peripheralsModels) {
        this.context = context;
        this.peripheralsModels = peripheralsModels;
    }

    @NonNull
    @Override
    public PeripheralsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.peripherals_recyclerview_row, parent, false);

        return new PeripheralsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeripheralsAdapter.MyViewHolder holder, int position) {
        holder.peripheralsName.setText(peripheralsModels.get(position).getPeripheralsProductName());
        holder.peripheralsPrice.setText(peripheralsModels.get(position).getPeripheralsProductPrice());
        holder.imageView.setImageResource(peripheralsModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return peripheralsModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView peripheralsName, peripheralsPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.peripherals_modelImage_image);
            peripheralsName = itemView.findViewById(R.id.peripherals_modelname_text);
            peripheralsPrice = itemView.findViewById(R.id.peripherals_modelprice_text);
        }
    }
}
