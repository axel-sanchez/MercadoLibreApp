package com.example.mercadolibreapp.data.service

import com.example.mercadolibreapp.data.models.ResponseDTO
import com.example.mercadolibreapp.data.models.ResponseDTO.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Axel Sanchez
 */
interface ApiServiceProduct {
    @GET("search")
    suspend fun getProducts(@Query("q") query: String): Response<ResponseDTO?>
}