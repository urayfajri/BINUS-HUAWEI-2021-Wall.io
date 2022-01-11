package com.example.wallpaperapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wallpaperapplication.models.FavoritedWallpaper;

import java.util.List;

//CHANGE ALL userEmail TO FIT THE HUAWEI AUTH API IDENTIFIER
@Dao
public interface FavoriteWallpaperDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addFavorite(FavoritedWallpaper favoritedWallpaper);

    @Query("SELECT * FROM favorite_wallpaper WHERE userEmail=:userEmail")
    List<FavoritedWallpaper> getUserFavoritedData(String userEmail);

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_wallpaper WHERE userEmail=:userEmail AND wallpaperUrl=:wallpaperUrl)")
    int isFavorited(String userEmail, String wallpaperUrl);

    @Query("DELETE FROM favorite_wallpaper WHERE userEmail=:userEmail AND wallpaperUrl=:wallpaperUrl")
    int removeFromFavorite(String userEmail, String wallpaperUrl);
}
