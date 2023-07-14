package com.company.impl;


import com.company.Info;

public class AppInfo implements Info {
    public AppInfo(int id, String token){
        this.id = id;
        this.token = token;
    }
    private int id ;
    private String token = null;

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
