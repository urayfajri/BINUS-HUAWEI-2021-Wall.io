package com.example.wallpaperapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wallpaperapplication.R;
import com.example.wallpaperapplication.fragments.WallpaperFragment;

import java.util.ArrayList;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolder> {

    ArrayList<String> wallpapers;
    Context context;

    public WallpaperAdapter(ArrayList<String> wallpapers, Context context) {
        this.wallpapers = wallpapers;
        this.context = context;
    }

    @NonNull
    @Override
    public WallpaperAdapter.WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallpaper_item, parent, false);

        return new WallpaperAdapter.WallpaperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperAdapter.WallpaperViewHolder holder, int position) {
        String wallpaper = wallpapers.get(position);
        Glide.with(context).load(wallpaper).into(holder.ivWallpaper);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment_container, (Fragment)new WallpaperFragment().newInstance(wallpaper)).addToBackStack(null).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return wallpapers.size();
    }

    public class WallpaperViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivWallpaper;

        public WallpaperViewHolder(@NonNull View itemView) {
            super(itemView);

            ivWallpaper = itemView.findViewById(R.id.ivWallpaper);
        }
    }
}
