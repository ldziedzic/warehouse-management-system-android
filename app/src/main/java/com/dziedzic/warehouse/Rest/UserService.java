package com.dziedzic.warehouse.Rest;

import com.dziedzic.warehouse.Entity.User;

import org.apache.http.HttpHeaders;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;


public interface UserService {
    @GET("/api/warehouse/users/get_current")
    Call<User> getCurrentUser(@Header(HttpHeaders.AUTHORIZATION) String authHeader);
}
