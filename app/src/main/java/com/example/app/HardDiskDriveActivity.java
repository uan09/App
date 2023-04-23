package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.app.ui.adapters.HddAdapter;
import com.example.app.ui.models.HddModel;

import java.util.ArrayList;

public class HardDiskDriveActivity extends AppCompatActivity {

    ArrayList<HddModel> hddModels = new ArrayList<>();

    int[] hddProductImages = {R.drawable.wdbluehdd, R.drawable.wdredhdd, R.drawable.seagatehdd};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_disk_drive);

        RecyclerView recyclerView = findViewById(R.id.hdd_recyclerview_view);

        setUpHddModels();

        HddAdapter adapter = new HddAdapter(this, hddModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpHddModels() {
        String[] hddProductName = getResources().getStringArray(R.array.hdd_product_name);
        String[] hddProductPrice = getResources().getStringArray(R.array.hdd_product_price);

        for (int i = 0; i  < hddProductName.length; i++) {
            hddModels.add(new HddModel(hddProductName[i], hddProductPrice[i], hddProductImages[i]));
        }
    }
}