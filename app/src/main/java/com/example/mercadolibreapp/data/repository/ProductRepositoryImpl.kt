package com.example.mercadolibreapp.data.repository

import com.example.mercadolibreapp.data.models.DataProducts
import com.example.mercadolibreapp.data.models.ProductDetails
import com.example.mercadolibreapp.data.models.ResponseDTO.*
import com.example.mercadolibreapp.data.source.ProductLocalSource
import com.example.mercadolibreapp.data.source.ProductRemoteSource
import com.example.mercadolibreapp.domain.repository.ProductRepository
import com.example.mercadolibreapp.helpers.Constants.ApiError.*
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

    override suspend fun getProductsBySearch(query: String): DataProducts {
        val localProducts = getLocalProducts(query)
        if (localProducts.isNotEmpty()) {
            return DataProducts(products = localProducts)
        }

        val remoteDataProducts = getRemoteProducts(query)

        if (!remoteDataProducts.products.isNullOrEmpty()) {
            addProductsInDB(remoteDataProducts.products, query)
        }

        return remoteDataProducts
    }

    override suspend fun getProductDetails(idProduct: String): ProductDetails {
        val localProductDetails = productLocalSource.getProductDetails(idProduct)
        if (localProductDetails?.description != null) {
            return localProductDetails
        }

        val remoteDataProductDetails = getRemoteProductDetails(idProduct)

        if (remoteDataProductDetails.apiError == null) {
            addProductDetailsInDB(remoteDataProductDetails)
        }

        return remoteDataProductDetails
    }

    override suspend fun getLocalProducts(query: String): List<Product?> {
        return productLocalSource.getProductBySearch(query)
    }

    override suspend fun getRemoteProducts(query: String): DataProducts {
        return productRemoteSource.getProducts(query).value ?: DataProducts(apiError = GENERIC)
    }

    private suspend fun getRemoteProductDetails(idProduct: String): ProductDetails{
        val description = productRemoteSource.getDescription(idProduct).value
        val productDetailsLiveData = productRemoteSource.getProductDetails(idProduct)
        productDetailsLiveData.value?.description = description
        return productDetailsLiveData.value ?: ProductDetails(id = "", apiError = GENERIC)
    }

    private suspend fun addProductsInDB(result: List<Product?>, query: String) {
        result.forEach { product ->
            product?.search = query
            productLocalSource.insertProduct(product)
        }
    }

    private suspend fun addProductDetailsInDB(productDetails: ProductDetails?) {
        productLocalSource.insertProductDetails(productDetails)
    }
}