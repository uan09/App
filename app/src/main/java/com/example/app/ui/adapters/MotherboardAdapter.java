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

import com.bumptech.glide.Glide;
import com.example.app.ui.models.MotherboardModel;
import com.example.app.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
        NumberFormat formatter = new DecimalFormat("###,###,###");

        // Set the data to the corresponding views in the item layout
        holder.txtMotherboardName.setText(motherboardModel.getMotherboardName());
        holder.txtMotherboardSocket.setText(motherboardModel.getMotherboardSocket());
        holder.txtMotherboardFormFactor.setText(motherboardModel.getMotherboardFormFactor());
        holder.txtMotherboardMemoryType.setText(motherboardModel.getMotherboardMemoryType());
        String formattedNumber = formatter.format(Long.valueOf(motherboardModel.getProduct_price()));
        holder.txtPrice.setText("P"+formattedNumber+".00");
        Glide.with(context)
                .load(motherboardModel.getProduct_image())
                .centerCrop()
                .into(holder.imgProduct);
        // You can add any additional logic or functionality here as needed
    }

    @Override
    public int getItemCount() {
        return motherboardModels.size();
    }

    public static class MotherboardViewHolder extends RecyclerView.ViewHolder {
        TextView txtMotherboardName;
        TextView txtMotherboardSocket, txtMotherboardFormFactor, txtMotherboardMemoryType;
        ImageView imgProduct;
        TextView txtPrice;
        ImageButton add_item_button;
        public MotherboardViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtMotherboardName = itemView.findViewById(R.id.ProductName);
            txtPrice = itemView.findViewById(R.id.Price);
            txtMotherboardFormFactor = itemView.findViewById(R.id.MotherboardFormFactor);
            txtMotherboardSocket = itemView.findViewById(R.id.MotherboardSocket);
            txtMotherboardMemoryType = itemView.findViewById(R.id.MemoryType);
            add_item_button = itemView.findViewById(R.id.add_item_button);
        }
    }
}
