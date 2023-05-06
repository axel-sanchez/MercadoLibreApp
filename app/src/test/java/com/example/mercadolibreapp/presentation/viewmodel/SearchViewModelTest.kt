package com.example.mercadolibreapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.example.mercadolibreapp.data.models.DataProducts
import com.example.mercadolibreapp.data.repository.FakeRepository
import com.example.mercadolibreapp.data.repository.FakeRepository.Companion.TECLADO
import com.example.mercadolibreapp.domain.usecase.GetProductsBySearchUseCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.contains
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test

/**
 * @author Axel Sanchez
 */
class SearchViewModelTest{

    private val repository = FakeRepository()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun should_update_livedata_with_product_list(){
        val useCase = object : GetProductsBySearchUseCase {
            override suspend fun call(query: String): DataProducts {
                return repository.getProductsBySearch("")
            }
        }

        val viewModel = SearchViewModel(useCase)
        runBlocking {
            viewModel.setListData(useCase.call(TECLADO))
            val result = viewModel.getProductLiveData().value
            result?.products?.let { products ->
                assertThat(products, contains(repository.product1, repository.product2, repository.product3, repository.product4))
            } ?: kotlin.run { fail("El Live Data no pudo ser actualizado con su nuevo valor") }
        }
    }
}