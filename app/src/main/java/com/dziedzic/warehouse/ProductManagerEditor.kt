package com.dziedzic.warehouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.dziedzic.warehouse.Entity.ProductDTO

import com.dziedzic.warehouse.Rest.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductManagerEditor : AppCompatActivity() {
    private val TAG = "ProductManagerEditor"
    protected val productService = APIClient.getProductService()
    private var productManagerBrowserAdapter: ProductManagerBrowserAdapter? = null
    private var editType = "CREATE_NEW_PRODUCT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_manager_editor)

        val i = intent
        val product = i.getParcelableExtra<ProductDTO>("EDIT_PRODUCT")

        val manufacturerName: EditText = findViewById(R.id.manufacturerName)
        val modelName: EditText = findViewById(R.id.amount)
        val price: EditText = findViewById(R.id.price)

        if (product != null) {
            manufacturerName.setText(product.manufacturerName)
            modelName.setText(product.modelName)
            price.setText(product.price)
            editType = "EDIT_PRODUCT"
        }


        val saveChanges: Button = findViewById(R.id.saveChanges)

        saveChanges.setOnClickListener {
            var editedProduct = ProductDTO()
            editedProduct.manufacturerName = manufacturerName.text.toString()
            editedProduct.modelName = modelName.text.toString()
            editedProduct.price = price.text.toString().toInt()

            saveProduct(editedProduct)
        }
    }

    fun saveProduct(product: ProductDTO) {
        val call = productService.addProduct(MainActivity.user.bearerToken, product)
        call.enqueue(getFetchCallback())
    }

    private fun getFetchCallback(): Callback<Void> {
        return object : Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                Log.i(TAG, response.message())
                val nextScreen = Intent(applicationContext, ProductManagerBrowser::class.java)
                startActivity(nextScreen)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, t.message, t)
                val nextScreen = Intent(applicationContext, ProductManagerBrowser::class.java)
                startActivity(nextScreen)
            }
        }
    }
}
