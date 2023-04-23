package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.app.ui.adapters.CaseAdapter;
import com.example.app.ui.models.CaseModel;

import java.util.ArrayList;

public class PcCaseActivity extends AppCompatActivity {

    ArrayList<CaseModel> caseModels = new ArrayList<>();

    int[] caseProductImages = {R.drawable.corsairicuecase, R.drawable.gofazzacase, R.drawable.beastatxcase};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pc_case);

        RecyclerView recyclerView = findViewById(R.id.pccase_recyclerview_view);

        setUpCaseModels();

        CaseAdapter adapter = new CaseAdapter(this, caseModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpCaseModels() {
        String[] caseProductName = getResources().getStringArray(R.array.pccase_product_name);
        String[] caseProductPrice = getResources().getStringArray(R.array.pccase_product_price);

        for (int i = 0; i  < caseProductName.length; i++) {
            caseModels.add(new CaseModel(caseProductName[i], caseProductPrice[i],  caseProductImages[i]));
        }
    }
}