package com.example.mercadolibreapp.domain.repository

import com.example.mercadolibreapp.data.models.DataProducts
import com.example.mercadolibreapp.data.models.ProductDetails
import com.example.mercadolibreapp.data.models.ResponseDTO.*

/**
 * @author Axel Sanchez
 */
interface ProductRepository {
    suspend fun getProductsBySearch(query: String): DataProducts
    suspend fun getProductDetails(idProduct: String): ProductDetails
    suspend fun getLocalProducts(query: String): List<Product?>
    suspend fun getRemoteProducts(query: String): DataProducts
}