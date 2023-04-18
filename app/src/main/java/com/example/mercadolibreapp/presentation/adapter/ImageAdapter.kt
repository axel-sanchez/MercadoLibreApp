package com.example.mercadolibreapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mercadolibreapp.R
import com.example.mercadolibreapp.data.models.Picture
import com.example.mercadolibreapp.helpers.load

/**
 * @author Axel Sanchez
 */
class ImageAdapter(private val images: List<Picture?>?) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images?.get(position))
    }

    override fun getItemCount() = images?.size?:0

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.ivImage)

        fun bind(picture: Picture?) {
            imageView.load(picture?.secure_url)
        }
    }
}