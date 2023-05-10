package com.example.app;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.app.databinding.ActivityRetailManageProductsBinding;
import com.example.app.ui.adapters.ProductsAdapter;
import com.example.app.ui.adapters.ViewPagerAdapter;
import com.example.app.ui.models.ItemModel;
import com.example.app.ui.models.ProductModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class Retail_ManageProductsActivity extends AppCompatActivity {
    ActivityRetailManageProductsBinding binding;
    ProductsAdapter productsAdapter;
    ImageView BackButton;
    ImageButton add_button;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    EditText add_item_product_name, add_item_description, add_item_price, add_item_quantity, add_item_category;
    ViewPager viewPager;
    RadioGroup status;
    RadioButton radioButton;
    AppCompatButton upload_photo;
    Button add_item_cancel_button, add_item_add_button;
    ProgressDialog progressDialog;
    FirebaseFirestore db;
    Uri ImageUri;
    ArrayList<Uri> ChooseImageList;
    ArrayList<String> UrlsList;
    StorageReference storagereference;
    FirebaseStorage mStorage;
    View contactPopupView;
    private RecyclerView recyclerView;
    CollectionReference productsRef;
    TextView products_empty;
    String layout_style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();
        binding = ActivityRetailManageProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BackButton = findViewById(R.id.backbutton19);
        BackButton.setOnClickListener(view -> {
            Intent i = new Intent (this, Retail_NavigationActivity.class);
            startActivity(i);
        });

        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(v -> PopupDialog());

        products_empty = findViewById(R.id.products_empty);

        recyclerView = findViewById(R.id.productsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productsAdapter = new ProductsAdapter(productList, this, products_empty, productsRef, layout_style);
        recyclerView.setAdapter(productsAdapter);

        loadProduct();
    }

    List<ProductModel> productList = new ArrayList<>();
    private void loadProduct() {
        FirebaseFirestore.getInstance()
                .collection("Products")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot ds:dsList) {
                        ProductModel product = ds.toObject(ProductModel.class);
                        if (product.getProduct_image() != null && !product.getProduct_image().isEmpty()) {
                            String firstImageUrl = product.getProduct_image().get(0);
                            product.setFirst_image_url(firstImageUrl);
                            productsAdapter.add(product);
                        }
                        initComponents();
                    }
                }).addOnFailureListener(e -> initComponents());
    }

    private void initComponents() {
        recyclerView = findViewById(R.id.productsRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        productsAdapter = new ProductsAdapter(productList, this, products_empty, productsRef, layout_style);
        recyclerView.setAdapter(productsAdapter);

        productsAdapter.setOnItemClickListener(new ProductsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ProductModel obj, int position) {
                Intent i = new Intent(Retail_ManageProductsActivity.this, Retail_DisplayProductActivity.class);
                i.putExtra("product_id", obj.getProduct_id());
                Retail_ManageProductsActivity.this.startActivity(i);
            }
        });
    }

    private void PopupDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        contactPopupView = getLayoutInflater().inflate(R.layout.activity_add_item_popup, null);

        db = FirebaseFirestore.getInstance();
        mStorage = FirebaseStorage.getInstance();
        storagereference = mStorage.getReference();
        viewPager = (ViewPager) contactPopupView.findViewById(R.id.imgGallery);

        ChooseImageList = new ArrayList<>();
        UrlsList = new ArrayList<>();
        upload_photo = (AppCompatButton) contactPopupView.findViewById(R.id.upload_photo);
        upload_photo.setOnClickListener(view -> {
            CheckPermission();
        });

        add_item_product_name = (EditText) contactPopupView.findViewById(R.id.add_item_product_name);
        add_item_category = (EditText) contactPopupView.findViewById(R.id.add_item_category);
        add_item_description = (EditText) contactPopupView.findViewById(R.id.add_item_description);
        add_item_price = (EditText) contactPopupView.findViewById(R.id.add_item_price);
        add_item_quantity = (EditText) contactPopupView.findViewById(R.id.add_item_quantity);
        status = (RadioGroup) contactPopupView.findViewById(R.id.status);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Data");
        progressDialog.setMessage("Please Wait While Uploading Your data...");

        add_item_cancel_button = (Button) contactPopupView.findViewById(R.id.add_item_cancel_button);
        add_item_cancel_button.setOnClickListener(view -> dialog.dismiss());

        add_item_add_button = (Button) contactPopupView.findViewById(R.id.add_item_add_button);
        add_item_add_button.setOnClickListener(view -> {
            addItem();
        });

        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
    }


    private void CheckPermission() {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(Retail_ManageProductsActivity.this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Retail_ManageProductsActivity.this, new
                        String[] { Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                PickImageFromGallery();
            } else {
                PickImageFromGallery();
            }
        } else {
            PickImageFromGallery();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data!= null && data.getClipData() != null) {
            int count = data.getClipData().getItemCount();
            for (int i=0; i < count; i++) {
                ImageUri = data.getClipData().getItemAt(i).getUri();
                ChooseImageList.add(ImageUri);
                SetAdapter();
            }
        } else { // Single image is selected
            ImageUri = data.getData();
            ChooseImageList.clear();
            ChooseImageList.add(ImageUri);
            SetAdapter();
        }
    }

    private void SetAdapter() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, ChooseImageList);
        viewPager.setAdapter(adapter);
    }

    private void addItem() {
        for (int i=0; i < ChooseImageList.size(); i++) {
            Uri IndividualImage = ChooseImageList.get(i);
            if (IndividualImage!=null) {
                progressDialog.show();
                StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("ItemImages");
                final StorageReference ImageName = ImageFolder.child("image"+i+" :"+ IndividualImage.getLastPathSegment());
                ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                UrlsList.add(String.valueOf(uri));
                                if (UrlsList.size()== ChooseImageList.size()) {
                                    StoreLinks(UrlsList);
                                }
                            }
                        });
                    }
                });
            } else {
                Toast.makeText(this, "Please fill all text fields", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void StoreLinks(ArrayList<String> UrlsList) {

        String product_name = add_item_product_name.getText().toString();
        String product_type = add_item_category.getText().toString();
        String product_description = add_item_description.getText().toString();
        String product_price = add_item_price.getText().toString();
        String product_quantity = add_item_quantity.getText().toString();
        String statusText = "";
        if (status != null) {
            int radioID = status.getCheckedRadioButtonId();
            if (radioID != -1) {
                radioButton = (RadioButton) contactPopupView.findViewById(radioID);
                statusText = radioButton.getText().toString();
            }
        }

        if (!TextUtils.isEmpty(product_name) && !TextUtils.isEmpty(product_description)  && !TextUtils.isEmpty(product_price) && !TextUtils.isEmpty(product_quantity) && !TextUtils.isEmpty(statusText) && ImageUri != null && !TextUtils.isEmpty(product_type)) {
            ItemModel model = new ItemModel( product_name, product_type, product_description, product_price, statusText, product_quantity, " ", UrlsList);

            // Add the model to "Products" collection in Firestore
            db.collection("Products").add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    model.setProduct_id(documentReference.getId());

                    // Update the document with the model using merge option
                    db.collection("Products").document(model.getProduct_id())
                            .set(model, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Retail_ManageProductsActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    recreate();
                                }
                            });
                }
            });
        } else {
            progressDialog.dismiss();
            Toast.makeText(this, "Please fill all text fields", Toast.LENGTH_SHORT).show();
        }
        ChooseImageList.clear();
    }

    private void PickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, 1);
    }
}