package com.example.wallpaperapplication.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wallpaperapplication.BuildConfig;
import com.example.wallpaperapplication.R;

public class AboutFragment extends Fragment {

    TextView tvVersion, tvOS;
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;
    String release = Build.VERSION.RELEASE;
    int sdkVersion = Build.VERSION.SDK_INT;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("About App");
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        tvVersion = (TextView)view.findViewById(R.id.tvVersion);
        tvOS = (TextView)view.findViewById(R.id.tvOS);

        tvVersion.setText("Version code: " + versionCode + "\nVersion name: " + versionName);
        tvOS.setText("Android SDK: " + sdkVersion + " (" + release + ")");

        return view;
    }
}