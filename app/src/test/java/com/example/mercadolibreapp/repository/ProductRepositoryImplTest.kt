package com.example.mercadolibreapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.mercadolibreapp.data.repository.ProductRepositoryImpl
import com.example.mercadolibreapp.data.source.ProductLocalSource
import com.example.mercadolibreapp.data.source.ProductRemoteSource
import com.example.mercadolibreapp.domain.repository.ProductRepository
import com.example.mercadolibreapp.helpers.DummyProducts.TECLADO
import com.example.mercadolibreapp.helpers.DummyProducts.getListProducts
import com.example.mercadolibreapp.helpers.DummyProducts.product1
import com.example.mercadolibreapp.helpers.DummyProducts.product2
import com.example.mercadolibreapp.helpers.DummyProducts.product3
import com.example.mercadolibreapp.helpers.DummyProducts.product4
import com.example.mercadolibreapp.helpers.Either
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.never
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ProductRepositoryImplTest {

    private val productRemoteSource: ProductRemoteSource = mock(ProductRemoteSource::class.java)
    private val productLocalSource: ProductLocalSource = mock(ProductLocalSource::class.java)
    private val productRepository: ProductRepository = ProductRepositoryImpl(productRemoteSource, productLocalSource)

    @Test
        fun should_calls_to_getRemoteProducts_when_there_are_not_local_products(){
        runBlocking {
            val mutableListData = MutableLiveData(getListProducts())
            given(productRemoteSource.getProducts(TECLADO)).willReturn(mutableListData)
            given(productRepository.getLocalProducts(TECLADO)).willReturn(listOf())
            productRepository.getProductsBySearch(TECLADO)
            verify(productRemoteSource).getProducts(TECLADO)
        }
    }

    @Test
    fun should_not_calls_to_getRemoteProducts_when_there_are_local_products(){
        runBlocking {
            given(productRepository.getLocalProducts(TECLADO)).willReturn(listOf(product1))
            productRepository.getProductsBySearch(TECLADO)
            verify(productRemoteSource, never()).getProducts(TECLADO)
        }
    }

    /*@Test
    fun should_return_product_list_sorted_by_title() {
        runBlocking {
            given(productRepository.getLocalProducts(TECLADO)).willReturn(listOf())

            val mutableListData = MutableLiveData(getListProducts())
            given(productRemoteSource.getProducts(TECLADO)).willReturn(mutableListData)

            val result = productRepository.getProductsBySearch(TECLADO)
            assertThat((result as Either.Right).r, contains(product3, product2, product4, product1))
        }
    }*/
}