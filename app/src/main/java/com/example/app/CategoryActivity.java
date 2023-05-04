package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.CategoryAdapter;
import com.example.app.ui.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryRecyclerView = findViewById(R.id.category_recyclerView);
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

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Handle the back button click
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}