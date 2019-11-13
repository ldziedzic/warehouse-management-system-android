package com.dziedzic.warehouse

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dziedzic.warehouse.Entity.ProductDTO
import com.dziedzic.warehouse.ProductManagerAdapter.MyViewHolder

class ProductManagerAdapter(
    private var productList: List<ProductDTO>?, var context: Context)
        : RecyclerView.Adapter<MyViewHolder>() {

    override fun getItemCount(): Int {
        if (productList.isNullOrEmpty())
            return 0
        return productList!!.size

    }


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var price: TextView
        var quantity: TextView
        var layout: RelativeLayout


        init {
            title = view.findViewById(R.id.name)
            price = view.findViewById(R.id.price)
            quantity = view.findViewById(R.id.quantity)
            layout = view.findViewById(R.id.background)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_product_manager_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = productList!![position]
        val title = product.manufacturerName + " " + product.modelName
        holder.title.setText(title)

        val price = product.price
        val priceMessage = "Price: $price"
        holder.price.text = priceMessage

        val quantity = product.quantity
        val quantityMessage = "Quantity: $quantity"
        holder.quantity.text = quantityMessage

        if (position % 2 == 0)
            holder.layout.setBackgroundColor(Color.parseColor("#deeefa"))
        else
            holder.layout.setBackgroundColor(Color.parseColor("#f2f7fa"))
    }

    fun update(productList: List<ProductDTO>) {
        this.productList = productList
        notifyDataSetChanged()
    }


    fun returnProduct(position: Int): ProductDTO {
        return productList!![position]
    }
}
