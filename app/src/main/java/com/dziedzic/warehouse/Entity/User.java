package com.dziedzic.warehouse.Entity;

public class User {
    private String refreshToken;
    private String name;
    private String email;
    private String role;

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken(){
        return this.refreshToken;
    }

    public String getBearerToken(){
        return "Bearer " + this.refreshToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
