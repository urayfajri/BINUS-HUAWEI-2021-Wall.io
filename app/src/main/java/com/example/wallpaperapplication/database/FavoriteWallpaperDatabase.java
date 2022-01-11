package com.example.wallpaperapplication.database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.wallpaperapplication.dao.FavoriteWallpaperDao;
import com.example.wallpaperapplication.models.FavoritedWallpaper;

@Database(entities = {FavoritedWallpaper.class}, version = 1)
public abstract class FavoriteWallpaperDatabase extends RoomDatabase {

    public abstract FavoriteWallpaperDao favoriteWallpaperDao();

}
