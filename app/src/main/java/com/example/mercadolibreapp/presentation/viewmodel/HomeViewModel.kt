package com.example.mercadolibreapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author Axel Sanchez
 */
class HomeViewModel: ViewModel() {
    private val authLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun setAuth(auth: Boolean) {
        authLiveData.postValue(auth)
    }

    fun getAuth(): Boolean {
        return authLiveData.value?:false
    }
}