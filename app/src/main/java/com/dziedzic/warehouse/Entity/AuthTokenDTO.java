package com.dziedzic.warehouse.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AuthTokenDTO  {
    @JsonProperty("id_token")
    private String idToken;

    public AuthTokenDTO(String idToken) {
        this.idToken = idToken;
    }

    public AuthTokenDTO() {
    }

    String getIdToken() {
        return idToken;
    }

    void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
