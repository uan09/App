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
import com.example.app.Tech_LoginActivity;
import com.example.app.TermsOfServiceActivity;
import com.example.app.UpdateProfileActivity;

public class Retail_ManagerFragment extends Fragment {

    Activity retail_notifications, retail_edit, retail_technician, retail_buyer, retail_terms, retail_about, retail_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        retail_notifications = getActivity();
        retail_edit = getActivity();
        retail_technician = getActivity();
        retail_buyer = getActivity();
        retail_terms = getActivity();
        retail_about = getActivity();
        retail_logout = getActivity();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_retail__manager, container, false);
    }

    public void onStart() {
        super.onStart();
        //Direct to Notifications Activity
        TextView retailmanage_notifs = (TextView) retail_notifications.findViewById(R.id.retailmanage_notifications);

        retailmanage_notifs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(retail_notifications, NotificationActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Edit Profile Activity
        TextView retailmanage_edit = (TextView) retail_edit.findViewById(R.id.retailedit_profile);

        retailmanage_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(retail_edit, UpdateProfileActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Switch to Technician Activity
        TextView retailmanage_technician = (TextView) retail_technician.findViewById(R.id.retailswitch_to_technician);

        retailmanage_technician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(retail_technician, Tech_LoginActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Switch to Buyer Activity
        TextView retailmanage_buyer = (TextView) retail_buyer.findViewById(R.id.retailswitch_to_buyer);

        retailmanage_buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(retail_buyer, NavigationActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Terms of Service Activity
        TextView retailmanage_terms = (TextView) retail_terms.findViewById(R.id.retailterms_of_service);

        retailmanage_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(retail_terms, TermsOfServiceActivity.class);
                startActivity(intent);
            }
        });

        //Direct to About Us Activity
        TextView retailmanage_about = (TextView) retail_about.findViewById(R.id.retailmanage_about_us);

        retailmanage_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(retail_about, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Main Activity
        Button retailmanage_logout = (Button) retail_logout.findViewById(R.id.retailmanage_logout_button);

        retailmanage_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(retail_logout, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}