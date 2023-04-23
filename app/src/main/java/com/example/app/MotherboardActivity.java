package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.app.ui.adapters.MotherboardAdapter;
import com.example.app.ui.models.MotherboardModel;

import java.util.ArrayList;

public class MotherboardActivity extends AppCompatActivity {

    ArrayList<MotherboardModel> motherboardModels = new ArrayList<>();

    int[] motherboardProductImages = {R.drawable.motherboardaorus, R.drawable.motherboardaorusmaster, R.drawable.motherboardmsi};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motherboard);

        RecyclerView recyclerView = findViewById(R.id.motherboard_recyclerview_view);

        setUpMotherboardModels();

        MotherboardAdapter adapter = new MotherboardAdapter(this, motherboardModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpMotherboardModels() {
        String[] motherboardProductName = getResources().getStringArray(R.array.motherboard_product_name);
        String[] motherboardProductPrice = getResources().getStringArray(R.array.motherboard_product_price);

        for (int i = 0; i  < motherboardProductName.length; i++) {
            motherboardModels.add(new MotherboardModel(motherboardProductName[i], motherboardProductPrice[i], motherboardProductImages[i]));
        }
    }
}