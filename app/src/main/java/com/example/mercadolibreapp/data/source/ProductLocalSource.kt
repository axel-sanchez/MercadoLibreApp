package com.example.mercadolibreapp.data.source

import com.example.mercadolibreapp.data.models.ProductDetails
import com.example.mercadolibreapp.data.models.ResponseDTO.*
import com.example.mercadolibreapp.data.room.ProductDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface ProductLocalSource {
    suspend fun getProductBySearch(query: String): List<Product?>
    suspend fun getProductDetails(idProduct: String): ProductDetails?
    suspend fun insertProduct(product: Product?)
    suspend fun insertProductDetails(productDetails: ProductDetails?)
}

@Singleton
class ProductLocalSourceImpl @Inject constructor(private val database: ProductDao):
    ProductLocalSource {
    override suspend fun getProductBySearch(query: String): List<Product?> {
        return database.getProductBySearch(query)
    }

    override suspend fun insertProduct(product: Product?) {
        database.insertProduct(product)
    }

    override suspend fun insertProductDetails(productDetails: ProductDetails?) {
        database.insertProductDetails(productDetails)
    }

    override suspend fun getProductDetails(idProduct: String): ProductDetails? {
        return database.getProductDetails(idProduct)
    }
}