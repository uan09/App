package com.example.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app.R;
import com.example.app.ui.models.CartModel;

import java.util.List;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.ViewHolder> {

    private List<CartModel> orderItems;
    private final Context context;

    public OrderItemsAdapter(List<CartModel> orderItems, Context context) {
        this.orderItems = orderItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartModel orderItem = orderItems.get(position);
        holder.productName.setText(orderItem.getProduct_name());
        holder.productType.setText(orderItem.getProduct_type());
        holder.productPrice.setText(orderItem.getProduct_price());
        holder.productNumber.setText(orderItem.getProduct_number());
        Glide.with(context)
                .load(orderItem.getProduct_image_url())
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productType, productPrice, productNumber;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.cart_product_name);
            productType = itemView.findViewById(R.id.cart_product_type);
            productPrice = itemView.findViewById(R.id.cart_product_price);
            productNumber = itemView.findViewById(R.id.cart_product_number);
            productImage = itemView.findViewById(R.id.cart_product_image);
        }
    }
}