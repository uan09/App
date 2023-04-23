package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {
    EditText login_email, login_password;
    TextView registerhere, forgotpassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Button loginbutton;
    SharedPreferences sharedpreferences;
    int autoSave;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);

        progressDialog = new ProgressDialog(this);

        registerhere = findViewById(R.id.registerhere);
        registerhere.setOnClickListener(view -> openRegisterActivity());

        forgotpassword = findViewById(R.id.forgotpassword);
        forgotpassword.setOnClickListener(view -> openForgotPasswordActivity());

        loginbutton = findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(view -> Login());

    }

    private void Login() {
        String email = String.valueOf(login_email.getText());
        String password = String.valueOf(login_password.getText());

        if(email.isEmpty()) {
            login_email.setError("Enter your Email");
        } else if(!email.matches(emailPattern)) {
            login_email.setError("Enter correct Email");
        } else if(password.isEmpty()) {
            login_password.setError("Enter your Password");
        }

        db.collection("UserAccounts")
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot doc : task.getResult()) {
                            String a = doc.getString("user_Email");
                            String b = doc.getString("user_password");
                            if (Objects.equals(a, email) & Objects.equals(b, password)) {
                                login_email.setError(null);
                                login_password.setError(null);

                                progressDialog.setMessage("Please wait while logging in...");
                                progressDialog.setTitle("LOGGING IN");
                                progressDialog.setCanceledOnTouchOutside(false);
                                progressDialog.show();
                                openNavigationActivity();
                                Toast.makeText(LoginActivity.this, "LOGGED IN", Toast.LENGTH_SHORT).show();

                                Intent intent1 = new Intent (this, NavigationActivity.class);
                                Bundle data1 = new Bundle();
                                data1.putString("Value", email);
                                intent1.putExtras(data1);
                                startActivity(intent1);
                                break;
                            } else if (email.equals(a) & !password.equals(b)) {
                                login_password.setError(Html.fromHtml("<font color='red'>Invalid</font>"));
                                login_email.setError(null);
                            } else if (!password.equals(b)){
                                login_email.setError(Html.fromHtml("<font color='red'>Invalid</font>"));
                                login_password.setError(Html.fromHtml("<font color='red'>Invalid</font>"));
                            }
                        }
                    }
                });
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void openNavigationActivity() {
        progressDialog.dismiss();
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
    }

    public void openForgotPasswordActivity() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}