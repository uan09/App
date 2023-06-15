package com.example.app.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.app.AssistanceActivity;
import com.example.app.BrowseItemsActivity;
import com.example.app.ForumActivity;
import com.example.app.NewBuildActivity;
import com.example.app.R;

public class DashboardFragment extends Fragment {

    Activity build, browse, technician, forums;
    String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("Email")) {
            email = bundle.getString("Email");
        }

        build = getActivity();
        browse = getActivity();
        technician = getActivity();
        forums = getActivity();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    public void onStart() {
        super.onStart();
        //Direct to New Build Activity
        CardView dashboard_build = (CardView) build.findViewById(R.id.new_build_card);
        dashboard_build.setOnClickListener(view -> {

            Bundle bundle = new Bundle();
            bundle.putString("Email", email);
            Intent intent = new Intent(build, NewBuildActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        CardView browse_items = (CardView) build.findViewById(R.id.browse_items_card);
        browse_items.setOnClickListener(view -> {

            Bundle bundle = new Bundle();
            bundle.putString("Email", email);
            Intent intent = new Intent(build, BrowseItemsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        //Direct to Assistance Activity
        CardView dashboard_technician = (CardView) technician.findViewById(R.id.look_technician_card);
        dashboard_technician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(technician, AssistanceActivity.class);
                startActivity(intent);
            }
        });

        //Direct to Forums Activity
        CardView dashboard_forums = (CardView) forums.findViewById(R.id.forums_card);
        dashboard_forums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forums, ForumActivity.class);
                startActivity(intent);
            }
        });
    }
}