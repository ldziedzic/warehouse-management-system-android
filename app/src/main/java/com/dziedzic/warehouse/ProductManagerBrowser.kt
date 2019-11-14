package com.dziedzic.warehouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dziedzic.warehouse.Entity.ProductDTO

import com.dziedzic.warehouse.Rest.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductManagerBrowser : AppCompatActivity() {
    private val TAG = "ProductManagerBrowser"
    private val productService = APIClient.getProductService()
    private var productManagerBrowserAdapter: ProductManagerBrowserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_manager_browser)

        val newProductButton: Button = findViewById(R.id.add_new_product_button)
        newProductButton.setOnClickListener {addNewProduct(it)}
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        productManagerBrowserAdapter = ProductManagerBrowserAdapter(null, getApplicationContext())

        val mLayoutManager = LinearLayoutManager(
            applicationContext
        )
        recyclerView.setLayoutManager(mLayoutManager)
        recyclerView.setItemAnimator(DefaultItemAnimator())
        recyclerView.setAdapter(productManagerBrowserAdapter)

        displayProducts()
    }

    fun addNewProduct(view: View) {
        val nextScreen = Intent(applicationContext, ProductManagerEditor::class.java)
        startActivity(nextScreen)
    }

    fun displayProducts() {
        val call = productService.getProducts(MainActivity.user.bearerToken)
        call.enqueue(getFetchCallback())
    }

    private fun getFetchCallback(): Callback<List<ProductDTO>> {
        return object : Callback<List<ProductDTO>> {
            override fun onResponse(
                call: Call<List<ProductDTO>>,
                response: Response<List<ProductDTO>>
            ) {
                Log.i(TAG, response.message())
                if (response.isSuccessful()) {
                    productManagerBrowserAdapter?.update(response.body().orEmpty())
                }
            }

            override fun onFailure(call: Call<List<ProductDTO>>, t: Throwable) {
                Log.e(TAG, t.message, t)
            }
        }
    }
}
