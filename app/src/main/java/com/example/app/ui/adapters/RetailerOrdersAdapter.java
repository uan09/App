package com.example.app.ui.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.Retailer_OrderDetailsActivity;
import com.example.app.ui.models.OrderModel;

import java.util.List;

public class RetailerOrdersAdapter extends RecyclerView.Adapter<RetailerOrdersAdapter.ViewHolder> {

    private List<OrderModel> orderList;

    public RetailerOrdersAdapter(List<OrderModel> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_retailer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel order = orderList.get(position);

        holder.orderNumber.setText("Order ID: "+ order.getOrderId());
        holder.orderEmail.setText("Email: " + order.getEmail());
        holder.orderStatus.setText("Status: " + order.getStatus());
        holder.orderTotalPrice.setText(order.getTotal_price());

        // Set the order status text and color based on its value
        String status = order.getStatus();
        holder.orderStatus.setText("Status: " + status);

        int textColor = R.color.white;  // Default color for "Pending" status
        if (status.equals("Accepted")) {
            textColor = R.color.green;  // Change color to green for "Accepted" status
        } else if (status.equals("Declined")) {
            textColor = R.color.red;  // Change color to red for "Declined" status
        } else if (status.equals("Pending")) {
            textColor = R.color.white;
        }
        holder.orderStatus.setTextColor(holder.itemView.getResources().getColor(textColor));

        holder.check_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderNumber = order.getOrderId();

                // Create an Intent to start the new activity
                Intent intent = new Intent(v.getContext(), Retailer_OrderDetailsActivity.class);

                // Pass the order details to the new activity
                intent.putExtra("orderID", orderNumber);

                // Start the new activity
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderNumber, orderEmail, orderTotalPrice, orderStatus;
        ImageButton check_order;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNumber = itemView.findViewById(R.id.order_number);
            orderEmail = itemView.findViewById(R.id.order_email);
            orderStatus = itemView.findViewById(R.id.order_status);
            orderTotalPrice = itemView.findViewById(R.id.order_total);
            check_order = itemView.findViewById(R.id.check_order);
        }
    }
}

