package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.app.ui.adapters.PsuAdapter;
import com.example.app.ui.models.PsuModel;

import java.util.ArrayList;

public class PowerSupplyUnitActivity extends AppCompatActivity {

    ArrayList<PsuModel> psuModels = new ArrayList<>();

    int[] psuProductImages = {R.drawable.risepsu, R.drawable.bequietpsu, R.drawable.thermaltakepsu};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_supply_unit);

        RecyclerView recyclerView = findViewById(R.id.psu_recyclerview_view);

        setUpPsuModels();

        PsuAdapter adapter = new PsuAdapter(this, psuModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpPsuModels() {
        String[] psuProductName = getResources().getStringArray(R.array.psu_product_name);
        String[] psuProductPrice = getResources().getStringArray(R.array.psu_product_price);

        for (int i = 0; i  < psuProductName.length; i++) {
            psuModels.add(new PsuModel(psuProductName[i], psuProductPrice[i], psuProductImages[i]));
        }
    }
}