package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Tech_RegisterActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Initialize edit texts, date picker and radio group
    private EditText name, contactNumber, address, password, confirmPassword;

    //Initialize buttons and textview
    private Button registerButton;
    private TextView loginHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_tech_register);

        //Get users input
        name = (EditText) findViewById(R.id.register_technician_name);
        contactNumber = (EditText) findViewById(R.id.register_technician_contactNum);
        address = (EditText) findViewById(R.id.register_technician_address);
        password = (EditText) findViewById(R.id.register_technician_password);
        confirmPassword = (EditText) findViewById(R.id.register_technician_confirm_password);

        //Register Button
        registerButton = (Button) findViewById(R.id.technician_registerbutton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(registerValidations()) {
                    register();
                }
            }
        });

        //Finds the id login and calls the method openLoginRetailerActivity
        loginHere = findViewById(R.id.technician_loginhere);
        loginHere.setOnClickListener(view -> openLoginTechnicianActivity());
    }

    //Register Method
    public void register() {
        //Generates random user id number
        int randomId = new Random().nextInt(900000) + 100000;
        String userId = "TECH_ID" + randomId;

        String rName = name.getText().toString();
        String rContactNumber = contactNumber.getText().toString();
        String rAddress = address.getText().toString();
        String rPassword = password.getText().toString();
        String rConfirmPassword = confirmPassword.getText().toString();

        //Creates a new document to the collection
        DocumentReference docRef = db.collection("TechnicianAccounts").document(rName);

        //New data document
        Map<String, Object> TechnicianAccounts = new HashMap<>();
        TechnicianAccounts.put("tech_userId", userId);
        TechnicianAccounts.put("tech_name", rName);
        TechnicianAccounts.put("tech_contactNum", rContactNumber);
        TechnicianAccounts.put("tech_address", rAddress);
        TechnicianAccounts.put("tech_password", rPassword);
        TechnicianAccounts.put("tech_confirmPassword", rConfirmPassword);
        TechnicianAccounts.put("tech_permit", "Pending");
        TechnicianAccounts.put("tech_status", "Active");

        //Setting the new data to the database
        // Add the user's information to the document
        docRef.set(TechnicianAccounts)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Redirects to the LoginActivity
                        Toast.makeText(Tech_RegisterActivity.this, "SUCCESSFUL", Toast.LENGTH_SHORT).show();
                        openLoginTechnicianActivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Tech_RegisterActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //Registration Validations
    public boolean registerValidations() {
        //Technician Name Validation
        if(name.getText().toString().trim().isEmpty()) {
            //if store name is empty
            name.setError("Input First Name");
            return false;
        }

        //Contact Number Validation
        if(contactNumber.getText().toString().trim().isEmpty()) {
            //if contact number is empty
            contactNumber.setError("Input Contact Number");
            return false;
        }

        //Address Validation
        if(address.getText().toString().trim().isEmpty()) {
            //if address is empty
            address.setError("Address");
            return false;
        }

        //Password Validation
        if(password.getText().toString().trim().isEmpty()) {
            //if password is empty
            password.setError("Input Password");
            return false;
        }

        //Confirm Password Validation
        if(confirmPassword.getText().toString().trim().isEmpty()) {
            //if confirm password is empty
            confirmPassword.setError("Input Confirm Password");
            return false;
        }

        return true;
    }

    //Method that would redirect to the LoginRetailerActivity
    public void openLoginTechnicianActivity() {
        Intent intent = new Intent(this, Tech_LoginActivity.class);
        startActivity(intent);
    }
}