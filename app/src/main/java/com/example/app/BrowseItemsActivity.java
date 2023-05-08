package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.CategoryAdapter;
import com.example.app.ui.adapters.ProductsAdapter;
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
    TextView products_empty;
    CollectionReference productsRef;
    FirebaseFirestore db;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_items);

        categoryRecyclerView = findViewById(R.id.category_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel("home", "Home", R.drawable.home_icon));
        categoryModelList.add(new CategoryModel("motherboard", "Motherboards", R.drawable.post_add_icon));
        categoryModelList.add(new CategoryModel("processor", "Processors", R.drawable.processor_icon));
        categoryModelList.add(new CategoryModel("graphics card", "Graphics Cards", R.drawable.blur_icon));
        categoryModelList.add(new CategoryModel("RAM", "Memory (RAMs)", R.drawable.ram_icon));
        categoryModelList.add(new CategoryModel("HDD", "Hard Disk Drives", R.drawable.hd_icon));
        categoryModelList.add(new CategoryModel("SDD", "Solid State Drives", R.drawable.sd_icon));
        categoryModelList.add(new CategoryModel("PSU", "Power Supply Units", R.drawable.power_icon));
        categoryModelList.add(new CategoryModel("monitor", "Monitors", R.drawable.monitor_icon));
        categoryModelList.add(new CategoryModel("keyboard", "Keyboards", R.drawable.keyboard_icon));
        categoryModelList.add(new CategoryModel("mouse", "Mouses", R.drawable.mouse_icon));
        categoryModelList.add(new CategoryModel("pc case", "Computer Cases", R.drawable.pc_case_icon));
        categoryModelList.add(new CategoryModel("accessory", "Others", R.drawable.more_horiz_icon));

        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productsAdapter = new ProductsAdapter(productList, this, products_empty, productsRef);
        recyclerView.setAdapter(productsAdapter);

        loadProduct();

        String type = getIntent().getStringExtra("CategoryType");

        if (type.equals("motherboard")) {
            Intent productListIntent = new Intent(this, ProductListActivity.class);
            productListIntent.putExtra("CategoryType", type);
            startActivity(productListIntent);
            finish();
        } else if (type.equals("processor")) {
            Intent productListIntent = new Intent(this, ProductListActivity.class);
            productListIntent.putExtra("CategoryType", type);
            startActivity(productListIntent);
            finish();
        } else if (type.equals("graphics card")) {
            Intent productListIntent = new Intent(this, ProductListActivity.class);
            productListIntent.putExtra("CategoryType", type);
            startActivity(productListIntent);
            finish();
        } else if (type.equals("RAM")) {
            Intent productListIntent = new Intent(this, ProductListActivity.class);
            productListIntent.putExtra("CategoryType", type);
            startActivity(productListIntent);
            finish();
        } else if (type.equals("HDD")) {
            Intent productListIntent = new Intent(this, ProductListActivity.class);
            productListIntent.putExtra("CategoryType", type);
            startActivity(productListIntent);
            finish();
        } else if (type.equals("SDD")) {
            Intent productListIntent = new Intent(this, ProductListActivity.class);
            productListIntent.putExtra("CategoryType", type);
            startActivity(productListIntent);
            finish();
        } else if (type.equals("PSU")) {
            Intent productListIntent = new Intent(this, ProductListActivity.class);
            productListIntent.putExtra("CategoryType", type);
            startActivity(productListIntent);
            finish();
        } else if (type.equals("monitor")) {
            Intent productListIntent = new Intent(this, ProductListActivity.class);
            productListIntent.putExtra("CategoryType", type);
            startActivity(productListIntent);
            finish();
        } else if (type.equals("keyboard")) {
            Intent productListIntent = new Intent(this, ProductListActivity.class);
            productListIntent.putExtra("CategoryType", type);
            startActivity(productListIntent);
            finish();
        } else if (type.equals("mouse")) {
            Intent productListIntent = new Intent(this, ProductListActivity.class);
            productListIntent.putExtra("CategoryType", type);
            startActivity(productListIntent);
            finish();
        } else if (type.equals("pc case")) {
            Intent productListIntent = new Intent(this, ProductListActivity.class);
            productListIntent.putExtra("CategoryType", type);
            startActivity(productListIntent);
            finish();
        } else if (type.equals("accessory")) {
            Intent productListIntent = new Intent(this, ProductListActivity.class);
            productListIntent.putExtra("CategoryType", type);
            startActivity(productListIntent);
            finish();
        }

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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        productsAdapter = new ProductsAdapter(productList, this, products_empty, productsRef);
        recyclerView.setAdapter(productsAdapter);

        productsAdapter.setOnItemClickListener(new ProductsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ProductModel obj, int position) {
                Intent i = new Intent(BrowseItemsActivity.this, User_DisplayProductActivity.class);
                i.putExtra("product_id", obj.getProduct_id());
                BrowseItemsActivity.this.startActivity(i);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.home:
                Intent intent = new Intent(this, NavigationActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.menu_search:
                // Handle search action

                return true;
            case R.id.menu_cart:
                // Handle cart action
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}