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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class Tech_LoginActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Initialize edit texts
    private EditText t_name, t_password;
    //Initialize button and textview
    private Button t_loginButton;
    private TextView t_registerHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_tech_login);

        t_name = (EditText) findViewById(R.id.login_technician_name);
        t_password = (EditText) findViewById(R.id.login_technician_password);

        //Finds the id registerHere and calls the method openRegistrationTechnicianActivity
        t_registerHere = (TextView) findViewById(R.id.technician_register_here);
        t_registerHere.setOnClickListener(view -> openRegistrationTechnicianActivity());

        //Login Button
        t_loginButton = (Button) findViewById(R.id.technician_login_button);
        t_loginButton.setOnClickListener(new View.OnClickListener() {
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
        CollectionReference logRef = db.collection("TechnicianAccounts");
        Query q = logRef.whereEqualTo("tech_name", t_name.getText().toString())
                .whereEqualTo("tech_password", t_password.getText().toString());
        Task<QuerySnapshot> querySnapshotTask = q.get();

        querySnapshotTask.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    if(task.getResult().size() == 0) {
                        Toast.makeText(getApplicationContext(), "Invalid tech name or password", Toast.LENGTH_SHORT).show();
                    }
                    openTechnicianDashboardActivity();
                }
            }
        });
    }

    //Login Validations
    public boolean loginValidations() {
        //Technician Name Validation
        if(t_name.getText().toString().trim().isEmpty()) {
            //if store name is empty
            t_name.setError("Input Store Name");
            return false;
        }

        //Password Validation
        if(t_password.getText().toString().isEmpty()) {
            //if password is empty
            t_password.setError("Input Password");
            return false;
        }

        return true;
    }

    //Method that would redirect to the RegistrationTechnicianActivity
    public void openRegistrationTechnicianActivity() {
        Intent intent = new Intent(this, Tech_RegisterActivity.class);
        startActivity(intent);
    }

    //Method that would redirect to the TechnicianNavActivity
    public void openTechnicianDashboardActivity() {
        Intent intent = new Intent(this, Tech_NavigationActivity.class);
        intent.putExtra("techNameGet", t_name.getText().toString());
        startActivity(intent);
    }
}