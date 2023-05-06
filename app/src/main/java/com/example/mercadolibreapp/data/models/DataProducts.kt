package com.example.mercadolibreapp.data.models

import com.example.mercadolibreapp.helpers.Constants

/**
 * @author Axel Sanchez
 */
data class DataProducts(
    val products: List<ResponseDTO.Product?>? = null,
    val apiError: Constants.ApiError? = null
)