package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.app.ui.adapters.RamAdapter;
import com.example.app.ui.models.RamModel;

import java.util.ArrayList;

public class RandomAccessMemoryActivity extends AppCompatActivity {

    ArrayList<RamModel> ramModels = new ArrayList<>();

    int[] ramProductImages = {R.drawable.corsairram, R.drawable.hyperxfuryram, R.drawable.kinstonram};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_access_memory);

        RecyclerView recyclerView = findViewById(R.id.ram_recyclerview_view);

        setUpRamModels();

        RamAdapter adapter = new RamAdapter(this, ramModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpRamModels() {
        String[] ramProductName = getResources().getStringArray(R.array.ram_product_name);
        String[] ramProductPrice = getResources().getStringArray(R.array.ram_product_price);

        for (int i = 0; i  < ramProductName.length; i++) {
            ramModels.add(new RamModel(ramProductName[i], ramProductPrice[i], ramProductImages[i]));
        }
    }
}