package com.music.aman.musicg.Models;

/**
 * Created by kipl217 on 9/8/2015.
 */
public class Users {
    private String Name;
    private boolean isSubscribed;
    private String Email;
    private String password;

    public Users() {
    }

    public Users(String email, boolean subscribed, String password) {
        Email = email;
        isSubscribed = subscribed;
        this.password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
