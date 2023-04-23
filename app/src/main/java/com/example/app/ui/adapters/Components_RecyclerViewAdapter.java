package com.example.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.ui.models.ComponentsModel;

import java.util.ArrayList;

public class Components_RecyclerViewAdapter extends RecyclerView.Adapter<Components_RecyclerViewAdapter.MyViewHolder> {

    Context context; //Needed to inflate the layout
    ArrayList<ComponentsModel> componentsModels; //Holds over the component models
    OnComponentListener onComponentListener;

    //This constructor gets the values of the initiated variables
    public Components_RecyclerViewAdapter(Context context, ArrayList<ComponentsModel> componentsModels, OnComponentListener onComponentListener) {
        this.context = context;
        this.componentsModels = componentsModels;
        this.onComponentListener = onComponentListener;
    }

    @NonNull
    @Override
    //onCreateViewHolder giving layout for each of the rows
    public Components_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflates the layout (Giving the rows a look)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_rows, parent, false);

        return new Components_RecyclerViewAdapter.MyViewHolder(view, onComponentListener);
    }

    @Override
    //onBindViewHolder changes the data based on the recyclerview position of each items
    public void onBindViewHolder(@NonNull Components_RecyclerViewAdapter.MyViewHolder holder, int position) {
        //Binds the process or assigns values to the views created in the recyclerview_rows layout file
        //It bases on the position of the recycler view
        holder.textName.setText(componentsModels.get(position).getNewBuilds());
        holder.imageView.setImageResource(componentsModels.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        //Recycler view wanting to know the number of items to be displayed
        //Helps onBindViewHolder so it knows what data to update to the views
        return componentsModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Grabs the views from the recyclerview_row layout file
        //Similar to onCreate method

        ImageView imageView;
        TextView textName;

        OnComponentListener onComponentListener;

        public MyViewHolder(@NonNull View itemView, OnComponentListener onComponentListener) {
            super(itemView);
            //Finds and gets the name and image of the values
            imageView = itemView.findViewById(R.id.modelImage_image);
            textName = itemView.findViewById(R.id.modelname_text);
            this.onComponentListener = onComponentListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onComponentListener.onComponentClick(getAdapterPosition());
        }
    }

    public interface OnComponentListener {
        void onComponentClick(int position);
    }
}
