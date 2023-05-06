package com.example.mercadolibreapp.helpers

import androidx.test.platform.app.InstrumentationRegistry

/**
 * @author Axel Sanchez
 */
object Constants {
    const val QUERY = "query"
    const val MERCADO_LIBRE_PACKAGE = "com.mercadolibre"
    const val ID_PRODUCT = "idProduct"
    const val ID_IMAGE_VIEW = "imageView"
    const val BASE_URL = "https://api.mercadolibre.com/"

    //Endpoints
    const val GET_PRODUCTS = "sites/MLA/search"
    const val GET_DESCRIPTION = "items/{productId}/description"
    const val GET_DETAILS = "items/{productId}"

    enum class ApiError(var error: String) {
        GENERIC("Hubo un error al obtener los productos"),
        GENERIC_DETAILS("Hubo un error al obtener los detalles del producto"),
        EMPTY_PRODUCTS("No se obtuvo ningún producto"),
        NETWORK_ERROR("Hubo un error en la conexión de internet")
    }

    val isRunningTest = try {
        InstrumentationRegistry.getInstrumentation()
        true
    } catch (e: IllegalStateException) {
        false
    }
}

object ContentDescription{
    const val LOGO = "logo de mercado libre"
    const val START_ICON = "Start Icon"
}