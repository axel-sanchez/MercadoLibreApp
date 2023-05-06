package com.example.mercadolibreapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mercadolibreapp.data.models.ResponseDTO.Product.*
import com.example.mercadolibreapp.helpers.Constants
import com.google.gson.annotations.SerializedName

/**
 * @author Axel Sanchez
 */
@Entity data class ProductDetails(
    @PrimaryKey val id: String,
    @SerializedName("accepts_mercadopago")
    val acceptsMercadoPago: Boolean? = null,
    @SerializedName("available_quantity")
    val availableQuantity: Int? = null,
    val condition: String? = null,
    val shipping: Shipping? = null,
    val pictures: List<Picture?>? = null,
    val price: Number? = null,
    var permalink: String? = null,
    @SerializedName("secure_thumbnail")
    val secureThumbnail: String? = null,
    @SerializedName("seller_address")
    val sellerAddress: SellerAddress? = null,
    val title: String? = null,
    var description: Description? = null,
    val apiError: Constants.ApiError? = null
)
