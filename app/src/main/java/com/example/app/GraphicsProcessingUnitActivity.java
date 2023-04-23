package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.app.ui.adapters.GpuAdapter;
import com.example.app.ui.models.GpuModel;

import java.util.ArrayList;

public class GraphicsProcessingUnitActivity extends AppCompatActivity {

    ArrayList<GpuModel> gpuModels = new ArrayList<>();

    int[] gpuProductImages = {R.drawable.gtx1050ti, R.drawable.asustufgaming, R.drawable.asusrogstrix};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_processing_unit);

        RecyclerView recyclerView = findViewById(R.id.gpu_recyclerview_view);

        setUpGpuModels();

        GpuAdapter adapter = new GpuAdapter(this, gpuModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpGpuModels() {
        String[] gpuProductName = getResources().getStringArray(R.array.gpu_product_name);
        String[] gpuProductPrice = getResources().getStringArray(R.array.gpu_product_price);

        for (int i = 0; i  < gpuProductName.length; i++) {
            gpuModels.add(new GpuModel(gpuProductName[i], gpuProductPrice[i], gpuProductImages[i]));
        }
    }
}