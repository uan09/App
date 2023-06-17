package com.example.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.models.MotherboardModel;
import com.example.app.R;

import java.util.ArrayList;

public class MotherboardAdapter extends RecyclerView.Adapter<MotherboardAdapter.MotherboardViewHolder> {
    private Context context;
    private ArrayList<MotherboardModel> motherboardModels;

    public MotherboardAdapter(Context context, ArrayList<MotherboardModel> motherboardModels) {
        this.context = context;
        this.motherboardModels = motherboardModels;
    }

    @NonNull
    @Override
    public MotherboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_motherboard, parent, false);
        return new MotherboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MotherboardViewHolder holder, int position) {
        MotherboardModel motherboardModel = motherboardModels.get(position);

        // Set the data to the corresponding views in the item layout
        holder.txtMotherboardName.setText(motherboardModel.getMotherboardName());
        holder.txtMotherboardSocket.setText(motherboardModel.getMotherboardSocket());

        // You can add any additional logic or functionality here as needed
    }

    @Override
    public int getItemCount() {
        return motherboardModels.size();
    }

    public static class MotherboardViewHolder extends RecyclerView.ViewHolder {
        TextView txtMotherboardName;
        TextView txtMotherboardSocket, txtMotherboardFormFactor;
        ImageView imgProduct;
        TextView txtProductName, txtPrice;
        ImageButton add_item_button;
        public MotherboardViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.ProductName);
            txtPrice = itemView.findViewById(R.id.Price);
            txtMotherboardFormFactor = itemView.findViewById(R.id.txtMotherboardFormFactor);
            txtMotherboardSocket = itemView.findViewById(R.id.txtMotherboardSocket);
            add_item_button = itemView.findViewById(R.id.add_item_button);
        }
    }
}
