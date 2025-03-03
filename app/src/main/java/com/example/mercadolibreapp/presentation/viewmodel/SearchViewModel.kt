package com.example.mercadolibreapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.mercadolibreapp.data.models.DataProducts
import com.example.mercadolibreapp.domain.usecase.GetProductsBySearchUseCase
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class SearchViewModel(private val getProductsBySearchUseCase: GetProductsBySearchUseCase): ViewModel() {

    private var listData: MutableLiveData<DataProducts> =
        MutableLiveData<DataProducts>()


    fun setListData(result: DataProducts) {
        listData.postValue(result)
    }

    fun reset(){
        listData = MutableLiveData<DataProducts>()
    }

    fun getProducts(query: String) {
        viewModelScope.launch {
            setListData(getProductsBySearchUseCase.call(query))
        }
    }

    fun getProductsLiveData(): LiveData<DataProducts> {
        return listData
    }

    class SearchViewModelFactory(private val getProductsBySearchUseCase: GetProductsBySearchUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetProductsBySearchUseCase::class.java).newInstance(getProductsBySearchUseCase)
        }
    }
}