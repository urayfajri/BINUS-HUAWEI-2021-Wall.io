package com.example.wallpaperapplication.session;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.wallpaperapplication.models.User;

public class SharedPreference {

    private SharedPreferences sharedPreferences;

    public SharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
    }

    public void save(User user)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name", user.getName());
        editor.putString("Email", user.getEmail());
        editor.putString("Password", user.getPassword());
        editor.apply();
    }

    public User load()
    {
        User user = new User();
        user.setName(sharedPreferences.getString("Name", ""));
        user.setEmail(sharedPreferences.getString("Email", ""));
        user.setPassword(sharedPreferences.getString("Password", ""));
        return user;
    }

}
