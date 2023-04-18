package com.example.mercadolibreapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.example.mercadolibreapp.data.models.ResponseDTO
import com.example.mercadolibreapp.domain.usecase.GetProductsBySearchUseCase
import com.example.mercadolibreapp.helpers.Constants
import com.example.mercadolibreapp.helpers.DummyProducts.getListProducts
import com.example.mercadolibreapp.helpers.DummyProducts.product1
import com.example.mercadolibreapp.helpers.DummyProducts.product2
import com.example.mercadolibreapp.helpers.DummyProducts.product3
import com.example.mercadolibreapp.helpers.DummyProducts.product4
import com.example.mercadolibreapp.helpers.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.contains
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test

/**
 * @author Axel Sanchez
 */
class SearchViewModelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun should_update_livedata_with_product_list(){
        val useCase = object : GetProductsBySearchUseCase {
            override suspend fun call(query: String): Either<Constants.ApiError, List<ResponseDTO.Product?>> {
                return getListProducts()
            }
        }

        val viewModel = SearchViewModel(useCase)
        runBlocking(Dispatchers.IO) {
            viewModel.setListData(useCase.call("teclado"))
            val result = viewModel.getProductLiveData().value
            result?.fold(
                left = {},
                right = { products ->
                    assertThat(products, contains(product1, product2, product3, product4))
                }
            )?: kotlin.run { fail("El Live Data no pudo ser actualizado con su nuevo valor") }
        }
    }
}