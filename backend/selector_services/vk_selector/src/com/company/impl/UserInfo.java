package com.company.impl;


import com.company.Info;

public class UserInfo implements Info {
    public UserInfo(int userId, int vkId, int time, String token) {
        this.userId = userId;
        this.time = time;
        this.vkId = vkId;
        this.token = token;
    }

    private int vkId, time, userId;
    private String token;

    public int getVkId() {
        return vkId;
    }
    public int getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public int getTime() {
        return time;
    }
}
