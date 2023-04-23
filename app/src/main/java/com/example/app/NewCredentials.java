package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewCredentials extends AppCompatActivity {
    EditText password_new, confirm_password_new;
    FirebaseFirestore db;
    ImageView backbutton4;
    TextView emailTextView;
    Button update_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_new_credentials);

        db = FirebaseFirestore.getInstance();
        emailTextView = findViewById(R.id.emailTextView);
        password_new = findViewById(R.id.password_new);
        confirm_password_new = findViewById(R.id.confirm_password_new);


        update_button = findViewById(R.id.update_button);
        update_button.setOnClickListener(view -> Update());

        backbutton4 = findViewById(R.id.backbutton4);
        backbutton4.setOnClickListener(view -> openForgotPasswordActivity());
    }

    public void Update() {
        String email;
        String new_password = password_new.getText().toString();
        String confirmPassword = confirm_password_new.getText().toString();

        Intent intentReceived = getIntent();
        Bundle extras = intentReceived.getExtras();
        email = extras.getString("Value");
        emailTextView.setText("");
        password_new.setText("");

        if (new_password.isEmpty()) {
            password_new.setError ("Enter Your New Password");
        }else if (!new_password.equals(confirmPassword)) {
            confirm_password_new.setError("Password does not match");
        } else {
            UpdateData (email, new_password);
        }
    }

    public void openForgotPasswordActivity() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void UpdateData (String email, String new_password) {
        Map<String, Object> UserAccounts = new HashMap<>();
        UserAccounts.put("user_password", new_password);

        db.collection("UserAccounts")
                .whereEqualTo("user_Email", email)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        String documentID = documentSnapshot.getId();
                        db.collection("UserAccounts")
                                .document(documentID)
                                .update(UserAccounts)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(NewCredentials.this, "Successfully changed new password", Toast.LENGTH_SHORT).show();
                                    openLoginActivity();
                                }).addOnFailureListener(e -> Toast.makeText(NewCredentials.this, "Error Occurred", Toast.LENGTH_SHORT).show());
                    } else {
                        Toast.makeText(NewCredentials.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}