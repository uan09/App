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
import com.example.app.Tech_MessagesActivity;
import com.example.app.Tech_RequestsActivity;

public class Tech_DashboardFragment extends Fragment {

    Activity tech_requests, tech_messages, tech_forums;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        tech_requests = getActivity();
        tech_messages = getActivity();
        tech_forums = getActivity();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tech__dashboard, container, false);
    }

    public void onStart() {
        super.onStart();
        //Direct to Requests Activity
        CardView dashboard_request = (CardView) tech_requests.findViewById(R.id.techrequest_card);
        dashboard_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tech_requests, Tech_RequestsActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Messages Activity
        CardView dashboard_messages = (CardView) tech_messages.findViewById(R.id.techmessages_card);
        dashboard_messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tech_messages, Tech_MessagesActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Forums Activity
        CardView dashboard_techforums = (CardView) tech_forums.findViewById(R.id.techforums_card);
        dashboard_techforums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tech_forums, ForumActivity.class);
                startActivity(intent);
            }
        });
    }
}