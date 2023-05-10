package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.app.ui.adapters.ProductImageAdapter;
import com.example.app.ui.models.ProductModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Retail_DisplayProductActivity extends AppCompatActivity {
    String product_id = "";
    ViewPager Product_image;
    ImageView BackButton;
    TextView Product_name, Product_description, Product_quantity, Product_status, Product_price, Product_type;
    Button edit_item;
    ProductModel product = new ProductModel();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Context context;
    NumberFormat formatter = new DecimalFormat("###,###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retail_product_display);

        BackButton = findViewById(R.id.backbutton19);
        BackButton.setOnClickListener(view -> {
            Intent intent = new Intent(Retail_DisplayProductActivity.this, Retail_ManageProductsActivity.class);
            startActivity(intent);
        });

        context = this;

        Intent i = getIntent();
        product_id = i.getStringExtra("product_id");
        if (product_id == null || product_id.length() < 1) {
            Toast.makeText(this, "ID NOT FOUND", Toast.LENGTH_SHORT).show();
            finish();
        }
        bind_views();
        get_data();
    }

    private void bind_views() {
        Product_image = findViewById(R.id.product_image);
        Product_name = findViewById(R.id.product_name);
        Product_type = findViewById(R.id.product_type);
        Product_description = findViewById(R.id.product_description);
        Product_quantity = findViewById(R.id.product_quantity);
        Product_status = findViewById(R.id.product_status);
        Product_price = findViewById(R.id.product_price);
        edit_item = findViewById(R.id.edit_item);
    }

    private void get_data() {
        db.collection("Products").document(product_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (!documentSnapshot.exists()) {
                    Toast.makeText(context, "Product not found.", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                product = documentSnapshot.toObject(ProductModel.class);
                feed_data();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed because " + e.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        });
    }

    private void feed_data() {
        if (product.getProduct_image() != null && !product.getProduct_image().isEmpty()) {
            Product_image.setAdapter(new ProductImageAdapter(context, product.getProduct_image()));
        }
        Product_name.setText(product.getProduct_name());
        Product_description.setText(product.getProduct_description());
        Product_type.setText("Type " + product.getProduct_type());
        Product_quantity.setText("Quantity " + product.getProduct_quantity());
        Product_status.setText(product.getProduct_status());
        String formattedNumber = formatter.format(Long.valueOf((String)product.getProduct_price()));
        Product_price.setText("P"+formattedNumber);
        edit_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_item();
            }
        });


    }
    private void edit_item() {


    }
}