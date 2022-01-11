package com.example.wallpaperapplication.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

//CHANGE "userEmail" IN indices value TO WHATEVER INSIDE OF ColumnInfo name WHEN FITTING IT WITH HUAWEI AUTH API
@Entity(tableName = "favorite_wallpaper", indices = {@Index(value = {"userEmail", "wallpaperUrl"}, unique = true)})
public class FavoritedWallpaper {

    @PrimaryKey(autoGenerate = true)
    private int fav_user_wp_id;

    //CHANGE THIS TO FIT THE HUAWEI AUTH API IDENTIFIER (attribute name & column info name)
    @NonNull
    @ColumnInfo(name = "userEmail")
    private String userEmail;

    @NonNull
    @ColumnInfo(name = "wallpaperUrl")
    private String wallpaperUrl;

    public FavoritedWallpaper(@NonNull String userEmail, @NonNull String wallpaperUrl) {
        this.userEmail = userEmail;
        this.wallpaperUrl = wallpaperUrl;
    }

    public int getFav_user_wp_id() {
        return fav_user_wp_id;
    }

    public void setFav_user_wp_id(int fav_user_wp_id) {
        this.fav_user_wp_id = fav_user_wp_id;
    }

    @NonNull
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(@NonNull String userEmail) {
        this.userEmail = userEmail;
    }

    @NonNull
    public String getWallpaperUrl() {
        return wallpaperUrl;
    }

    public void setWallpaperUrl(@NonNull String wallpaperUrl) {
        this.wallpaperUrl = wallpaperUrl;
    }
}
