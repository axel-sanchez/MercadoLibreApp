package com.example.mercadolibreapp.domain.repository

import com.example.mercadolibreapp.data.models.ResponseDTO.*
import com.example.mercadolibreapp.helpers.Constants
import com.example.mercadolibreapp.helpers.Either

/**
 * @author Axel Sanchez
 */
interface ProductRepository {
    suspend fun getProductsBySearch(query: String): Either<Constants.ApiError, List<Product?>>
    suspend fun getProduct(idProduct: String): Product?
    suspend fun getLocalProducts(query: String): List<Product?>
}