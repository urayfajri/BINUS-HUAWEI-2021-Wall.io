package com.example.wallpaperapplication.models;

public class User {

    public int id;
    public String Name;
    public String Email;
    public String Password;

    public User() {

    }

    public User(String name, String email, String password) {
        Name = name;
        Email = email;
        Password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
