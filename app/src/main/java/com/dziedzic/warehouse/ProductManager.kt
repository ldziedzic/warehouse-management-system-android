package com.dziedzic.warehouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.dziedzic.warehouse.Entity.ProductDTO

import com.dziedzic.warehouse.Rest.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductManager : AppCompatActivity() {
    private val TAG = "SignInActivity"
    protected val productService = APIClient.getProductService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_manager)
    }

    fun viewProducts(view: View) {
        val call = productService.getProducts(MainActivity.user.bearerToken)
        call.enqueue(getFetchCallback())
    }

    private fun getFetchCallback(): Callback<List<ProductDTO>> {
        return object : Callback<List<ProductDTO>> {
            override fun onResponse(call: Call<List<ProductDTO>>, response: Response<List<ProductDTO>>) {
                Log.i(TAG, response.message())
//                if(response.isSuccessful()) {
//                    val refreshToken = response.body()?.idToken;
//                    MainActivity.user.refreshToken = refreshToken
//                } else {
//                    MainActivity.user.refreshToken = ""
//                }
            }

            override fun onFailure(call: Call<List<ProductDTO>>, t: Throwable) {
                Log.e(TAG, t.message, t)
            }
        }
    }
}
