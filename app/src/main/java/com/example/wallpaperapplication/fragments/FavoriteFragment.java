package com.example.wallpaperapplication.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wallpaperapplication.MainActivity;
import com.example.wallpaperapplication.R;
import com.example.wallpaperapplication.adapters.FavoriteWallpaperAdapter;
import com.example.wallpaperapplication.adapters.WallpaperAdapter;
import com.example.wallpaperapplication.models.FavoritedWallpaper;
import com.example.wallpaperapplication.models.User;
import com.example.wallpaperapplication.session.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FavoriteFragment extends Fragment {

    private RecyclerView rvFavWallpapers;
    private ProgressBar pbLoading;
    private ArrayList<FavoritedWallpaper> wallpapers;
    private FavoriteWallpaperAdapter wallpaperFavoriteAdapter;
    TextView tvEmpty;
    private View view;
    private User loggedInUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Favorite");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvFavWallpapers = view.findViewById(R.id.rvWallpapers);
        pbLoading = view.findViewById(R.id.pbLoading);
        tvEmpty = view.findViewById(R.id.tv_empty);
        wallpapers = new ArrayList<>();

        SharedPreference sharedPreference = new SharedPreference(requireContext());
        loggedInUser = sharedPreference.load();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        rvFavWallpapers.setLayoutManager(gridLayoutManager);
        wallpaperFavoriteAdapter = new FavoriteWallpaperAdapter(loggedInUser,wallpapers, this.getContext());
        rvFavWallpapers.setAdapter(wallpaperFavoriteAdapter);

        setView();
        return view;
    }

    //currently placeholder by getting random wallpaper from pexels, still need to figure out what needs to be saved into db
    //probably saves the url
    private void getFavoritedWallpapers() {
        wallpapers.clear();
        pbLoading.setVisibility(View.VISIBLE);

        wallpapers.addAll(MainActivity.favoriteWallpaperDatabase.favoriteWallpaperDao().getUserFavoritedData(loggedInUser.getEmail()));
        wallpaperFavoriteAdapter.notifyDataSetChanged();
        pbLoading.setVisibility(View.GONE);
    }

    private void setView(){
        getFavoritedWallpapers();
        if(wallpapers.size() < 1){
            rvFavWallpapers.setVisibility(View.INVISIBLE);
            tvEmpty.setVisibility(View.VISIBLE);
        }
        else{
            rvFavWallpapers.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.INVISIBLE);
        }
    }

}