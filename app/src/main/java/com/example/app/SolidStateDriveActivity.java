package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.app.ui.adapters.SsdAdapter;
import com.example.app.ui.models.SsdModel;

import java.util.ArrayList;

public class SolidStateDriveActivity extends AppCompatActivity {

    ArrayList<SsdModel> ssdModels = new ArrayList<>();

    int[] ssdProductImages = {R.drawable.wdgreenssd, R.drawable.wdbluessd, R.drawable.oricossd};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solid_state_drive);

        RecyclerView recyclerView = findViewById(R.id.ssd_recyclerview_view);

        setUpSsdModels();

        SsdAdapter adapter = new SsdAdapter(this, ssdModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpSsdModels() {
        String[] ssdProductName = getResources().getStringArray(R.array.ssd_product_name);
        String[] ssdProductPrice = getResources().getStringArray(R.array.ssd_product_price);

        for (int i = 0; i  < ssdProductName.length; i++) {
            ssdModels.add(new SsdModel(ssdProductName[i], ssdProductPrice[i], ssdProductImages[i]));
        }
    }
}