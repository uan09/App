package com.example.app.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.app.AboutUsActivity;
import com.example.app.LoginActivity;
import com.example.app.NotificationActivity;
import com.example.app.R;
import com.example.app.Retail_LoginActivity;
import com.example.app.Tech_LoginActivity;
import com.example.app.TermsOfServiceActivity;
import com.example.app.UpdateProfileActivity;

public class ManagerFragment extends Fragment {

    Activity notifications, edit, retailer, technician, terms, about, logout;
    String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        notifications = getActivity();
        edit = getActivity();
        retailer = getActivity();
        technician = getActivity();
        terms = getActivity();
        about = getActivity();
        logout = getActivity();

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("Email")) {
            email = bundle.getString("Email");
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manager, container, false);
    }

    public void onStart() {
        super.onStart();
        //Direct to Notifications Activity
        TextView manage_notifications = (TextView) notifications.findViewById(R.id.manage_notifications);

        manage_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notifications, NotificationActivity.class);
                intent.putExtra("Email", email);
                startActivity(intent);
            }
        });

        //Direct to Edit Profile Activity
        TextView manage_edit = (TextView) edit.findViewById(R.id.edit_profile);

        manage_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(edit, UpdateProfileActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Switch to Retailer Activity
        TextView manage_retailer = (TextView) retailer.findViewById(R.id.switch_to_retailer);

        manage_retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(retailer, Retail_LoginActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Switch to Technician Activity
        TextView manage_tech = (TextView) technician.findViewById(R.id.switch_to_technician);

        manage_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(technician, Tech_LoginActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Terms of Service Activity
        TextView manage_terms = (TextView) terms.findViewById(R.id.terms_of_service);

        manage_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(terms, TermsOfServiceActivity.class);
                startActivity(intent);
            }
        });

        //Direct to About Us Activity
        TextView manage_about = (TextView) about.findViewById(R.id.manage_about_us);

        manage_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(about, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Main Activity
        Button manage_logout = (Button) logout.findViewById(R.id.manage_logout_button);

        manage_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(logout, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}