package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.app.ui.adapters.CoolerAdapter;
import com.example.app.ui.models.CoolerModel;

import java.util.ArrayList;

public class CpuCoolerActivity extends AppCompatActivity {

    ArrayList<CoolerModel> coolerModels = new ArrayList<>();

    int[] coolerImages = {R.drawable.pccasergbfan, R.drawable.hlspccpu, R.drawable.aigocooler};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_cooler);

        RecyclerView recyclerView = findViewById(R.id.cooler_recyclerview_view);

        setUpCoolerModels();

        CoolerAdapter adapter = new CoolerAdapter(this, coolerModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpCoolerModels() {
        String[] coolerProductName = getResources().getStringArray(R.array.cooler_product_name);
        String[] coolerProductPrice = getResources().getStringArray(R.array.cooler_product_price);

        for (int i = 0; i < coolerProductName.length; i++) {
            coolerModels.add(new CoolerModel(coolerProductName[i], coolerProductPrice[i], coolerImages[i]));
        }
    }
}