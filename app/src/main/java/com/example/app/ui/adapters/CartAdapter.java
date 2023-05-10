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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartModel> cartList;
    private final Context context;

    public CartAdapter(List<CartModel> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartModel cart = cartList.get(position);
        holder.productName.setText(cart.getProduct_name());
        holder.productType.setText(cart.getProduct_type());
        holder.productPrice.setText(cart.getProduct_price());
        holder.productNumber.setText(cart.getProduct_number());
        String firstImageUrl = cart.getFirst_image_url();
        Glide.with(context).load(firstImageUrl).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productType, productPrice, productNumber;
        ImageView productImage;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.cart_product_name);
            productType = itemView.findViewById(R.id.cart_product_type);
            productPrice = itemView.findViewById(R.id.cart_product_price);
            productNumber = itemView.findViewById(R.id.cart_product_number);
            productImage = itemView.findViewById(R.id.cart_product_image);
        }
    }
}
