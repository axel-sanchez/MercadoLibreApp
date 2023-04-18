package com.example.mercadolibreapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.mercadolibreapp.data.models.ResponseDTO.*
import com.example.mercadolibreapp.domain.usecase.GetProductsBySearchUseCase
import com.example.mercadolibreapp.helpers.Constants
import com.example.mercadolibreapp.helpers.Either
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class SearchViewModel(private val getProductsBySearchUseCase: GetProductsBySearchUseCase): ViewModel() {

    private val listData: MutableLiveData<Either<Constants.ApiError, List<Product?>>> =
        MutableLiveData<Either<Constants.ApiError, List<Product?>>>()


    fun setListData(result: Either<Constants.ApiError, List<Product?>>) {
        listData.postValue(result)
    }

    fun getProduct(query: String) {
        viewModelScope.launch {
            setListData(getProductsBySearchUseCase.call(query))
        }
    }

    fun getProductLiveData(): LiveData<Either<Constants.ApiError, List<Product?>>> {
        return listData
    }

    class SearchViewModelFactory(private val getProductsBySearchUseCase: GetProductsBySearchUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetProductsBySearchUseCase::class.java).newInstance(getProductsBySearchUseCase)
        }
    }
}