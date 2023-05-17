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
import com.example.app.User_OrderDetailsActivity;
import com.example.app.ui.models.OrderModel;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private List<OrderModel> orderList;

    public OrdersAdapter(List<OrderModel> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel order = orderList.get(position);

        holder.orderNumber.setText("Order ID: "+ order.getOrderId());
        holder.orderEmail.setText("Email: " + order.getEmail());
        holder.orderStatus.setText("Status: " + order.getStatus());
        holder.orderTotalPrice.setText(order.getTotal_price());
        holder.check_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderNumber = order.getOrderId();

                // Create an Intent to start the new activity
                Intent intent = new Intent(v.getContext(), User_OrderDetailsActivity.class);

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

