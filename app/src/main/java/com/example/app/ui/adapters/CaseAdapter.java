package com.example.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.models.CaseModel;
import com.example.app.R;

import java.util.ArrayList;

public class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.MyViewHolder>{

    Context context;
    ArrayList<CaseModel> caseModels;

    public CaseAdapter(Context context, ArrayList<CaseModel> caseModels) {
        this.context = context;
        this.caseModels = caseModels;
    }

    @NonNull
    @Override
    public CaseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pccase_recyclerview_row, parent, false);

        return new CaseAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaseAdapter.MyViewHolder holder, int position) {
        holder.caseName.setText(caseModels.get(position).getCaseProductName());
        holder.casePrice.setText(caseModels.get(position).getCaseProductPrice());
        holder.imageView.setImageResource(caseModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return caseModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView caseName, casePrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.pccase_modelImage_image);
            caseName = itemView.findViewById(R.id.pccase_modelname_text);
            casePrice = itemView.findViewById(R.id.pccase_modelprice_text);
        }
    }
}
