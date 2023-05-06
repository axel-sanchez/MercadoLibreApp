package com.example.mercadolibreapp.domain.usecase

import com.example.mercadolibreapp.data.models.ProductDetails
import com.example.mercadolibreapp.domain.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetProductDetailsUseCase{
    suspend fun call(idProduct: String): ProductDetails
}

@Singleton
class GetProductDetailsUseCaseImpl @Inject constructor(private val repository: ProductRepository): GetProductDetailsUseCase {
    override suspend fun call(idProduct: String): ProductDetails {
        return repository.getProductDetails(idProduct)
    }
}