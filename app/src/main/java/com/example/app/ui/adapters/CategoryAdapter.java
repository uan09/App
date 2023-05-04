package com.example.app.ui.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.CategoryActivity;
import com.example.app.R;
import com.example.app.ui.models.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder viewHolder, int position) {
        int icon = categoryModelList.get(position).getIcon();
        String name = categoryModelList.get(position).getCategoryName();
        String type = categoryModelList.get(position).getCategoryType();
        viewHolder.setCategory(name, type);
        viewHolder.categoryIcon.setImageResource(icon);
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView categoryIcon;
        private TextView categoryName, categoryType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.category_icon);
            categoryName = itemView.findViewById(R.id.category_name);
            categoryType = itemView.findViewById(R.id.category_type);
        }

        private void setCategory(final String name, String type) {
           categoryName.setText(name);
           categoryType.setText(type);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent categoryIntent = new Intent(itemView.getContext(), CategoryActivity.class);
                   categoryIntent.putExtra("CategoryName", name);
                   categoryIntent.putExtra("CategoryType", type);
                   itemView.getContext().startActivity(categoryIntent);
               }
           });

        }
    }
}
