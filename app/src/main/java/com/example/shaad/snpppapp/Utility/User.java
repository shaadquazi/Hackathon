package com.example.shaad.snpppapp.Utility;

/**
 * Created by Shaad on 30-03-2018.
 */

public class User {
    private String Name;
    private String status;

    public User() {
    }

    public User(String name, String status) {
        Name = name;
        this.status = status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
