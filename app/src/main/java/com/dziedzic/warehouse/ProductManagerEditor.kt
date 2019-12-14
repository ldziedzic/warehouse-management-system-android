package com.dziedzic.warehouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.dziedzic.warehouse.Entity.ProductDTO
import com.dziedzic.warehouse.Entity.ProductEditDTO
import com.dziedzic.warehouse.Entity.RequestManagerEntity

import com.dziedzic.warehouse.Rest.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductManagerEditor : AppCompatActivity() {
    private val TAG = "ProductManagerEditor"
    protected val productService = APIClient.getProductService()
    private var productManagerBrowserAdapter: ProductManagerBrowserAdapter? = null
    private var editType = "CREATE_NEW_PRODUCT"
    var product: ProductDTO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_manager_editor)

        val i = intent
        product = i.getParcelableExtra<ProductDTO>("EDIT_PRODUCT")

        val manufacturerName: EditText = this.findViewById(R.id.manufacturerName)
        val modelName: EditText = findViewById(R.id.modelName)
        val price: EditText = findViewById(R.id.price)

        if (product != null) {
            manufacturerName.setText(product!!.manufacturerName)
            modelName.setText(product!!.modelName)
            price.setText(product!!.price.toString())
            editType = "EDIT_PRODUCT"
        }


        val saveChanges: Button = findViewById(R.id.saveChanges)

        saveChanges.setOnClickListener {
            val editedProduct = ProductDTO()
            editedProduct.manufacturerName = manufacturerName.text.toString()
            editedProduct.modelName = modelName.text.toString()
            if (price.getText().toString() != "")
                editedProduct.price = Integer.parseInt(price.getText().toString())
            else editedProduct.price = product!!.price

            saveProduct(editedProduct)
        }
    }

    override fun onBackPressed() {
        val nextScreen = Intent(applicationContext, ProductManagerBrowser::class.java)
        startActivity(nextScreen)
        finish()
    }

    fun saveProduct(editedProduct: ProductDTO) {
        if (editType == "EDIT_PRODUCT") {
            val productEditDTO: ProductEditDTO = ProductEditDTO()
            productEditDTO.manufacturerName = product!!.manufacturerName
            productEditDTO.modelName = product!!.modelName
            productEditDTO.price = editedProduct.price
            productEditDTO.newModelName = editedProduct.modelName
            productEditDTO.newManufacturerName = editedProduct.manufacturerName


            val utils = Utils();
            val isInternetAvailable = utils.isInternetAvailable(applicationContext)
            if (!isInternetAvailable) {
                MainActivity.requestsManager.addRequestToManager(
                    RequestManagerEntity("editProduct", null, productEditDTO)
                )
            } else {
                val call = productService.editProduct(MainActivity.user.bearerToken, productEditDTO)
                call.enqueue(getFetchCallback())
            }
        } else {
            val utils = Utils();
            val isInternetAvailable = utils.isInternetAvailable(applicationContext)
            if (!isInternetAvailable) {
                MainActivity.requestsManager.addRequestToManager(
                    RequestManagerEntity("addProduct", editedProduct, null)
                )
            } else {
                val call = productService.addProduct(MainActivity.user.bearerToken, editedProduct)
                call.enqueue(getFetchCallback())
            }
        }

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
                finish()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, t.message, t)
                val nextScreen = Intent(applicationContext, ProductManagerBrowser::class.java)
                startActivity(nextScreen)
            }
        }
    }
}
