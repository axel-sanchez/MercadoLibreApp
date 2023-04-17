package com.example.mercadolibreapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.mercadolibreapp.data.models.ResponseDTO.*
import com.example.mercadolibreapp.domain.usecase.GetProductUseCase
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class DetailsViewModel(private val getProductUseCase: GetProductUseCase) : ViewModel() {

    private val productLiveData: MutableLiveData<Product?> = MutableLiveData<Product?>()

    private fun setListData(result: Product?) {
        productLiveData.postValue(result)
    }

    fun getProduct(idProduct: String) {
        viewModelScope.launch {
            setListData(getProductUseCase.call(idProduct))
        }
    }

    fun getProductLiveData(): LiveData<Product?> {
        return productLiveData
    }

    class DetailsViewModelFactory(private val getProductUseCase: GetProductUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetProductUseCase::class.java)
                .newInstance(getProductUseCase)
        }
    }
}