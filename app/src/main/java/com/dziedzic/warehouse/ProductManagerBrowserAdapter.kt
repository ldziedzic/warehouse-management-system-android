package com.dziedzic.warehouse

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.dziedzic.warehouse.Entity.ProductDTO
import com.dziedzic.warehouse.ProductManagerBrowserAdapter.MyViewHolder
import com.dziedzic.warehouse.Rest.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductManagerBrowserAdapter (
    private var productList: List<ProductDTO>?, var context: Context)
        : RecyclerView.Adapter<MyViewHolder>() {
    private val productService = APIClient.getProductService()
    private val TAG = "ProductManagerBrowserAdapter"

    override fun getItemCount(): Int {
        if (productList.isNullOrEmpty())
            return 0
        return productList!!.size

    }


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var manufacturerName = ""
        var modelName = ""
        var price = 0
        var quantity = 0
        var productPosition: Int = 0
        var titleMessage: TextView
        var priceMessage: TextView
        var quantityMessage: TextView
        var layout: RelativeLayout
        var amount: TextView
        var increase_button: Button
        var decrease_button: Button
        var edit_button: Button

        init {
            titleMessage = view.findViewById(R.id.name)
            priceMessage = view.findViewById(R.id.price)
            quantityMessage = view.findViewById(R.id.quantity)
            amount = view.findViewById(R.id.amount)
            increase_button = view.findViewById(R.id.increase_button)
            decrease_button = view.findViewById(R.id.decrease_button)
            edit_button = view.findViewById(R.id.edit_button)
            layout = view.findViewById(R.id.background)
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_product_manager_browser_item, parent, false)

        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = productList!![position]
        holder.productPosition = position
        setHolderValues(product, holder)

        if (position % 2 == 0)
            holder.layout.setBackgroundColor(Color.parseColor("#deeefa"))
        else
            holder.layout.setBackgroundColor(Color.parseColor("#f2f7fa"))

        holder.increase_button.setOnClickListener {increaseProductQuantity(it, holder)}
        holder.decrease_button.setOnClickListener {decreaseProductQuantity(it, holder)}
        holder.edit_button.setOnClickListener {editProduct(it, product)}
    }


    private fun setHolderValues(
        product: ProductDTO,
        holder: MyViewHolder
    ) {
        holder.manufacturerName = product.manufacturerName
        holder.modelName = product.modelName
        holder.price = product.price
        holder.quantity = product.quantity

        val title = product.manufacturerName + " " + product.modelName
        holder.titleMessage.setText(title)

        val price = product.price
        val priceMessage = "Price: $price"
        holder.priceMessage.text = priceMessage

        val quantity = product.quantity
        val quantityMessage = "Quantity: $quantity"
        holder.quantityMessage.text = quantityMessage
    }


    fun increaseProductQuantity(view: View, holder: MyViewHolder) {
        val product = getProductFromHolder(holder)
        if (holder.amount.getText().toString() != "")
            product.quantity = Integer.parseInt(holder.amount.getText().toString())
        else product.quantity = 0

        val call = productService.increaseProductQuantity(MainActivity.user.bearerToken, product)
        call.enqueue(getFetchCallback(holder.productPosition))
    }


    fun decreaseProductQuantity(view: View, holder: MyViewHolder) {
        val product = getProductFromHolder(holder)
        if (holder.amount.getText().toString() != "")
            product.quantity = Integer.parseInt(holder.amount.getText().toString())
        else product.quantity = 0

        val call = productService.decreaseProductQuantity(MainActivity.user.bearerToken, product)
        call.enqueue(getFetchCallback(holder.productPosition))
    }


    fun editProduct(view: View, product: ProductDTO) {
        val nextScreen = Intent(context, ProductManagerEditor::class.java)
        nextScreen.putExtra("EDIT_PRODUCT", product)
        startActivity(context, nextScreen, null)
    }


    private fun getProductFromHolder(holder: MyViewHolder): ProductDTO {
        val product = ProductDTO()
        product.manufacturerName = holder.manufacturerName
        product.modelName = holder.modelName
        product.price = holder.price
        product.quantity = holder.quantity

        return product
    }


    private fun getFetchCallback(position: Int): Callback<ProductDTO> {
        return object : Callback<ProductDTO> {
            override fun onResponse(
                call: Call<ProductDTO>,
                response: Response<ProductDTO>
            ) {
                Log.i(TAG, response.message())
                if (response.isSuccessful()) {
                    val product = response.body()
                    if (product != null) {
                        changeProduct(product, position)
                        notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<ProductDTO>, t: Throwable) {
                Log.e(TAG, t.message, t)
            }
        }
    }


    fun update(productList: List<ProductDTO>) {
        this.productList = productList
        notifyDataSetChanged()
    }


    fun returnProduct(position: Int): ProductDTO {
        return productList!![position]
    }

    fun changeProduct(product : ProductDTO, position: Int) {
        this.productList!![position].modelName = product.modelName
        this.productList!![position].manufacturerName = product.manufacturerName
        this.productList!![position].price = product.price
        this.productList!![position].quantity = product.quantity
    }
}
