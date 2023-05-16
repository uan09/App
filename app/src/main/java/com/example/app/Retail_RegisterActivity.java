package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Retail_RegisterActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //Initialize edit texts, date picker and radio group, buttons and textview
    private EditText storeName, contactNumber, address, password, confirmPassword;
    private Button registerButton;
    private TextView loginHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_retail_register);

        //Get users input
        storeName = (EditText) findViewById(R.id.register_retailer_storename);
        contactNumber = (EditText) findViewById(R.id.register_retailer_contactNum);
        address = (EditText) findViewById(R.id.register_retailer_address);
        password = (EditText) findViewById(R.id.register_retailer_password);
        confirmPassword = (EditText) findViewById(R.id.register_retailer_confirm_password);

        //Register Button
        registerButton = (Button) findViewById(R.id.retailer_registerbutton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("RetailerAccounts")
                        .whereEqualTo("store_Name", storeName.getText().toString().trim())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    QuerySnapshot qs = task.getResult();
                                    //Checks if there is same email in the database
                                    if (!qs.isEmpty()) {
                                        storeName.setError("This store name has already been registered");
                                    } else {
                                        //Validates the users registrations (mostly checks if there are empty information)
                                        if (registerValidations()) {
                                            register();
                                        }
                                    }
                                } else {
                                    Log.d("", "Error getting data: ", task.getException());
                                }
                            }
                        });
            }
        });

        //Finds the id login and calls the method openLoginRetailerActivity
        loginHere = (TextView) findViewById(R.id.retailer_loginhere);
        loginHere.setOnClickListener(view -> openLoginRetailerActivity());
    }

    //Register Method
    public void register() {
        //Generates random user id number
        int randomId = new Random().nextInt(900000) + 100000;
        String userId = "RETAIL_ID" + randomId;

        String rName = storeName.getText().toString();
        String rNumber = contactNumber.getText().toString();
        String rAddress = address.getText().toString();
        String rPassword = password.getText().toString();
        String rConfirmPassword = confirmPassword.getText().toString();

        // Create a new document in the collection
        DocumentReference docRef = db.collection("RetailerAccounts").document(rName);

        //New data document
        Map<String, Object> RetailerAccounts = new HashMap<>();
        RetailerAccounts.put("retail_userId", userId);
        RetailerAccounts.put("retail_username", rName);
        RetailerAccounts.put("retail_contactNum", rNumber);
        RetailerAccounts.put("retail_address", rAddress);
        RetailerAccounts.put("retail_password", rPassword);
        RetailerAccounts.put("retail_confirmPassword", rConfirmPassword);
        RetailerAccounts.put("retail_permit", "Pending");
        RetailerAccounts.put("retail_status", "Active");

        // Add the user's information to the document
        docRef.set(RetailerAccounts)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Retail_RegisterActivity.this, "SUCCESSFUL", Toast.LENGTH_SHORT).show();
                        openLoginRetailerActivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Retail_RegisterActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //Registration Validations
    public boolean registerValidations() {
        //Store Name Validation
        if (storeName.getText().toString().trim().isEmpty()) {
            //if store name is empty
            storeName.setError("Input Store Name");
            return false;
        }

        //Contact Number Validation
        if (contactNumber.getText().toString().trim().isEmpty()) {
            //if contact number is empty
            contactNumber.setError("Input Contact Number");
            return false;
        }

        //Address Validation
        if (address.getText().toString().trim().isEmpty()) {
            //if address is empty
            address.setError("Address");
            return false;
        }

        //Password Validation
        if (password.getText().toString().trim().isEmpty()) {
            //if password is empty
            password.setError("Input Password");
            return false;
        }

        //Confirm Password Validation
        if (confirmPassword.getText().toString().trim().isEmpty()) {
            //if confirm password is empty
            confirmPassword.setError("Input Confirm Password");
            return false;
        }

        return true;
    }

    //Method that would redirect to the LoginRetailerActivity
    public void openLoginRetailerActivity() {
        Intent intent = new Intent(this, Retail_LoginActivity.class);
        startActivity(intent);
    }
}