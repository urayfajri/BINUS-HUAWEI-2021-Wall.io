package com.example.wallpaperapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wallpaperapplication.MainActivity;
import com.example.wallpaperapplication.R;
import com.example.wallpaperapplication.loginregister.LoginActivity;

public class LogoutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logout, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Log Out");

        TextView cancel;
        TextView logout;

        cancel = view.findViewById(R.id.tv_cancel_logout);
        logout = view.findViewById(R.id.tv_logout);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE).edit().clear().apply();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}