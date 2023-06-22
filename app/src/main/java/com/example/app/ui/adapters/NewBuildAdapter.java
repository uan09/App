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
import com.example.app.R;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class NewBuildAdapter extends RecyclerView.Adapter<NewBuildAdapter.ProductViewHolder> {
    private Context context;
    private List<DocumentSnapshot> documents;

    public NewBuildAdapter(Context context, List<DocumentSnapshot> documents) {
        this.context = context;
        this.documents = documents;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        DocumentSnapshot document = documents.get(position);
        NumberFormat formatter = new DecimalFormat("###,###,###");

        String productType = document.getId();
        String productName = document.getString("product_name");
        String productImage = document.getString("product_image");
        String productPrice = document.getString("product_price");

        // Set the data to the views in the ViewHolder
        holder.nameTextView.setText(productName);
        holder.typeTextView.setText(productType);
        String formattedNumber = formatter.format(Long.valueOf(String.valueOf(productPrice)));
        holder.priceTextView.setText("P" + formattedNumber + ".00");
        // Load the product image using your preferred image loading library

        Glide.with(context)
                .load(productImage)
                .centerCrop()
                .into(holder.imageView);

        holder.removeItemButton.setOnClickListener(v -> {


        });
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameTextView;
        public TextView typeTextView;
        public TextView priceTextView;
        public ImageButton removeItemButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgProduct);
            nameTextView = itemView.findViewById(R.id.ProductName);
            typeTextView = itemView.findViewById(R.id.ProductType);
            priceTextView = itemView.findViewById(R.id.Price);
            removeItemButton = itemView.findViewById(R.id.remove_item_button);
        }
    }
}
