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
import com.example.app.NavigationActivity;
import com.example.app.NotificationActivity;
import com.example.app.R;
import com.example.app.Retail_LoginActivity;
import com.example.app.TermsOfServiceActivity;
import com.example.app.UpdateProfileActivity;

public class Tech_ManagerFragment extends Fragment {

    Activity tech_notifications, tech_edit, tech_retailer, tech_buyer, tech_terms, tech_about, tech_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        tech_notifications = getActivity();
        tech_edit = getActivity();
        tech_retailer = getActivity();
        tech_buyer = getActivity();
        tech_terms = getActivity();
        tech_about = getActivity();
        tech_logout = getActivity();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tech__manager, container, false);
    }

    public void onStart() {
        super.onStart();
        //Direct to Notifications Activity
        TextView techmanage_notifs = (TextView) tech_notifications.findViewById(R.id.techmanage_notifications);

        techmanage_notifs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tech_notifications, NotificationActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Edit Profile Activity
        TextView techmanage_edit = (TextView) tech_edit.findViewById(R.id.techedit_profile);

        techmanage_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tech_edit, UpdateProfileActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Switch to Retailer Activity
        TextView techmanage_retailer = (TextView) tech_retailer.findViewById(R.id.techswitch_to_retailer);

        techmanage_retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tech_retailer, Retail_LoginActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Switch to Buyer Activity
        TextView techmanage_buyer = (TextView) tech_buyer.findViewById(R.id.techswitch_to_buyer);

        techmanage_buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tech_buyer, NavigationActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Terms of Service Activity
        TextView techmanage_terms = (TextView) tech_terms.findViewById(R.id.techterms_of_service);

        techmanage_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tech_terms, TermsOfServiceActivity.class);
                startActivity(intent);
            }
        });

        //Direct to About Us Activity
        TextView techmanage_about = (TextView) tech_about.findViewById(R.id.techmanage_about_us);

        techmanage_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tech_about, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Main Activity
        Button techmanage_logout = (Button) tech_logout.findViewById(R.id.techmanage_logout_button);

        techmanage_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tech_logout, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}