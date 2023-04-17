package com.example.mercadolibreapp.domain.usecase

import com.example.mercadolibreapp.data.models.ResponseDTO.*
import com.example.mercadolibreapp.domain.repository.ProductRepository
import com.example.mercadolibreapp.helpers.Constants
import com.example.mercadolibreapp.helpers.Either
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetProductsBySearchUseCase{
    suspend fun call(query: String): Either<Constants.ApiError, List<Product?>>
}

@Singleton
class GetProductsBySearchUseCaseImpl @Inject constructor(private val repository: ProductRepository):
    GetProductsBySearchUseCase {
    override suspend fun call(query: String): Either<Constants.ApiError, List<Product?>> {
        return repository.getProductsBySearch(query)
    }
}