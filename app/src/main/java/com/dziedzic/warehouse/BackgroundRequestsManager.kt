package com.dziedzic.warehouse

import android.content.ContentValues.TAG
import android.util.Log
import com.dziedzic.warehouse.Entity.ProductDTO
import com.dziedzic.warehouse.Entity.ProductEditDTO
import com.dziedzic.warehouse.Entity.RequestManagerEntity
import com.dziedzic.warehouse.Rest.APIClient

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BackgroundRequestsManager {
    private val productService = APIClient.getProductService()
    private val requestManagerEntities = ArrayList<RequestManagerEntity>()

    fun run() {
        for (requestManagerEntity in requestManagerEntities) {
            performRequest(requestManagerEntity)
            Thread.sleep(500)
        }
        requestManagerEntities.clear()
    }

    fun addRequestToManager(requestManagerEntity: RequestManagerEntity) {
        requestManagerEntities.add(requestManagerEntity)
    }

    private fun performRequest(requestManagerEntity: RequestManagerEntity) {
        when (requestManagerEntity.requestName) {
            "getProducts" -> {
            }
            "addProduct" -> {
                addProduct(requestManagerEntity.productDTO)
            }
            "increaseProductQuantity" -> {
                increaseProductQuantity(requestManagerEntity.productDTO)
            }
            "decreaseProductQuantity" -> {
                decreaseProductQuantity(requestManagerEntity.productDTO)
            }
            "editProduct" -> {
                editProduct(requestManagerEntity.productEditDTO)
            }
            "restoreProduct" -> {
                restoreProduct(requestManagerEntity.productDTO)
            }
            "removeProduct" -> {
                removeProduct(requestManagerEntity.productDTO)
            }
            else -> {
            }
        }

    }


    private fun increaseProductQuantity(productDTO: ProductDTO) {
        val call = productService.increaseProductQuantity(MainActivity.user.bearerToken, productDTO)
        call.enqueue(getProductDTOFetchCallback())
    }

    private fun decreaseProductQuantity(productDTO: ProductDTO) {
        val call = productService.decreaseProductQuantity(MainActivity.user.bearerToken, productDTO)
        call.enqueue(getProductDTOFetchCallback())
    }

    private fun restoreProduct(productDTO: ProductDTO) {
        val call = productService.restoreProduct(MainActivity.user.bearerToken, productDTO)
        call.enqueue(getProductDTOFetchCallback())
    }

    private fun removeProduct(productDTO: ProductDTO) {
        val call = productService.removeProduct(MainActivity.user.bearerToken, productDTO)
        call.enqueue(getProductDTOFetchCallback())
    }

    private fun editProduct(productEditDTO: ProductEditDTO) {
        val call = productService.editProduct(MainActivity.user.bearerToken, productEditDTO)
        call.enqueue(getVoidFetchCallback())
    }

    private fun addProduct(productDTO: ProductDTO) {
        val call = productService.addProduct(MainActivity.user.bearerToken, productDTO)
        call.enqueue(getVoidFetchCallback())
    }


    private fun getProductDTOFetchCallback(): Callback<ProductDTO> {
        return object : Callback<ProductDTO> {
            override fun onResponse(
                call: Call<ProductDTO>,
                response: Response<ProductDTO>
            ) {
                Log.i(TAG, response.message())
            }

            override fun onFailure(call: Call<ProductDTO>, t: Throwable) {
                Log.e(TAG, t.message, t)
            }
        }
    }

    private fun getVoidFetchCallback(): Callback<Void> {
        return object : Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                Log.i(TAG, response.message())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, t.message, t)
            }
        }
    }
}