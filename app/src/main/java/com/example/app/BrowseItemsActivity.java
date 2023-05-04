package com.example.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.CategoryAdapter;
import com.example.app.ui.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class BrowseItemsActivity extends Activity {

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_items);

        categoryRecyclerView = findViewById(R.id.category_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

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
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_back:
                // Handle back action
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