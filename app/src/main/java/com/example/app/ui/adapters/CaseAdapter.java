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
import com.example.app.ui.models.CaseModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.ViewHolder> {

    private Context context;
    private FirebaseFirestore firestore;
    private ArrayList<CaseModel> caseModels;

    public CaseAdapter(Context context, ArrayList<CaseModel> caseModels) {
        this.context = context;
        this.caseModels = caseModels;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pc_case, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CaseModel caseModel = caseModels.get(position);
        NumberFormat formatter = new DecimalFormat("###,###,###");

        String caseName = (caseModel.getProductName());
        holder.caseName.setText(caseName);
        String formattedNumber = formatter.format(Long.valueOf(caseModel.getProductPrice()));
        holder.casePrice.setText("P" + formattedNumber + ".00");
        holder.caseType.setText(caseModel.getCaseType());
        holder.caseFormFactor.setText(caseModel.getCaseFormFactor());


        // Load product image using Glide or any other image loading library
        Glide.with(context)
                .load(caseModel.getProductImage())
                .centerCrop()
                .into(holder.caseImage);
        holder.add_item_button.setOnClickListener(v -> {
            firestore.collection("PC_Case_DB")
                    .document(caseName)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            DocumentSnapshot ramDocument = documentSnapshot;
                            // Save the data to the NewBuild collection under the Motherboard document
                            firestore.collection("NewBuild")
                                    .document("Power Supply Unit")
                                    .set(ramDocument.getData())
                                    .addOnSuccessListener(aVoid -> {
                                        // Data saved successfully to NewBuild collection
                                        Toast.makeText(context, "PC Case Selected", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Handle any errors that occurred while saving data to NewBuild collection
                                    });
                        } else {
                            // No matching document found in the Motherboard_DB collection
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Handle any errors that occurred while fetching the document from Motherboard_DB collection
                    });
        });
    }

    @Override
    public int getItemCount() {
        return caseModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView caseImage;
        TextView caseName;
        TextView caseType;
        TextView caseFormFactor;
        TextView casePrice;
        ImageButton add_item_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            caseImage = itemView.findViewById(R.id.imgProduct);
            caseName = itemView.findViewById(R.id.ProductName);
            caseType = itemView.findViewById(R.id.CaseType);
            caseFormFactor = itemView.findViewById(R.id.CaseFormFactor);
            casePrice = itemView.findViewById(R.id.Price);
            add_item_button = itemView.findViewById(R.id.add_item_button);
        }

    }
}
