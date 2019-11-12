package com.dziedzic.warehouse.Entity;

public class User {
    private String refreshToken;

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken(){
        return this.refreshToken;
    }

}
