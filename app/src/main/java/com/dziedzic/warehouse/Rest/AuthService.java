package com.dziedzic.warehouse.Rest;

import com.dziedzic.warehouse.Entity.AuthTokenDTO;

import retrofit2.Call;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthService {
    @FormUrlEncoded
    @POST("/auth/android_token_signin")
    Call<AuthTokenDTO> signInOAuth(@Field("token") String token);
}
