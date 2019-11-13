package com.dziedzic.warehouse.Rest;

import com.dziedzic.warehouse.Entity.ProductDTO;

import org.apache.http.HttpHeaders;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ProductService {
    @GET("/api/warehouse/products/get_products")
    Call<List<ProductDTO>> getProducts(@Header(HttpHeaders.AUTHORIZATION)
                                           String authHeader);
}
