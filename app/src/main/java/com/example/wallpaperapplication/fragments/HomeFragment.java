package com.example.wallpaperapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wallpaperapplication.MainActivity;
import com.example.wallpaperapplication.R;
import com.example.wallpaperapplication.adapters.WallpaperAdapter;
import com.example.wallpaperapplication.adapters.WallpaperCategoryAdapter;
import com.example.wallpaperapplication.interfaces.WallpaperCategoryClickable;
import com.example.wallpaperapplication.models.WallpaperCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment implements WallpaperCategoryClickable {

    private EditText etSearchWallpaper;
    private ImageView ivSearchWallpaper;
    private RecyclerView rvWallpaperCategory, rvWallpapers;
    private ProgressBar pbLoading;
    private ArrayList<String> wallpapers;
    private ArrayList<WallpaperCategory> wallpaperCategories;
    private WallpaperCategoryAdapter wallpaperCategoryAdapter;
    private WallpaperAdapter wallpaperAdapter;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_home, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Home");

        etSearchWallpaper = view.findViewById(R.id.etSearchWallpaper);
        ivSearchWallpaper = view.findViewById(R.id.ivSearchWallpaper);
        rvWallpaperCategory = view.findViewById(R.id.rvWallpaperCategory);
        rvWallpapers = view.findViewById(R.id.rvWallpapers);
        pbLoading = view.findViewById(R.id.pbLoading);
        wallpapers = new ArrayList<>();
        wallpaperCategories = new ArrayList<>();

        // wallpaper category
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false);
        rvWallpaperCategory.setLayoutManager(linearLayoutManager);
        wallpaperCategoryAdapter = new WallpaperCategoryAdapter(wallpaperCategories, this.getContext(), this::onWallpaperCategoryClick);
        rvWallpaperCategory.setAdapter(wallpaperCategoryAdapter);

        // wallpaper collection
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        rvWallpapers.setLayoutManager(gridLayoutManager);
        wallpaperAdapter = new WallpaperAdapter(wallpapers, this.getContext());
        rvWallpapers.setAdapter(wallpaperAdapter);

        getWallpaperCategories();
        getWallpapers();

        ivSearchWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchStr = etSearchWallpaper.getText().toString();
                if(searchStr.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your search query", Toast.LENGTH_SHORT).show();
                } else {
                    getWallpaperByCategory(searchStr);
                }
            }
        });

        return view;
    }

    private void getWallpaperCategories() {
        wallpaperCategories.add(new WallpaperCategory("technology","https://www.pexels.com/photo/black-flat-screen-computer-monitor-1714208/"));
        wallpaperCategories.add(new WallpaperCategory("programming","https://www.pexels.com/photo/text-2061168/"));
        wallpaperCategories.add(new WallpaperCategory("nature","https://www.pexels.com/photo/person-walking-between-green-forest-trees-15286/"));
        wallpaperCategoryAdapter.notifyDataSetChanged();
    }

    private void getWallpapers() {
        wallpapers.clear();
        pbLoading.setVisibility(View.VISIBLE);

        String url = "https://api.pexels.com/v1/curated?per_page=30&page=1";

        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                pbLoading.setVisibility(View.GONE);
                try {
                    JSONArray photoArray = response.getJSONArray("photos");
                    for (int i=0; i<photoArray.length();i++) {
                        JSONObject photoObj = photoArray.getJSONObject(i);
                        String imgUrl = photoObj.getJSONObject("src").getString("portrait");
                        wallpapers.add(imgUrl);
                    }
                    wallpaperAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Fail to load wallpapers..", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization","563492ad6f9170000100000107cbb7a5027f44f6b19b66d0fdc4ccb2");

                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private void getWallpaperByCategory(String category) {
        wallpapers.clear();
        pbLoading.setVisibility(View.VISIBLE);

        String url = "https://api.pexels.com/v1/search?query="+ category +"&per_page=30&page=1";

        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                pbLoading.setVisibility(View.GONE);
                try {
                    JSONArray photoArray = response.getJSONArray("photos");
                    for (int i=0; i<photoArray.length();i++) {
                        JSONObject photoObj = photoArray.getJSONObject(i);
                        String imgUrl = photoObj.getJSONObject("src").getString("portrait");
                        wallpapers.add(imgUrl);
                    }
                    wallpaperAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Fail to load wallpapers..", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization","563492ad6f9170000100000107cbb7a5027f44f6b19b66d0fdc4ccb2");

                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onWallpaperCategoryClick(int position) {
        String category = wallpaperCategories.get(position).getCategoryName();
        getWallpaperByCategory(category);
    }
}