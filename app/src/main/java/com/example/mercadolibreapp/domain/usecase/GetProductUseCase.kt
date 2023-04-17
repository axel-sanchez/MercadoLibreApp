package com.example.mercadolibreapp.domain.usecase

import com.example.mercadolibreapp.data.models.ResponseDTO.*
import com.example.mercadolibreapp.domain.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetProductUseCase{
    suspend fun call(idProduct: String): Product?
}

@Singleton
class GetProductUseCaseImpl @Inject constructor(private val repository: ProductRepository): GetProductUseCase {
    override suspend fun call(idProduct: String): Product? {
        return repository.getProduct(idProduct)
    }
}