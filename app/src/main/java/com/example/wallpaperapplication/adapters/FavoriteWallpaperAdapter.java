package com.example.wallpaperapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wallpaperapplication.MainActivity;
import com.example.wallpaperapplication.R;
import com.example.wallpaperapplication.fragments.WallpaperFragment;
import com.example.wallpaperapplication.models.FavoritedWallpaper;
import com.example.wallpaperapplication.models.User;

import java.util.ArrayList;
import java.util.List;

public class FavoriteWallpaperAdapter extends RecyclerView.Adapter<FavoriteWallpaperAdapter.WallpaperViewHolder>{

    private ArrayList<FavoritedWallpaper> wallpapers;
    //user can be changed to whatever huawei auth identifier use
    private User user;
    Context context;

    public FavoriteWallpaperAdapter(User user, ArrayList<FavoritedWallpaper> wallpapers, Context context) {
        this.user = user;
        this.wallpapers = wallpapers;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteWallpaperAdapter.WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_wallpaper_item, parent, false);

        return new FavoriteWallpaperAdapter.WallpaperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteWallpaperAdapter.WallpaperViewHolder holder, int position) {
        FavoritedWallpaper wallpaper = wallpapers.get(position);
        Glide.with(context).load(wallpaper.getWallpaperUrl()).into(holder.ivWallpaper);
        holder.itemView.setOnClickListener(view -> {
            FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fragment_container, (Fragment)new WallpaperFragment().newInstance(wallpaper.getWallpaperUrl())).addToBackStack(null).commit();
        });

        holder.btnRemoveFavorite.setOnClickListener(view -> {
            int deleted = MainActivity.favoriteWallpaperDatabase.favoriteWallpaperDao().removeFromFavorite(user.getEmail(),wallpaper.getWallpaperUrl());
            if(deleted != 0){
                wallpapers.remove(wallpaper);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount() - position);
                Toast.makeText(context,"Removed from favorites", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context,"Failed to remove from favorites...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return wallpapers.size();
    }

    public class WallpaperViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivWallpaper;
        private ImageButton btnRemoveFavorite;

        public WallpaperViewHolder(@NonNull View itemView) {
            super(itemView);

            ivWallpaper = itemView.findViewById(R.id.ivWallpaper);
            btnRemoveFavorite = itemView.findViewById(R.id.img_btn_fav);
        }
    }
}
