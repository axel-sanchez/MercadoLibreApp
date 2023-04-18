package com.example.mercadolibreapp.data.service

import com.example.mercadolibreapp.data.models.Description
import com.example.mercadolibreapp.data.models.ProductDetails
import com.example.mercadolibreapp.data.models.ResponseDTO
import com.example.mercadolibreapp.data.models.ResponseDTO.*
import com.example.mercadolibreapp.helpers.Constants.GET_DESCRIPTION
import com.example.mercadolibreapp.helpers.Constants.GET_DETAILS
import com.example.mercadolibreapp.helpers.Constants.GET_PRODUCTS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Axel Sanchez
 */
interface ApiServiceProduct {
    @GET(GET_PRODUCTS)
    suspend fun getProducts(@Query("q") query: String): Response<ResponseDTO?>

    @GET(GET_DETAILS)
    suspend fun getDetailsById(
        @Path("productId") productId: String
    ): Response<ProductDetails>

    @GET(GET_DESCRIPTION)
    suspend fun getDescription(
        @Path("productId") productId: String
    ): Response<Description?>
}