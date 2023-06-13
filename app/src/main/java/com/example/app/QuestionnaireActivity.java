package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class QuestionnaireActivity extends AppCompatActivity {
    FirebaseFirestore db;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_questionnaire);
        db = FirebaseFirestore.getInstance();
        Button registerButton = findViewById(R.id.registerbutton);
        registerButton.setOnClickListener(view -> savePreferences());

        email = getIntent().getStringExtra("email");
        if (email != null) {
            Toast.makeText(QuestionnaireActivity.this, "email"+email, Toast.LENGTH_SHORT).show();
        }
    }
    public void savePreferences() {
        // Retrieve the selected radio button value and the brand value
        RadioGroup categoryRadioGroup = findViewById(R.id.CategoryRadio);
        int selectedRadioButtonId = categoryRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        String categoryPreference = selectedRadioButton.getText().toString();

        EditText brandEditText = findViewById(R.id.answer_brand);
        String brandPreference = brandEditText.getText().toString();

        // Create a new map to store the preferences
        Map<String, Object> userPreferences = new HashMap<>();
        userPreferences.put("preference_category", categoryPreference);
        userPreferences.put("preference_brand", brandPreference);

        // Save the preferences under the UserPreference collection within the UserAccounts document with matching user_email

        db.collection("UserAccounts")
                .whereEqualTo("user_Email", email)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        // Get the first document in the query results
                        DocumentSnapshot userAccountDoc = querySnapshot.getDocuments().get(0);

                        // Get the reference to the UserPreference collection within the UserAccounts document
                        CollectionReference userPreferenceCollection = userAccountDoc.getReference().collection("UserPreference");

                        // Create a new document within the UserPreference collection
                        userPreferenceCollection
                                .add(userPreferences)
                                .addOnSuccessListener(documentReference -> {
                                    Toast.makeText(QuestionnaireActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                    openLoginActivity();
                                })
                                .addOnFailureListener(e -> Toast.makeText(QuestionnaireActivity.this, "Failed to save preferences", Toast.LENGTH_SHORT).show());
                    } else {
                        Toast.makeText(QuestionnaireActivity.this, "UserAccount document not found for the given email", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(QuestionnaireActivity.this, "Failed to retrieve UserAccount document", Toast.LENGTH_SHORT).show());
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
