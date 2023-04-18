package com.example.mercadolibreapp.domain.usecase

import com.example.mercadolibreapp.data.models.ProductDetails
import com.example.mercadolibreapp.domain.repository.ProductRepository
import com.example.mercadolibreapp.helpers.Constants
import com.example.mercadolibreapp.helpers.Either
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetProductDetailsUseCase{
    suspend fun call(idProduct: String): Either<Constants.ApiError, ProductDetails?>
}

@Singleton
class GetProductDetailsUseCaseImpl @Inject constructor(private val repository: ProductRepository): GetProductDetailsUseCase {
    override suspend fun call(idProduct: String): Either<Constants.ApiError, ProductDetails?> {
        return repository.getProductDetails(idProduct)
    }
}