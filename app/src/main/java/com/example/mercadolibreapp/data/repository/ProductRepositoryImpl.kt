package com.example.mercadolibreapp.data.repository

import com.example.mercadolibreapp.data.models.ProductDetails
import com.example.mercadolibreapp.data.models.ResponseDTO.*
import com.example.mercadolibreapp.data.source.ProductLocalSource
import com.example.mercadolibreapp.data.source.ProductRemoteSource
import com.example.mercadolibreapp.domain.repository.ProductRepository
import com.example.mercadolibreapp.helpers.Constants.ApiError
import com.example.mercadolibreapp.helpers.Constants.ApiError.*
import com.example.mercadolibreapp.helpers.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * @author Axel Sanchez
 */
@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val productRemoteSource: ProductRemoteSource,
    private val productLocalSource: ProductLocalSource
) : ProductRepository {

    override suspend fun getProductsBySearch(query: String): Either<ApiError, List<Product?>> {

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

    override suspend fun getProductDetails(idProduct: String): Either<ApiError, ProductDetails?> {
        val localProductDetails = productLocalSource.getProductDetails(idProduct)
        if (localProductDetails?.description != null) {
            return Either.Right(localProductDetails)
        }

        val eitherRemoteProductDetails = getRemoteProductDetails(idProduct)

        if (eitherRemoteProductDetails is Either.Right) {
            addProductDetailsInDB(eitherRemoteProductDetails.r)
        }

        return eitherRemoteProductDetails
    }

    override suspend fun getRemoteProductDetails(idProduct: String): Either<ApiError, ProductDetails?>{
        val description = productRemoteSource.getDescription(idProduct).value
        val productDetailsLiveData = productRemoteSource.getProductDetails(idProduct)

        productDetailsLiveData.value?.fold(
            left = {},
            right = { productDetails ->
                    productDetails?.description = description
            }
        )
       return productDetailsLiveData.value ?: Either.Left(GENERIC)
    }

    override suspend fun getLocalProducts(query: String): List<Product?> {
        return productLocalSource.getProductBySearch(query)
    }

    override suspend fun getRemoteProducts(query: String): Either<ApiError, List<Product?>> {
        return productRemoteSource.getProducts(query).value ?: Either.Left(GENERIC)
    }

    override suspend fun addProductsInDB(result: List<Product?>, query: String) {
        result.forEach { product ->
            product?.search = query
            productLocalSource.insertProduct(product)
        }
    }

    override suspend fun addProductDetailsInDB(productDetails: ProductDetails?) {
        productLocalSource.insertProductDetails(productDetails)
    }
}