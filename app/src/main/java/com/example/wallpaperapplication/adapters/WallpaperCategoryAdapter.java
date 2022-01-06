package com.example.wallpaperapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wallpaperapplication.R;
import com.example.wallpaperapplication.interfaces.WallpaperCategoryClickable;
import com.example.wallpaperapplication.models.WallpaperCategory;

import java.util.ArrayList;

public class WallpaperCategoryAdapter extends RecyclerView.Adapter<WallpaperCategoryAdapter.WallpaperCategoryViewHolder> {

    private ArrayList<WallpaperCategory> wallpaperCategories;
    private Context context;
    private WallpaperCategoryClickable listener;

    public WallpaperCategoryAdapter(ArrayList<WallpaperCategory> wallpaperCategories, Context context, WallpaperCategoryClickable listener) {
        this.wallpaperCategories = wallpaperCategories;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WallpaperCategoryAdapter.WallpaperCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_wallpaper_item, parent, false);
        return new WallpaperCategoryAdapter.WallpaperCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperCategoryAdapter.WallpaperCategoryViewHolder holder, int position) {
        WallpaperCategory wallpaperCategory = wallpaperCategories.get(position);
        holder.tvWallpaperCategory.setText(wallpaperCategory.getCategoryName());
        Glide.with(context).load(wallpaperCategory.getCategoryUrl()).into(holder.ivWallpaperCategory);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onWallpaperCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wallpaperCategories.size();
    }

    public class WallpaperCategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tvWallpaperCategory;
        private ImageView ivWallpaperCategory;

        public WallpaperCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvWallpaperCategory = itemView.findViewById(R.id.tvWallpaperCategory);
            ivWallpaperCategory = itemView.findViewById(R.id.ivWallpaperCategory);
        }
    }
}
