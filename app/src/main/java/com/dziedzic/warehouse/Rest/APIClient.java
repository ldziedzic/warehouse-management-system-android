package com.dziedzic.warehouse.Rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class APIClient {

    private static final String scheme = "https";
    private static final String hostIP = "lukasz-d.pl";
    private static final int port = 443;
    private static final HttpUrl hostUrl = new HttpUrl.Builder()
            .scheme(scheme)
            .host(hostIP)
            .port(port)
            .build();

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new ContentTypeInterceptor())
            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create(getDefaultObjectMapper()))
            .client(client)
            .baseUrl(hostUrl)
            .build();


    public static AuthService getAuthService() {
        return retrofit.create(AuthService.class);
    }

    public static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setDateFormat(new StdDateFormat());

        return mapper;
    }
}
