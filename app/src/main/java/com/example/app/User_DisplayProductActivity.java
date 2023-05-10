package com.example.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class User_DisplayProductActivity extends AppCompatActivity {
    String product_id = "";
    ViewPager Product_image;
    EditText quantity;
    ImageView BackButton;
    TextView Product_id, Product_name, Product_description, Product_quantity, Product_status, Product_price, Product_type;
    Button add_item;
    ProductModel product = new ProductModel();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Context context;
    NumberFormat formatter = new DecimalFormat("###,###,###");
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_product_display);

        BackButton = findViewById(R.id.backbutton19);
        BackButton.setOnClickListener(view -> {
            Intent intent = new Intent(User_DisplayProductActivity.this, BrowseItemsActivity.class);
            startActivity(intent);
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Data");
        progressDialog.setMessage("Please Wait While Uploading Your data...");

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
        quantity = findViewById(R.id.user_quantity);
        add_item = findViewById(R.id.add_to_cart);
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
        Product_description.setText("Description: " + product.getProduct_description());
        Product_type.setText("Type: " + product.getProduct_type());
        Product_quantity.setText("Quantity: " + product.getProduct_quantity());
        Product_status.setText(product.getProduct_status());
        String formattedNumber = formatter.format(Long.valueOf((String)product.getProduct_price()));
        Product_price.setText("P"+formattedNumber);
        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_item();
            }
        });

    }

    private void add_item() {
        String product_name = Product_name.getText().toString();
        String product_type = Product_type.getText().toString();
        String product_price = Product_price.getText().toString();
        String product_number = quantity.getText().toString();
        String product_image_url = null;

        if (product_number.isEmpty()) {
            Toast.makeText(context, "Please enter a quantity", Toast.LENGTH_SHORT).show();
            return;
        }
        int currentImagePosition = Product_image.getCurrentItem();
        if (currentImagePosition >= 0 && currentImagePosition < product.getProduct_image().size()) {
            product_image_url = product.getProduct_image().get(currentImagePosition);
        }

        CollectionReference cartRef = FirebaseFirestore.getInstance().collection("Cart");

        Map<String, Object> cart_item = new HashMap<>();
        cart_item.put("product_name", product_name);
        cart_item.put("product_type", product_type);
        cart_item.put("product_price", product_price);
        cart_item.put("product_number", product_number);
        if (product_image_url != null) {
            cart_item.put("product_image_url", product_image_url);
        }

        // Create a batch operation to update the product quantity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();
        DocumentReference productRef = FirebaseFirestore.getInstance().collection("Products").document(product_id);

        productRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    WriteBatch batch = FirebaseFirestore.getInstance().batch();

                    String quantityString = product.getProduct_quantity();
                    String inputtedQuantityString = quantity.getText().toString();

                    int currentQuantity = Integer.parseInt(quantityString);
                    int desiredQuantity = Integer.parseInt(inputtedQuantityString);

                    int newQuantity = currentQuantity - desiredQuantity;


                    String newQuantityStr = String.valueOf(newQuantity);
                    Object finalQty = newQuantityStr;
                    batch.update(productRef, "product_quantity", finalQty);
                    batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Product quantity updated", Toast.LENGTH_SHORT).show();
                            recreate();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Product quantity failed to update", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(context, "Product not found", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error retrieving product: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }



//    private void add_item() {
//        String product_name = Product_name.getText().toString();
//        String product_type = Product_type.getText().toString();
//        String product_price = Product_price.getText().toString();
//        String product_number = quantity.getText().toString();
//        String product_image_url = null;
//
//        if (product_number.isEmpty()) {
//            Toast.makeText(context, "Please enter a quantity", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        int currentImagePosition = Product_image.getCurrentItem();
//        if (currentImagePosition >= 0 && currentImagePosition < product.getProduct_image().size()) {
//            product_image_url = product.getProduct_image().get(currentImagePosition);
//        }
//
//        CollectionReference cartRef = FirebaseFirestore.getInstance().collection("Cart");
//
//        Map<String, Object> cart_item = new HashMap<>();
//        cart_item.put("product_name", product_name);
//        cart_item.put("product_type", product_type);
//        cart_item.put("product_price", product_price);
//        cart_item.put("product_number", product_number);
//        if (product_image_url != null) {
//            cart_item.put("product_image_url", product_image_url);
//        }
//
//        // Add a new document to the "cart" collection with the data from the cart_item object
//        cartRef.add(cart_item)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(context, "Product added to cart", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(context, "Product failed to cart", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

}