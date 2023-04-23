package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.app.ui.adapters.CpuAdapter;
import com.example.app.ui.models.CpuModel;

import java.util.ArrayList;

public class CentralProcessingUnitActivity extends AppCompatActivity {

    ArrayList<CpuModel> cpuModels = new ArrayList<>();

    int[] cpuProductImages = {R.drawable.intelcorei55600_image, R.drawable.amdryzen75700_image, R.drawable.amdryzen55600_image};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central_processing_unit);

        RecyclerView recyclerView = findViewById(R.id.cpu_recyclerview_view);

        setUpCpuModels();

        CpuAdapter adapter = new CpuAdapter(this, cpuModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpCpuModels() {
        String[] cpuProductName = getResources().getStringArray(R.array.cpu_product_name);
        String[] cpuProductPrice = getResources().getStringArray(R.array.cpu_product_price);

        for (int i = 0; i  < cpuProductName.length; i++) {
            cpuModels.add(new CpuModel(cpuProductName[i], cpuProductPrice[i], cpuProductImages[i]));
        }
    }
}