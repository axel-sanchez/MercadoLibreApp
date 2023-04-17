package com.example.mercadolibreapp.data.repository

import com.example.mercadolibreapp.data.models.ResponseDTO.*
import com.example.mercadolibreapp.data.source.ProductLocalSource
import com.example.mercadolibreapp.data.source.ProductRemoteSource
import com.example.mercadolibreapp.domain.repository.ProductRepository
import com.example.mercadolibreapp.helpers.Constants
import com.example.mercadolibreapp.helpers.Either
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val productRemoteSource: ProductRemoteSource,
    private val productLocalSource: ProductLocalSource
) : ProductRepository {

    override suspend fun getProductsBySearch(query: String): Either<Constants.ApiError, List<Product?>> {

        val localProducts = getLocalProducts(query)
        if (localProducts.isNotEmpty()) {
            return Either.Right(localProducts)
        }

        val eitherRemoteProducts = getRemoteProducts(query)

        if (eitherRemoteProducts is Either.Right) {
            addProductsInDB(eitherRemoteProducts.r, query)
        }

        return eitherRemoteProducts
    }

    override suspend fun getLocalProducts(query: String): List<Product?> {
        return productLocalSource.getProductBySearch(query)
    }

    private suspend fun getRemoteProducts(query: String): Either<Constants.ApiError, List<Product?>> {
        return productRemoteSource.getProducts(query).value ?: Either.Left(Constants.ApiError.API_ERROR)
    }

    override suspend fun getProduct(idProduct: String): Product? {
        return productLocalSource.getProduct(idProduct)
    }

    private suspend fun addProductsInDB(result: List<Product?>, query: String) {
        result.forEach { product ->
            product?.search = query
            productLocalSource.insertProduct(product)
        }
    }
}