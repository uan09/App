package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CpuProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_products);

        String name = getIntent().getStringExtra("NAME");
        String price = getIntent().getStringExtra("PRICE");
        String description = getIntent().getStringExtra("DESCRIPTION");
        int image = getIntent().getIntExtra("IMAGE", 0);

        TextView nameText = findViewById(R.id.productName);
        TextView priceText = findViewById(R.id.productPrice);
        TextView descriptionText = findViewById(R.id.productDescription);
        ImageView imageView = findViewById(R.id.productImage);

        nameText.setText(name);
        priceText.setText(price);
        descriptionText.setText(description);
        imageView.setImageResource(image);
    }
}