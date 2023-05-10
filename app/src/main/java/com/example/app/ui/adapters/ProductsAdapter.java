package com.example.app.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Html;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app.R;
import com.example.app.ui.models.MainViewModel;
import com.example.app.ui.models.ProductModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {
    private final Context context;
    private final List<ProductModel> productList;
    private OnItemClickListener mOnItemClickListener;
    TextView product_empty;
    MainViewModel mainViewModel;
    boolean isEnable = false, isSelectAll = false;
    ArrayList<String> selectList = new ArrayList<>();
    ArrayList<MyViewHolder> holders = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference productsRef = db.collection("Products");
    String layout_style = "0";


    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, ProductModel obj, int position);

    }

    public ProductsAdapter(List<ProductModel> productList, Context context, TextView product_empty, CollectionReference productsRef, String layout_style) {
        this.context = context;
        this.productList = productList;
        this.product_empty = product_empty;
        this.productsRef = productsRef;
        this.layout_style = layout_style;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void add(ProductModel product) {
        productList.add(product);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_retailer_product_view,parent, false);
        mainViewModel = ViewModelProviders.of((FragmentActivity)context).get(MainViewModel.class);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NumberFormat formatter = new DecimalFormat("###,###,###");
        holders.add(holder);
        ProductModel product = productList.get(position);
        holder.product_name.setText(product.getProduct_name());
        holder.product_quantity.setText("Quantity: "+product.getProduct_quantity());
        String formattedNumber = formatter.format(Long.valueOf(product.getProduct_price()));
        holder.product_price.setText("P"+formattedNumber+".00");
        String firstImageUrl = product.getProduct_image().get(0);
        Glide.with(context).load(firstImageUrl).centerCrop().into(holder.Product_image);
        holder.lyt_parent.setOnClickListener(view -> {
            if (mOnItemClickListener != null && !isEnable) {
                mOnItemClickListener.onItemClick(view, productList.get(position), position);
                holder.lyt_parent.setBackgroundColor(context.getResources().getColor(R.color.mustard));
            } else if (isEnable) {
                // Check if item is already selected
                boolean isSelected = selectList.contains(product.getProduct_id());
                // Update selection state
                if (isSelected) {
                    selectList.remove(product.getProduct_id());
                    holder.lyt_parent.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    selectList.add(product.getProduct_id());
                    holder.lyt_parent.setBackgroundColor(context.getResources().getColor(R.color.mustard));
                }
                mainViewModel.setText(String.valueOf(selectList.size()));
            }
        });
        holder.lyt_parent.setOnLongClickListener(view -> {
            if (!isEnable) {
                ActionMode.Callback callback = new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                        MenuInflater menuInflater = actionMode.getMenuInflater();
                        menuInflater.inflate(R.menu.menu,menu);

                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                        isEnable = true;
                        ClickItem(holder);
                        
                        mainViewModel.getText().observe((LifecycleOwner) context, s -> {
                            if (selectList.size() > 0) {
                                actionMode.setTitle(Html.fromHtml(String.format("<font color='#FFA500'>%s Selected</font>", s), Html.FROM_HTML_MODE_LEGACY));
                            } else {
                                // No items selected, remove title
                                actionMode.setTitle(null);
                            }
                        });
                        return true;
                    }

                    @SuppressLint({"NonConstantResourceId"})
                    @Override
                    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.menu_delete:
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Delete selected items")
                                        .setMessage("Are you sure you want to delete the selected items?")
                                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // User clicked Delete button, delete selected items
                                                for (String productId : selectList) {
                                                    // Access the document reference for the selected item and delete it
                                                    db.collection("Products").document(productId).delete();
                                                    for (int i = 0; i < productList.size(); i++) {
                                                        if (productList.get(i).getProduct_id().equals(productId)) {
                                                            actionMode.finish();
                                                            productList.remove(i);
                                                            Toast.makeText(context, "Product/s deleted", Toast.LENGTH_SHORT).show();
                                                            break;
                                                        }
                                                    }
                                                }
                                                // Clear the selection state and finish the action mode
                                                selectList.clear();
                                                notifyDataSetChanged(); // Refresh the adapter
                                                mainViewModel.setText(String.valueOf(selectList.size()));
                                                product_empty.setVisibility(productList.isEmpty() ? View.VISIBLE : View.GONE);
                                                dialog.dismiss();
                                            }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // User cancelled the dialog, do nothing
                                            }
                                        });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                break;

                            case R.id.menu_select_all:
                                if (selectList.size() == productList.size()) {
                                    // All items already selected, do nothing
                                    break;
                                } else {
                                    selectList.clear();
                                    for (ProductModel product : productList) {
                                        // Only add ID if it is not already selected
                                        if (!selectList.contains(product.getProduct_id())) {
                                            selectList.add(product.getProduct_id());
                                        }
                                    }
                                    isSelectAll = true;
                                    for (MyViewHolder holder : holders) {
                                        holder.lyt_parent.setBackgroundColor(context.getResources().getColor(R.color.mustard));
                                    }
                                }
                                if (selectList.size() == 0) {
                                    // No items selected, exit action mode
                                    actionMode.finish();
                                    return true;
                                }
                                mainViewModel.setText(String.valueOf(selectList.size()));
                                break;
                        }
                        if (selectList.size() == 0) {
                            // No items selected, exit action mode
                            actionMode.finish();
                            return true;
                        }
                        return true;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode actionMode) {
                        isEnable = false;
                        isSelectAll = false;
                        selectList.clear();
                        for (MyViewHolder holder : holders) {
                            holder.lyt_parent.setBackgroundColor(Color.TRANSPARENT);
                        }
                    }
                };
                ((AppCompatActivity) view.getContext()).startActionMode(callback);
            } else {
                ClickItem(holder);
            }
            return true;
        });

        holder.itemView.setOnClickListener(view -> {
            if (isEnable) {
                ClickItem(holder);
            } else {
                mOnItemClickListener.onItemClick(view, productList.get(position), position);
            }
        });
    }

    private void ClickItem(MyViewHolder holder) {
        String product_id = productList.get(holder.getAdapterPosition()).getProduct_id();
        if (holder.checkbox.getVisibility() == View.GONE) {
            holder.checkbox.setVisibility(View.VISIBLE);
            holder.lyt_parent.setBackgroundColor(context.getResources().getColor(R.color.mustard));
            selectList.add(product_id);
        } else {
            holder.checkbox.setVisibility(View.GONE);
            holder.lyt_parent.setBackgroundColor(Color.TRANSPARENT);
            selectList.remove(product_id);
        }
        mainViewModel.setText(String.valueOf(selectList.size()));
    }

   @Override
    public int getItemCount() {
        int size = productList.size();
       /* if (size == 0) {
            product_empty.setVisibility(View.VISIBLE);
        } else {
            product_empty.setVisibility(View.GONE);
        }*/
        return size;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView product_name, product_quantity, product_price;
        private final ImageView Product_image;
        private final View lyt_parent;
        private final ImageView checkbox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_quantity = itemView.findViewById(R.id.product_quantity);
            Product_image = itemView.findViewById(R.id.product_image);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
            checkbox = itemView.findViewById(R.id.checkbox);
        }
    }
}