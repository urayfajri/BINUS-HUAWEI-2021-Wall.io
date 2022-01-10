package com.example.wallpaperapplication.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.wallpaperapplication.R;
import com.example.wallpaperapplication.adapters.WallpaperAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FavoriteFragment extends Fragment {

    private RecyclerView rvFavWallpapers;
    private ProgressBar pbLoading;
    private ArrayList<String> wallpapers;
    private WallpaperAdapter wallpaperFavoriteAdapter;
    TextView tvEmpty;
    private View view;

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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        rvFavWallpapers.setLayoutManager(gridLayoutManager);
        wallpaperFavoriteAdapter = new WallpaperAdapter(wallpapers, this.getContext());
        rvFavWallpapers.setAdapter(wallpaperFavoriteAdapter);

        setView();

        return view;
    }

    //currently placeholder by getting random wallpaper from pexels, still need to figure out what needs to be saved into db
    //probably saves the url
    private void getFavoritedWallpapers() {
        wallpapers.clear();
        pbLoading.setVisibility(View.VISIBLE);

        String url = "https://api.pexels.com/v1/curated?per_page=30&page=1";

        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            pbLoading.setVisibility(View.GONE);
            try {
                JSONArray photoArray = response.getJSONArray("photos");
                for (int i=0; i<photoArray.length();i++) {
                    JSONObject photoObj = photoArray.getJSONObject(i);
                    String imgUrl = photoObj.getJSONObject("src").getString("portrait");
                    wallpapers.add(imgUrl);
                }
                wallpaperFavoriteAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getActivity(), "Fail to load wallpapers..", Toast.LENGTH_SHORT).show()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization","563492ad6f9170000100000107cbb7a5027f44f6b19b66d0fdc4ccb2");

                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    //temporary
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