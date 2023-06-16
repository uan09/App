package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app.ui.adapters.NotificationAdapter;
import com.example.app.ui.fragments.ManagerFragment;
import com.example.app.ui.notifications.NotificationGetterSetter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView notifRecyclerView;
    private ArrayList<NotificationGetterSetter> notifications;
    private NotificationAdapter notificationAdapter;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notifRecyclerView = findViewById(R.id.notif_recyclerview_view);
        notifRecyclerView.setHasFixedSize(true);
        notifRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        notifications = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(this, notifications);
        notifRecyclerView.setAdapter(notificationAdapter);

        String userEmail = getIntent().getStringExtra("Email");
        getUserNotifications(userEmail);

        backButton = findViewById(R.id.backbutton1);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_manage, new ManagerFragment()).commit();
            }
        });
    }

    private void getUserNotifications(String userEmail) {
        db.collection("UserAccounts")
                .whereEqualTo("user_Email", userEmail)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            DocumentSnapshot userDoc = queryDocumentSnapshots.getDocuments().get(0);
                            db.collection("UserAccounts")
                                    .document(userDoc.getId())
                                    .collection("Notifications")
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            notifications.clear();
                                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                                String nDetails = document.getString("nDetails");
                                                String nImage = document.getString("nImage");
                                                NotificationGetterSetter notification = new NotificationGetterSetter(nDetails, nImage);
                                                notifications.add(notification);
                                            }
                                            notificationAdapter.notifyDataSetChanged();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(NotificationActivity.this, "Firestore Error", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(NotificationActivity.this, "No user found with email " + userEmail, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NotificationActivity.this, "Firestore Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}