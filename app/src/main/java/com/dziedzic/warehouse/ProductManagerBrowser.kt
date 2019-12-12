package com.dziedzic.warehouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dziedzic.warehouse.Entity.ProductDTO

import com.dziedzic.warehouse.Rest.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import android.widget.Toast
import java.io.*


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

        displayUserData()
        displayProducts()
    }

    override fun onBackPressed() {
        finish()
    }

    fun addNewProduct(view: View) {
        val nextScreen = Intent(applicationContext, ProductManagerEditor::class.java)
        startActivity(nextScreen)
    }

    fun displayUserData() {
        val userName: TextView = findViewById(R.id.userName)
        val userRole: TextView = findViewById(R.id.userRole)
        userName.setText(MainActivity.user.name)
        userRole.setText(MainActivity.user.role)

    }

    fun displayProducts() {
        val call = productService.getProducts(MainActivity.user.bearerToken)
        val utils = Utils();
        val isInternetAvailable = utils.isInternetAvailable(applicationContext)
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
                    saveProductsState(response.body().orEmpty())
                    productManagerBrowserAdapter?.update(response.body().orEmpty())
                }
            }

            override fun onFailure(call: Call<List<ProductDTO>>, t: Throwable) {
                Log.e(TAG, t.message, t)
            }
        }
    }

    fun saveProductsState(products: List<ProductDTO>) {
        val folderPath = externalCacheDir.toString() + "/warehouse_manager"
        val gson = Gson()
        val productsJson = gson.toJson(products)

        createApplicationDir(folderPath)

        try {
            var output: Writer? = null
            val file = File(folderPath + "products.json")
            output = BufferedWriter(FileWriter(file))
            output.run {
                output = BufferedWriter(FileWriter(file))
                write(productsJson.toString())
                close()
            }
            Toast.makeText(applicationContext, "Products list saved", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            Toast.makeText(baseContext, e.message, Toast.LENGTH_LONG).show()
        }



        readProductsState()
    }


    fun readProductsState(): List<ProductDTO> {
        val folderPath = externalCacheDir.toString() + "/warehouse_manager"
        val gson = Gson()
        val bufferedReader: BufferedReader = File(folderPath + "products.json").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        val data = gson.fromJson(inputString, Array<ProductDTO>::class.java).toList();
        return data
    }


    fun createApplicationDir(folderPath: String) {
        val folder = File(folderPath)
        if (!folder.exists()) {
            val wallpaperDirectory = File(folderPath)
            wallpaperDirectory.mkdirs()
        }
    }
}
