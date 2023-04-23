package com.example.app.ui.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.LoginActivity;
import com.example.app.NavigationActivity;
import com.example.app.R;
import com.example.app.UpdateProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {
    TextView profile_userID, profile_username, profile_firstname, profile_lastname, profile_email, profile_contactNum, profile_gender, profile_birthdate, profile_address, connector;
    Button update_button, delete_button;
    ImageView Back;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String id, username, firstname, lastname, email, contactNum, gender, birthdate, address;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        profile_userID = rootView.findViewById(R.id.textview2);
        profile_username = rootView.findViewById(R.id.textview1);
        profile_firstname = rootView.findViewById(R.id.textview3);
        profile_lastname = rootView.findViewById(R.id.textview4);
        profile_email = rootView.findViewById(R.id.textview5);
        profile_contactNum = rootView.findViewById(R.id.textview6);
        profile_gender = rootView.findViewById(R.id.textview7);
        profile_birthdate = rootView.findViewById(R.id.textview8);
        profile_address = rootView.findViewById(R.id.textview9);
        connector = rootView.findViewById(R.id.connector);

        delete_button = rootView.findViewById(R.id.delete_button);
        delete_button.setOnClickListener(rootView1 -> openAlertBox());

        Back = rootView.findViewById(R.id.Back);
        Back.setOnClickListener(rootView1 -> openNavigationActivity());

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("Email")) {
            email = bundle.getString("Email");
            connector.setText(email);
        }

        update_button = rootView.findViewById(R.id.update_button);
        update_button.setOnClickListener(rootView12 -> {
            assert bundle != null;
            bundle.putString("Email", email);
            Intent intent = new Intent(getActivity(), UpdateProfileActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db.collection("UserAccounts")
                .whereEqualTo("user_Email", email)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        Log.d("UserAccounts", "DocumentSnapshot: " + documentSnapshot.getData());
                        id = documentSnapshot.getString("user_ID");
                        username = documentSnapshot.getString("user_name");
                        firstname = documentSnapshot.getString("user_Firstname");
                        lastname = documentSnapshot.getString("user_Lastname");
                        contactNum = documentSnapshot.getString("user_contactNum");
                        gender = documentSnapshot.getString("user_Gender");
                        birthdate = documentSnapshot.getString("user_birthDate");
                        address = documentSnapshot.getString("user_Address");

                        profile_userID.setText(id);
                        profile_username.setText(username);
                        profile_firstname.setText(firstname);
                        profile_lastname.setText(lastname);
                        profile_email.setText(email);
                        profile_contactNum.setText(contactNum);
                        profile_gender.setText(gender);
                        profile_birthdate.setText(birthdate);
                        profile_address.setText(address);
                    }
                });
    }

    public void openAlertBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Account");
        builder.setIcon(R.drawable.ic_baseline_error_outline_24);
        builder.setMessage("Are you sure you want to delete your account?This will permanently erase your account.");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (dialogInterface, i) -> db.collection("UserAccounts")
                .whereEqualTo("user_Email", email)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                        snapshot.getReference().delete();
                    }
                    mAuth.signOut();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    Toast.makeText(getContext(), "Account Successfully Deleted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to delete account: " + e.getMessage(), Toast.LENGTH_SHORT).show()));
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            Toast.makeText(getContext(), "Delete Account Cancelled!", Toast.LENGTH_SHORT).show();
            dialogInterface.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void openNavigationActivity() {
        Intent intent = new Intent(getContext(), NavigationActivity.class);
        startActivity(intent);
    }
}