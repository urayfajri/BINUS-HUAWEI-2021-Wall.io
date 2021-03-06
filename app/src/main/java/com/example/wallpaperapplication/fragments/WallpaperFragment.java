package com.example.wallpaperapplication.fragments;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.wallpaperapplication.MainActivity;
import com.example.wallpaperapplication.R;
import com.example.wallpaperapplication.models.FavoritedWallpaper;
import com.example.wallpaperapplication.models.User;
import com.example.wallpaperapplication.session.SharedPreference;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;

public class WallpaperFragment extends Fragment {

    private ImageView ivWallpaper;
    private Button btnSetWallpaper;
    private ImageButton btnAddToFav;
    private String imgUrl;
    WallpaperManager wallpaperManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wallpaper, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Wallpaper");

        ivWallpaper = view.findViewById(R.id.ivWallpaper);
        btnSetWallpaper = view.findViewById(R.id.btnSetWallpaper);
        btnAddToFav = view.findViewById(R.id.btn_add_to_fav);
        imgUrl = getArguments().getString("imgUrl");

        // imgUrl = getActivity().getIntent().getStringExtra("imgUrl");

        Glide.with(this).load(imgUrl).into(ivWallpaper);

        wallpaperManager = WallpaperManager.getInstance(getActivity().getApplicationContext());

        btnSetWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getActivity()).asBitmap().load(imgUrl).listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Toast.makeText(getActivity(), "Fail to load image..", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        try {
                            wallpaperManager.setBitmap(resource);
                        }catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Fail to set wallpaper..", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                }).submit();
                FancyToast.makeText(getActivity(), "Wallpaper set to Home Screen", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
            }
        });

        btnAddToFav.setOnClickListener(view1 -> {
            SharedPreference sharedPreference = new SharedPreference(requireContext());
            User user = sharedPreference.load();
            FavoritedWallpaper favoritedWallpaper = new FavoritedWallpaper(user.getEmail(), imgUrl);

            //NEED TO BE CHANGED IN isFavorited PARAMETERS TO FIT ACCORDING TO HUAWEI KIT API
            if(MainActivity.favoriteWallpaperDatabase.favoriteWallpaperDao().isFavorited(user.getEmail(), imgUrl)!=1) {
                MainActivity.favoriteWallpaperDatabase.favoriteWallpaperDao().addFavorite(favoritedWallpaper);
                Toast.makeText(requireContext(),"Added to favorite", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(requireContext(),"This wallpaper is already favorited!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public static WallpaperFragment newInstance(String imgUrl) {
        WallpaperFragment wallpaperFragment = new WallpaperFragment();
        Bundle args = new Bundle();
        args.putString("imgUrl", imgUrl);
        wallpaperFragment.setArguments(args);
        return wallpaperFragment;
    }
}