package com.example.app.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.ForumActivity;
import com.example.app.R;
import com.example.app.Retail_ManageProductsActivity;
import com.example.app.Retail_MessagesActivity;
import com.example.app.Tech_MessagesActivity;
import com.example.app.Tech_RequestsActivity;

public class Retail_DashboardFragment extends Fragment {

    Activity retail_manageproducts, retail_messages, retail_forums;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        retail_manageproducts = getActivity();
        retail_messages = getActivity();
        retail_forums = getActivity();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_retail__dashboard, container, false);
    }

    public void onStart() {
        super.onStart();
        //Direct to Requests Activity
        CardView dashboard_manageproducts = (CardView) retail_manageproducts.findViewById(R.id.retailmanageproducts_card);
        dashboard_manageproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(retail_manageproducts, Retail_ManageProductsActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Messages Activity
        CardView dashboard_messages = (CardView) retail_messages.findViewById(R.id.retailmessages_card);
        dashboard_messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(retail_messages, Retail_MessagesActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Forums Activity
        CardView dashboard_retailforums = (CardView) retail_forums.findViewById(R.id.retailforums_card);
        dashboard_retailforums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(retail_forums, ForumActivity.class);
                startActivity(intent);
            }
        });
    }
}