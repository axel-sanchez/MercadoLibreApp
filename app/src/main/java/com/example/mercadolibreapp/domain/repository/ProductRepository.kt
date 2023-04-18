package com.example.mercadolibreapp.domain.repository

import com.example.mercadolibreapp.data.models.ProductDetails
import com.example.mercadolibreapp.data.models.ResponseDTO.*
import com.example.mercadolibreapp.helpers.Constants
import com.example.mercadolibreapp.helpers.Either

/**
 * @author Axel Sanchez
 */
interface ProductRepository {
    suspend fun getProductsBySearch(query: String): Either<Constants.ApiError, List<Product?>>
    suspend fun getProductDetails(idProduct: String): Either<Constants.ApiError, ProductDetails?>
    suspend fun getLocalProducts(query: String): List<Product?>
    suspend fun getRemoteProducts(query: String): Either<Constants.ApiError, List<Product?>>
}