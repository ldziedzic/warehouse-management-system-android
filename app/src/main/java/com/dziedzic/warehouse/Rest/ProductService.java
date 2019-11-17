package com.dziedzic.warehouse.Rest;

import com.dziedzic.warehouse.Entity.ProductDTO;
import com.dziedzic.warehouse.Entity.ProductEditDTO;

import org.apache.http.HttpHeaders;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ProductService {
    @GET("/api/warehouse/products/get_products")
    Call<List<ProductDTO>> getProducts(@Header(HttpHeaders.AUTHORIZATION) String authHeader);

    @POST("/api/warehouse/products/add_product")
    Call<Void> addProduct(@Header(HttpHeaders.AUTHORIZATION) String authHeader,
                          @Body ProductDTO productDTO);

    @PUT("/api/warehouse/products/increase_product_quantity")
    Call<ProductDTO> increaseProductQuantity(@Header(HttpHeaders.AUTHORIZATION) String authHeader,
                                       @Body ProductDTO productDTO);

    @PUT("/api/warehouse/products/decrease_product_quantity")
    Call<ProductDTO> decreaseProductQuantity(@Header(HttpHeaders.AUTHORIZATION) String authHeader,
                                       @Body ProductDTO productDTO);

    @PUT("/api/warehouse/products/edit_product")
    Call<Void> editProduct(@Header(HttpHeaders.AUTHORIZATION) String authHeader,
                           @Body ProductEditDTO productEditDTO);

    @PUT("/api/warehouse/products/restore_product")
    Call<ProductDTO> restoreProduct(@Header(HttpHeaders.AUTHORIZATION) String authHeader,
                           @Body ProductDTO productDTO);

    @PUT("/api/warehouse/products/remove_product")
    Call<ProductDTO> removeProduct(@Header(HttpHeaders.AUTHORIZATION) String authHeader,
                           @Body ProductDTO productDTO);
}
