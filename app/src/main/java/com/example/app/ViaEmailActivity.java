package com.example.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;

public class ViaEmailActivity extends AppCompatActivity {

    EditText forgotpassword_viaemail;
    ImageView backButton;
    Button email_next_button;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_via_email);

        db = FirebaseFirestore.getInstance();
        forgotpassword_viaemail = findViewById(R.id.forgotpassword_viaemail);
        progressDialog = new ProgressDialog(this);

        backButton = findViewById(R.id.backbutton3);
        backButton.setOnClickListener(view -> openForgotPasswordActivity());

        email_next_button = findViewById(R.id.email_next_button);
        email_next_button.setOnClickListener(view -> forgotPassword());
    }

    private void forgotPassword() {
        String email = String.valueOf(forgotpassword_viaemail.getText());

        if(email.isEmpty()) {
            forgotpassword_viaemail.setError("Please input password.");
        } else if (!email.matches(emailPattern)) {
            forgotpassword_viaemail.setError("Enter Correct Email");

            db.collection("UserAccounts")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String a = doc.getString("user_Email");
                                if (Objects.equals(a, email)) {
                                    forgotpassword_viaemail.setError(null);
                                    progressDialog.setMessage("Please wait while...");
                                    progressDialog.setCanceledOnTouchOutside(false);
                                    progressDialog.show();
                                    openNewCredentialsActivity();
                                    Toast.makeText(ViaEmailActivity.this, "Correct Email", Toast.LENGTH_SHORT).show();
                                    Intent intent1 = new Intent(this, NewCredentials.class);
                                    Bundle data1 = new Bundle();
                                    data1.putString("Value", email);
                                    intent1.putExtras(data1);
                                    startActivity(intent1);
                                    break;
                                } else if (!Objects.equals(a, email) && !email.matches(emailPattern)) {
                                    forgotpassword_viaemail.setError(Html.fromHtml("<font color='red'>Invalid</font>"));
                                }
                            }
                        }
                    });
        }
    }

    public void openForgotPasswordActivity() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void openNewCredentialsActivity() {
        Intent intent = new Intent(this, NewCredentials.class);
        startActivity(intent);
    }
}