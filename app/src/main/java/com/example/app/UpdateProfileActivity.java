package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.ui.fragments.ManagerFragment;
import com.example.app.ui.fragments.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity {
    EditText update_input_first_name, update_input_last_name, update_input_contact_number, update_input_birthdate, update_address;
    TextView emailTextview;
    ImageView Back;
    Button update_profile_button;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    RadioGroup gender;
    RadioButton radioButton;
    ProfileFragment profileFragment = new ProfileFragment();
    String firstname, lastname, email, contactNum, birthdate, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_update_profile);

        update_input_first_name = findViewById(R.id.update_input_first_name);
        update_input_last_name = findViewById(R.id.update_input_last_name);
        update_input_contact_number = findViewById(R.id.update_input_contact_number);
        gender = findViewById(R.id.gender);
        update_input_birthdate = findViewById(R.id.update_birthdate);
        update_address = findViewById(R.id.update_address);
        emailTextview = findViewById(R.id.emailTextview);

        Back = findViewById(R.id.Back);
        Back.setOnClickListener(view -> {
            ProfileFragment fragment = new ProfileFragment();
            Bundle args = new Bundle();
            args.putString("Email", email);
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,profileFragment).commit();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frame_layout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        update_profile_button = findViewById(R.id.update_profile_button);
        update_profile_button.setOnClickListener(view -> Update());

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("Email")) {
            email = bundle.getString("Email");
            emailTextview.setText(email);
        }

        db.collection("UserAccounts")
                .whereEqualTo("user_Email", email)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        Log.d("UserAccounts", "DocumentSnapshot: " + documentSnapshot.getData());
                        firstname = documentSnapshot.getString("user_Firstname");
                        lastname = documentSnapshot.getString("user_Lastname");
                        contactNum = documentSnapshot.getString("user_contactNum");
                        //gender = documentSnapshot.getString("user_Gender");
                        birthdate = documentSnapshot.getString("user_birthDate");
                        address = documentSnapshot.getString("user_Address");

                        update_input_first_name.setText(firstname);
                        update_input_last_name.setText(lastname);
                        update_input_contact_number.setText(contactNum);
                        //profile_gender.setText(gender);
                        update_input_birthdate.setText(birthdate);
                        update_address.setText(address);
                    }
                });
    }

    public void Update() {

        String first_name = update_input_first_name.getText().toString();
        String last_name = update_input_last_name.getText().toString();
        String contact_Num = update_input_contact_number.getText().toString();
        int radioID = gender.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
        final String gender = radioButton.getText().toString();
        String birth_date = update_input_birthdate.getText().toString();
        String home_address = update_address.getText().toString();

        UpdateProfile(first_name, last_name, contact_Num, gender,  birth_date, home_address);
    }
    public void UpdateProfile(String first_name, String last_name, String contact_Num, String gender, String birth_date, String home_address) {
        Map<String, Object> UserAccounts = new HashMap<>();
        UserAccounts.put("user_Firstname", first_name);
        UserAccounts.put("user_Lastname", last_name);
        UserAccounts.put("user_contactNum", contact_Num);
        UserAccounts.put("user_Gender", gender);
        UserAccounts.put("user_birthDate", birth_date);
        UserAccounts.put("user_Address", home_address);

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
                                    openNavigationActivity();
                                    Toast.makeText(UpdateProfileActivity.this, "Successfully profile updated", Toast.LENGTH_SHORT).show();
                                }).addOnFailureListener(e ->
                                        Toast.makeText(UpdateProfileActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show());
                    } else {
                        Toast.makeText(UpdateProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void openNavigationActivity() {
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}