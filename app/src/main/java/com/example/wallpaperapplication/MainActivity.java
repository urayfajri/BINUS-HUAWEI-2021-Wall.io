package com.example.wallpaperapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.wallpaperapplication.database.FavoriteWallpaperDatabase;
import com.example.wallpaperapplication.fragments.AboutFragment;
import com.example.wallpaperapplication.fragments.FavoriteFragment;
import com.example.wallpaperapplication.fragments.HelpFragment;
import com.example.wallpaperapplication.fragments.HomeFragment;
import com.example.wallpaperapplication.fragments.LogoutFragment;
import com.example.wallpaperapplication.models.User;
import com.example.wallpaperapplication.session.SharedPreference;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    public static FavoriteWallpaperDatabase favoriteWallpaperDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView;

        TextView navEmail;

        loadFragment(new HomeFragment());

        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        View headerView = navigationView.getHeaderView(0);
        navEmail = headerView.findViewById(R.id.tv_navemail);

        setSupportActionBar(toolbar);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setTitle("Home");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        SharedPreference sharedPreference = new SharedPreference(MainActivity.this);
        User user = sharedPreference.load();
        navEmail.setText(user.getEmail());

        favoriteWallpaperDatabase = Room.databaseBuilder(getApplicationContext(), FavoriteWallpaperDatabase.class, "wallpaperFavDB")
                                    .allowMainThreadQueries().build();
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_my_favorite:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FavoriteFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_about_app:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_help:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HelpFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_log_out:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LogoutFragment()).addToBackStack(null).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}