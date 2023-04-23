package com.example.app;

import android.app.Activity;
import android.os.Bundle;

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

        List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel("link", "Home", R.drawable.home_icon));
        categoryModelList.add(new CategoryModel("link", "Motherboards", R.drawable.post_add_icon));
        categoryModelList.add(new CategoryModel("link", "Processors", R.drawable.processor_icon));
        categoryModelList.add(new CategoryModel("link", "Graphics Cards", R.drawable.blur_icon));
        categoryModelList.add(new CategoryModel("link", "Memory (RAMs)", R.drawable.ram_icon));
        categoryModelList.add(new CategoryModel("link", "Hard Disk Drives", R.drawable.hd_icon));
        categoryModelList.add(new CategoryModel("link", "Solid State Drives", R.drawable.sd_icon));
        categoryModelList.add(new CategoryModel("link", "Power Supply Units", R.drawable.power_icon));
        categoryModelList.add(new CategoryModel("link", "Monitors", R.drawable.monitor_icon));
        categoryModelList.add(new CategoryModel("link", "Keyboards", R.drawable.keyboard_icon));
        categoryModelList.add(new CategoryModel("link", "Mouses", R.drawable.mouse_icon));
        categoryModelList.add(new CategoryModel("link", "Computer Cases", R.drawable.pc_case_icon));
        categoryModelList.add(new CategoryModel("link", "Others", R.drawable.more_horiz_icon));

        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }
}