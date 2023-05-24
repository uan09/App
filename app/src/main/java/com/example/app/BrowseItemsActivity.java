package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.CategoryAdapter;
import com.example.app.ui.adapters.ProductsAdapter;
import com.example.app.ui.fragments.CartFragment;
import com.example.app.ui.fragments.ProfileFragment;
import com.example.app.ui.models.CategoryModel;
import com.example.app.ui.models.ProductModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class BrowseItemsActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView recyclerView;
    ProductsAdapter productsAdapter;
    TextView products_empty, connector;
    ImageView menu_cart1, menu_back;
    CollectionReference productsRef;
    FirebaseFirestore db;
    String layout_style, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_items);

        connector = findViewById(R.id.connector);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("Email")) {
            email = bundle.getString("Email");
            connector.setText(email);
        }
        Toast.makeText(BrowseItemsActivity.this, "Email"+email, Toast.LENGTH_SHORT).show();

        menu_cart1 = findViewById(R.id.menu_cart1);
        menu_cart1.setOnClickListener(view -> {
            CartFragment cartFragment = new CartFragment(); // create a new instance of your Fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_cart, cartFragment); // replace the current Fragment in the container with your new Fragment
            transaction.addToBackStack(null); // add the transaction to the back stack, so the user can navigate back to the previous Fragment by pressing the back button
            transaction.commit();
        });
        menu_back = findViewById(R.id.menu_back);
        menu_back.setOnClickListener(view -> {
            ProfileFragment fragment = new ProfileFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frame_layout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        categoryRecyclerView = findViewById(R.id.category_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        final List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel("home", "Home", R.drawable.home_icon));
        categoryModelList.add(new CategoryModel("processor", "Processors", R.drawable.processor_icon));
        categoryModelList.add(new CategoryModel("motherboard", "Motherboards", R.drawable.post_add_icon));
        categoryModelList.add(new CategoryModel("graphics card", "Graphics Cards", R.drawable.blur_icon));
        categoryModelList.add(new CategoryModel("RAM", "Memory (RAMs)", R.drawable.ram_icon));
        categoryModelList.add(new CategoryModel("HDD", "Hard Disk Drives", R.drawable.hd_icon));
        categoryModelList.add(new CategoryModel("SDD", "Solid State Drives", R.drawable.sd_icon));
        categoryModelList.add(new CategoryModel("cooling fan", "Cooling Fans", R.drawable.fan_icon));
        categoryModelList.add(new CategoryModel("PSU", "Power Supply Units", R.drawable.power_icon));
        categoryModelList.add(new CategoryModel("pc case", "Computer Cases", R.drawable.pc_case_icon));
        categoryModelList.add(new CategoryModel("others", "Others", R.drawable.more_horiz_icon));

        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productsAdapter = new ProductsAdapter(productList, this, products_empty, productsRef, layout_style);
        recyclerView.setAdapter(productsAdapter);

        loadProduct();

    }
    List<ProductModel> productList = new ArrayList<>();
    private void loadProduct() {
        db.getInstance()
                .collection("Products")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot ds:dsList) {
                        ProductModel product = ds.toObject(ProductModel.class);
                        if (product.getProduct_image() != null && !product.getProduct_image().isEmpty()) {
                            String firstImageUrl = product.getProduct_image().get(0);
                            product.setFirst_image_url(firstImageUrl);
                            productsAdapter.add(product);
                        }
                        initComponents();
                    }
                }).addOnFailureListener(e -> initComponents());
    }

    private void initComponents() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        productsAdapter = new ProductsAdapter(productList, this, products_empty, productsRef, layout_style);
        recyclerView.setAdapter(productsAdapter);

        productsAdapter.setOnItemClickListener(new ProductsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ProductModel obj, int position) {
                Intent i = new Intent(BrowseItemsActivity.this, User_DisplayProductActivity.class);
                i.putExtra("Email", email);
                i.putExtra("product_id", obj.getProduct_id());
                BrowseItemsActivity.this.startActivity(i);
            }
        });
    }
}