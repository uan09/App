package com.example.app.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.ui.notifications.NotificationGetterSetter;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private ArrayList<NotificationGetterSetter> notifArrayList;
    private Context context;
    public NotificationAdapter(Context context, ArrayList<NotificationGetterSetter> arrayList) {
        this.context = context;
        this.notifArrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shownotification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationGetterSetter modal = notifArrayList.get(position);
        holder.notifDetail.setText(modal.getNotificationDetails());

        // Load the image using Base64 decoding
        Bitmap bitmap = getNotificationImage(modal.getNotificationImage());
        holder.notifImage.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return notifArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView notifDetail;
        ImageView notifImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notifDetail = itemView.findViewById(R.id.notifDetailTextView);
            notifImage = itemView.findViewById(R.id.notifImageView);
        }
    }

    private Bitmap getNotificationImage(String encodedImage) {
        byte[] decodedBytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}