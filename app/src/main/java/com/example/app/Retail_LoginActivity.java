package com.example.app;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class Retail_LoginActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Initialize edit texts, button and textview
    private EditText r_name, r_password;
    private Button r_loginButton;
    private TextView r_registerHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_retail_login);

        r_name = (EditText) findViewById(R.id.login_retailer_name);
        r_password = (EditText) findViewById(R.id.login_retailer_password);

        //Finds the id registerHere and calls the method openRegistrationActivity
        r_registerHere = findViewById(R.id.retailer_register_here);
        r_registerHere.setOnClickListener(view -> openRegistrationActivity());

        //Login Button
        r_loginButton = (Button) findViewById(R.id.retailer_login_button);
        r_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginValidations()) {
                    login();
                }
            }
        });
    }

    //Login Method
    private void login() {
        CollectionReference logRef = db.collection("RetailerAccounts");
        Query q = logRef.whereEqualTo("retail_username", r_name.getText().toString())
                .whereEqualTo("retail_password", r_password.getText().toString());
        Task<QuerySnapshot> querySnapshotTask = q.get();

        querySnapshotTask.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    if(task.getResult().size() == 0) {
                        Toast.makeText(getApplicationContext(), "Invalid store name or password", Toast.LENGTH_SHORT).show();
                    } else {
                        openRetailerDashboardActivity();
                    }
                }
            }
        });
    }

    //Login Validations
    public boolean loginValidations() {
        //Store Name Validation
        if(r_name.getText().toString().trim().isEmpty()) {
            //if store name is empty
            r_name.setError("Input Store Name");
            return false;
        }

        //Password Validation
        if(r_password.getText().toString().isEmpty()) {
            //if password is empty
            r_password.setError("Input Password");
            return false;
        }

        return true;
    }

    //Method that would redirect to the RegistrationActivity
    public void openRegistrationActivity() {
        Intent intent = new Intent(this, Retail_RegisterActivity.class);
        startActivity(intent);
    }

    //Method that would redirect to the RetailerNavActivity
    public void openRetailerDashboardActivity() {
        Intent intent = new Intent(this, Retail_NavigationActivity.class);
        intent.putExtra("storeGet", r_name.getText().toString());
        startActivity(intent);
    }
}