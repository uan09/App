package com.example.app;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RegistrationActivity extends AppCompatActivity {
    EditText register_username, register_password, confirm_register_password, register_email, register_firstname, register_lastname, register_contactNum, register_birthdate, register_address;
    TextView user_id, loginhere;
    Button registerbutton;
    RadioGroup gender;
    RadioButton radioButton;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseFirestore db;
    DatePickerDialog.OnDateSetListener dateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_registration);

        db = FirebaseFirestore.getInstance();
        register_username = findViewById(R.id.register_username);
        register_firstname = findViewById(R.id.register_firstname);
        register_lastname = findViewById(R.id.register_lastname);
        register_password =  findViewById(R.id.register_password);
        confirm_register_password = findViewById(R.id.confirm_register_password);
        register_email = findViewById(R.id.register_email);
        register_contactNum = findViewById(R.id.register_contactNum);
        gender = findViewById(R.id.gender);
        register_birthdate = findViewById(R.id.register_birthDate);
        register_address = findViewById(R.id.register_address);
        user_id = findViewById(R.id.user_id);

        progressDialog = new ProgressDialog(this);

        loginhere = findViewById(R.id.loginhere);
        loginhere.setOnClickListener(view -> openLoginActivity());

        register_birthdate.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatePickerDialog dialog = new DatePickerDialog(RegistrationActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener,year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            Log.d(TAG, "onDateSet: date: " + month + "/" + day + "/" + year);

            String date = month + "/" + day + "/" + year;
            register_birthdate.setText(date);
        };

        registerbutton = findViewById(R.id.registerbutton);
        registerbutton.setOnClickListener(view -> Register());
    }

    @SuppressLint("DefaultLocale")
    private void Register() {
        int radioID = gender.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
        final String gender = radioButton.getText().toString();
        final Random myRandom = new Random();
        String username = register_username.getText().toString();
        String firstname = register_firstname.getText().toString();
        String lastname = register_lastname.getText().toString();
        String password = register_password.getText().toString();
        String confirmPassword = confirm_register_password.getText().toString();
        String email = register_email.getText().toString();
        String contactNum = register_contactNum.getText().toString();
        String birthDate = register_birthdate.getText().toString();
        String address = register_address.getText().toString();

        if (username.isEmpty()) {
            register_username.setError ("Enter Your Username");
        }else if (firstname.isEmpty()) {
            register_firstname.setError ("Enter Your First Name");
        }else if (lastname.isEmpty()) {
            register_lastname.setError ("Enter Your Last Name");
        }else if (password.isEmpty()) {
            register_password.setError ("Enter Your Password");
        }else if (!password.equals(confirmPassword)) {
            confirm_register_password.setError ("Password does not match");
        }else if (email.isEmpty()) {
            register_email.setError ("Enter Your Email Address");
        } else if (!email.matches(emailPattern)) {
            register_email.setError ("Enter Correct Email");
        }else if (contactNum.isEmpty()) {
            register_contactNum.setError ("Enter Your Contact Number");
        }else if (birthDate.isEmpty()) {
            register_birthdate.setError ("Enter Your Date of Birth");
        }else if (address.isEmpty()) {
            register_address.setError ("Enter Your Complete Address");
        }else {
            user_id.setText(String.format("%06d", myRandom.nextInt(1)));
            String register_user_id = user_id.getText().toString();

            progressDialog.setMessage("Please wait while registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            Map<String, Object> UserAccounts = new HashMap<>();
            UserAccounts.put("user_ID", register_user_id);
            UserAccounts.put("user_name", username);
            UserAccounts.put("user_password", password);
            UserAccounts.put("user_Firstname", firstname);
            UserAccounts.put("user_Lastname", lastname);
            UserAccounts.put("user_Email", email);
            UserAccounts.put("user_contactNum", contactNum);
            UserAccounts.put("user_Gender", gender);
            UserAccounts.put("user_birthDate", birthDate);
            UserAccounts.put("user_Address", address);

            db.collection("UserAccounts")
                    .add(UserAccounts)
                    .addOnSuccessListener(documentReference -> {
                        openLoginActivity();
                        Toast.makeText(RegistrationActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e -> Toast.makeText(RegistrationActivity.this, "Failed", Toast.LENGTH_SHORT).show());

           /*
            db.collection("UserAccounts")
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc : task.getResult()) {
                                String a = doc.getString("user_ID");
                                if (register_user_id.equals(a)) {

                                    Toast.makeText(RegistrationActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }).addOnFailureListener(e -> Toast.makeText(RegistrationActivity.this, "User ID already existed", Toast.LENGTH_SHORT).show());
            */
        }
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}