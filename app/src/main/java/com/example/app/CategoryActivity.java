package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.CategoryAdapter;
import com.example.app.ui.adapters.ProductsAdapter;
import com.example.app.ui.models.ProductModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView recyclerView;
    ProductsAdapter productsAdapter;
    List<ProductModel> productList = new ArrayList<>();
    TextView products_empty;
    CollectionReference productsRef;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadProduct();
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        /*categoryRecyclerView = findViewById(R.id.category_recyclerView);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);



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


        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();




        String type = getIntent().getStringExtra("CategoryType");*/

        /*if (type.equals("motherboard")) {
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
        }*/
    }

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
                Intent i = new Intent(CategoryActivity.this, Retail_DisplayProductActivity.class);
                i.putExtra("product_id", obj.getProduct_id());
                CategoryActivity.this.startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Handle the back button click
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, BrowseItemsActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}