package com.example.mercadolibreapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mercadolibreapp.data.models.ResponseDTO.*
import com.example.mercadolibreapp.databinding.ItemProductBinding
import com.example.mercadolibreapp.helpers.hide
import com.example.mercadolibreapp.helpers.load
import com.example.mercadolibreapp.helpers.show

/**
 * @author Axel Sanchez
 */
class ProductAdapter(
    private var mItems: List<Product?>,
    private val itemClick: (Product?, ImageView) -> Unit?
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product?, itemClick: (Product?, ImageView) -> Unit?, position: Int) {

            with(binding) {
                item?.let { product ->
                    itemView.setOnClickListener {
                        itemClick(product, ivProduct)
                    }

                    product.title?.let { title ->
                        tvTitle.text = title
                    } ?: kotlin.run { tvTitle.hide() }

                    product.price?.let { price ->
                        tvPrice.text = "$$price"
                    } ?: kotlin.run { tvPrice.hide() }

                    product.thumbnail?.let { urlImage ->
                        if (urlImage.isNotEmpty()) ivProduct.load(urlImage)
                    } ?: kotlin.run { ivProduct.hide() }

                    product.shipping?.free_shipping?.let { freeShipping ->
                        if (freeShipping) txtFreeShipping.show()
                        else txtFreeShipping.hide()
                    }

                    if (position == itemCount - 1) {
                        vSeparator.hide()
                    } else {
                        vSeparator.show()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val recyclerRowBinding: ItemProductBinding =
            ItemProductBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(recyclerRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(mItems[position], itemClick, position)


    override fun getItemCount() = mItems.size
}