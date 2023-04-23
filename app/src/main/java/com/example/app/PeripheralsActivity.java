package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.app.ui.adapters.PeripheralsAdapter;
import com.example.app.ui.models.PeripheralsModel;

import java.util.ArrayList;

public class PeripheralsActivity extends AppCompatActivity {

    ArrayList<PeripheralsModel> peripheralsModels = new ArrayList<>();

    int[] peripheralsProductImages = {R.drawable.vcommouse, R.drawable.m001ergomouse, R.drawable.mxmiomouse};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peripherals);

        RecyclerView recyclerView = findViewById(R.id.peripherals_recyclerview_view);

        setUpPeripheralsModels();

        PeripheralsAdapter adapter = new PeripheralsAdapter(this, peripheralsModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpPeripheralsModels() {
        String[] peripheralsProductName = getResources().getStringArray(R.array.peripherals_product_name);
        String[] peripheralsProductPrice = getResources().getStringArray(R.array.peripherals_product_price);

        for (int i = 0; i  < peripheralsProductName.length; i++) {
            peripheralsModels.add(new PeripheralsModel(peripheralsProductName[i], peripheralsProductPrice[i],  peripheralsProductImages[i]));
        }
    }
}