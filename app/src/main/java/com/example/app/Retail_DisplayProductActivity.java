package com.example.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.app.ui.adapters.ProductImageAdapter;
import com.example.app.ui.models.ProductModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class Retail_DisplayProductActivity extends AppCompatActivity {
    String product_id = "";
    ViewPager Product_image;
    ImageView BackButton;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    View contactPopupView;
    EditText add_item_product_name, add_item_type, add_item_brand, add_item_description, add_item_price, add_item_quantity;
    TextView Product_name, Product_description, Product_brand, Product_quantity, Product_type, Product_status, Product_category, Product_price, Store_name;
    Spinner Product_type_dropdown;
    RadioGroup status, CategoryRadio;
    Button add_item_cancel_button, add_item_add_button;
    RadioButton radioButton;
    Button edit_item;
    ProductModel product = new ProductModel();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ProgressDialog progressDialog;
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
        Product_type_dropdown = findViewById(R.id.spinner_dropdown);
        Product_description = findViewById(R.id.product_description);
        Product_brand = findViewById(R.id.product_brand);
        Product_quantity = findViewById(R.id.product_quantity);
        Product_status = findViewById(R.id.product_status);
        Product_price = findViewById(R.id.product_price);
        Product_category = findViewById(R.id.product_category);
        edit_item = findViewById(R.id.edit_item);
        Store_name = findViewById(R.id.store_name);

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
            }
        });
    }

    private void feed_data() {
        if (product.getProduct_image() != null && !product.getProduct_image().isEmpty()) {
            Product_image.setAdapter(new ProductImageAdapter(context, product.getProduct_image()));
        }

        Product_type.setText(product.getProduct_type());

        Product_name.setText(product.getProduct_name());
        Product_description.setText(product.getProduct_description());
        Product_brand.setText(product.getProduct_brand());
        Store_name.setText(product.getStore_name());
        Product_quantity.setText(product.getProduct_quantity());
        Product_status.setText(product.getProduct_status());
        Product_category.setText(product.getProduct_category());
        String formattedNumber = formatter.format(Long.valueOf((String) product.getProduct_price()));
        Product_price.setText(formattedNumber);

        edit_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_item_popup();
            }
        });
    }

    private void edit_item_popup() {
        dialogBuilder = new AlertDialog.Builder(this);
        contactPopupView = getLayoutInflater().inflate(R.layout.activity_retail_edit_product, null);

        db = FirebaseFirestore.getInstance();
        FirebaseStorage mStorage = FirebaseStorage.getInstance();
        StorageReference storagereference = mStorage.getReference();

        add_item_product_name = contactPopupView.findViewById(R.id.add_item_product_name);
        Product_type_dropdown = contactPopupView.findViewById(R.id.spinner_dropdown);
        add_item_brand = contactPopupView.findViewById(R.id.add_item_brand);
        add_item_description = contactPopupView.findViewById(R.id.add_item_description);
        add_item_price = contactPopupView.findViewById(R.id.add_item_price);
        add_item_quantity = contactPopupView.findViewById(R.id.add_item_quantity);

        status = (RadioGroup) contactPopupView.findViewById(R.id.status);
        CategoryRadio = (RadioGroup) contactPopupView.findViewById(R.id.CategoryRadio);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Data");
        progressDialog.setMessage("Please Wait While Uploading Your data...");

        add_item_cancel_button = (Button) contactPopupView.findViewById(R.id.add_item_cancel_button);
        add_item_cancel_button.setOnClickListener(view -> dialog.dismiss());

        add_item_add_button = (Button) contactPopupView.findViewById(R.id.add_item_add_button);
        add_item_add_button.setOnClickListener(view -> {
            edit_item();
        });

        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    private void edit_item() {

        Spinner spinner = contactPopupView.findViewById(R.id.spinner_dropdown);
        String selectedItem = null;
        if (spinner != null) {
            selectedItem = spinner.getSelectedItem().toString();
            // Rest of the code
        }


        String product_type_dropdown = "selected_item";

        String product_name = add_item_product_name != null ? add_item_product_name.getText().toString() : "";
        String product_desc = add_item_description != null ? add_item_description.getText().toString() : "";
        String product_price = add_item_price != null ? add_item_price.getText().toString() : "";
        String product_brand = add_item_brand != null ? add_item_brand.getText().toString() : "";
        String product_number = add_item_quantity != null ? add_item_quantity.getText().toString() : "";

        String statusText = "";
        String categoryText = "";

        if (CategoryRadio != null) {
            int radioID = CategoryRadio.getCheckedRadioButtonId();
            if (radioID != -1) {
                radioButton = (RadioButton) contactPopupView.findViewById(radioID);
                categoryText = radioButton.getText().toString();
            }
        }
        if (status != null) {
            int radioID = status.getCheckedRadioButtonId();
            if (radioID != -1) {
                radioButton = (RadioButton) contactPopupView.findViewById(radioID);
                statusText = radioButton.getText().toString();
            }
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();
        DocumentReference productRef = FirebaseFirestore.getInstance().collection("Products").document(product_id);

        Map<String, Object> updates = new HashMap<>();
        updates.put("product_name", product_name);
        updates.put("product_type", selectedItem);
        updates.put("product_brand", product_brand);
        updates.put("product_description", product_desc);
        updates.put("product_price", product_price);
        updates.put("product_number", product_number);
        updates.put("product_status", statusText);
        updates.put("product_category", categoryText);


        batch.update(productRef, updates);
        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Product updated", Toast.LENGTH_SHORT).show();
                recreate();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Product failed to update", Toast.LENGTH_SHORT).show();
            }
        });
    }



}