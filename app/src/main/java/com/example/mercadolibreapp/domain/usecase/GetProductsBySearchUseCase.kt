package com.example.mercadolibreapp.domain.usecase

import com.example.mercadolibreapp.data.models.DataProducts
import com.example.mercadolibreapp.domain.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetProductsBySearchUseCase{
    suspend fun call(query: String): DataProducts
}

@Singleton
class GetProductsBySearchUseCaseImpl @Inject constructor(private val repository: ProductRepository):
    GetProductsBySearchUseCase {
    override suspend fun call(query: String): DataProducts {
        return repository.getProductsBySearch(query)
    }
}