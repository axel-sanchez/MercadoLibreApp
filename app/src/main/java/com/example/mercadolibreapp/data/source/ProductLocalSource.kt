package com.example.mercadolibreapp.data.source

import com.example.mercadolibreapp.data.models.ResponseDTO.*
import com.example.mercadolibreapp.data.room.ProductDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface ProductLocalSource {
    suspend fun getProductBySearch(query: String): List<Product?>
    suspend fun getProduct(idProduct: String): Product?
    suspend fun insertProduct(product: Product?): Long
}

@Singleton
class ProductLocalSourceImpl @Inject constructor(private val database: ProductDao):
    ProductLocalSource {
    override suspend fun getProductBySearch(query: String): List<Product?> {
        return database.getProductBySearch(query)
    }

    override suspend fun insertProduct(product: Product?): Long {
        return database.insertProduct(product)
    }

    override suspend fun getProduct(idProduct: String): Product? {
        return database.getProduct(idProduct)
    }
}