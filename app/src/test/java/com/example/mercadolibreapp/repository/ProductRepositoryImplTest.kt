package com.example.mercadolibreapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.mercadolibreapp.data.repository.FakeRepository
import com.example.mercadolibreapp.data.repository.FakeRepository.Companion.TECLADO
import com.example.mercadolibreapp.data.repository.ProductRepositoryImpl
import com.example.mercadolibreapp.data.source.ProductLocalSource
import com.example.mercadolibreapp.data.source.ProductRemoteSource
import com.example.mercadolibreapp.domain.repository.ProductRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.never
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ProductRepositoryImplTest {

    private val repository = FakeRepository()

    private val productRemoteSource: ProductRemoteSource = mock(ProductRemoteSource::class.java)
    private val productLocalSource: ProductLocalSource = mock(ProductLocalSource::class.java)
    private val productRepository: ProductRepository = ProductRepositoryImpl(productRemoteSource, productLocalSource)

    @Test
        fun should_calls_to_getRemoteProducts_when_there_are_not_local_products(){
        runBlocking {
            val mutableListData = MutableLiveData(repository.getRemoteProducts(""))
            given(productRemoteSource.getProducts(TECLADO)).willReturn(mutableListData)
            given(productRepository.getLocalProducts(TECLADO)).willReturn(listOf())
            productRepository.getProductsBySearch(TECLADO)
            verify(productRemoteSource).getProducts(TECLADO)
        }
    }

    @Test
    fun should_not_calls_to_getRemoteProducts_when_there_are_local_products(){
        runBlocking {
            given(productRepository.getLocalProducts(TECLADO)).willReturn(listOf(repository.product1))
            productRepository.getProductsBySearch(TECLADO)
            verify(productRemoteSource, never()).getProducts(TECLADO)
        }
    }
}