package com.example.wallpaperapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wallpaperapplication.R;
import com.example.wallpaperapplication.splashscreen.SplashActivity;

public class HelpFragment extends Fragment {

    EditText etContact;
    Button btnSend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Help");
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        etContact = (EditText)view.findViewById(R.id.etContact);
        btnSend = (Button) view.findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etContact.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "Please write anything you want to ask us!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(view.getContext(), "Successfully Sent", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), SplashActivity.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}