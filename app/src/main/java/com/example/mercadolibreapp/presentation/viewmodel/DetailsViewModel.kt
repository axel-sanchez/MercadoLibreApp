package com.example.mercadolibreapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.mercadolibreapp.data.models.ProductDetails
import com.example.mercadolibreapp.domain.usecase.GetProductDetailsUseCase
import com.example.mercadolibreapp.helpers.Constants
import com.example.mercadolibreapp.helpers.Either
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class DetailsViewModel(private val getProductDetailsUseCase: GetProductDetailsUseCase) : ViewModel() {

    private val productLiveData: MutableLiveData<Either<Constants.ApiError, ProductDetails?>> =
        MutableLiveData<Either<Constants.ApiError, ProductDetails?>>()

    private fun setListData(result: Either<Constants.ApiError, ProductDetails?>) {
        productLiveData.postValue(result)
    }

    fun getProduct(idProduct: String) {
        viewModelScope.launch {
            setListData(getProductDetailsUseCase.call(idProduct))
        }
    }

    fun getProductLiveData(): LiveData<Either<Constants.ApiError, ProductDetails?>> {
        return productLiveData
    }

    class DetailsViewModelFactory(private val getProductDetailsUseCase: GetProductDetailsUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetProductDetailsUseCase::class.java)
                .newInstance(getProductDetailsUseCase)
        }
    }
}