package com.example.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app.R;
import com.example.app.ui.models.MotherboardModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class MotherboardAdapter extends RecyclerView.Adapter<MotherboardAdapter.MotherboardViewHolder> {
    private Context context;
    private ArrayList<MotherboardModel> motherboardModels;
    private FirebaseFirestore firestore;

    public MotherboardAdapter(Context context, ArrayList<MotherboardModel> motherboardModels) {
        this.context = context;
        this.motherboardModels = motherboardModels;
        this.firestore = FirebaseFirestore.getInstance();
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
        String motherboardName = motherboardModel.getMotherboardName();
        holder.txtMotherboardName.setText(motherboardName);
        holder.txtMotherboardSocket.setText(motherboardModel.getMotherboardSocket());
        holder.txtMotherboardFormFactor.setText(motherboardModel.getMotherboardFormFactor());
        holder.txtMotherboardMemoryType.setText(motherboardModel.getMotherboardMemoryType());
        String formattedNumber = formatter.format(Long.valueOf(motherboardModel.getProduct_price()));
        holder.txtPrice.setText("P" + formattedNumber + ".00");
        Glide.with(context)
                .load(motherboardModel.getProduct_image())
                .centerCrop()
                .into(holder.imgProduct);

        // Set OnClickListener to the add_item_button
        holder.add_item_button.setOnClickListener(v -> {
            // Find the document in the Motherboard_DB collection using the document ID
            firestore.collection("Motherboard_DB")
                    .document(motherboardName)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            DocumentReference motherboardDocumentRef = documentSnapshot.getReference();

                            // Save the data to the TempItems collection under the Temp document
                            firestore.collection("TempItems")
                                    .document("Temp")
                                    .set(motherboardDocumentRef.get())
                                    .addOnSuccessListener(aVoid -> {
                                        // Data saved successfully to TempItems collection
                                        Toast.makeText(context, "Data saved to TempItems collection", Toast.LENGTH_SHORT).show();

                                        // Create an intent to start the NewBuildActivity
                                    })
                                    .addOnFailureListener(e -> {
                                        // Handle any errors that occurred while saving data to TempItems collection
                                        Toast.makeText(context, "Failed to save data to TempItems collection", Toast.LENGTH_SHORT).show();
                                    });

                            // Save the data to the NewBuild collection under the Motherboard document
                            firestore.collection("NewBuild")
                                    .document("Motherboard")
                                    .set(motherboardDocumentRef.get())
                                    .addOnSuccessListener(aVoid -> {
                                        // Data saved successfully to NewBuild collection under Motherboard document
                                        Toast.makeText(context, "Data saved to NewBuild collection", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Handle any errors that occurred while saving data to NewBuild collection
                                        Toast.makeText(context, "Failed to save data to NewBuild collection", Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            // No matching document found in the Motherboard_DB collection
                            Toast.makeText(context, "No matching motherboard found", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Handle any errors that occurred while fetching the document from Motherboard_DB collection
                        Toast.makeText(context, "Failed to fetch document from Motherboard_DB collection", Toast.LENGTH_SHORT).show();
                    });
        });
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
