package com.example.mercadolibreapp.helpers

object Constants {
    const val QUERY = "query"
    const val MERCADO_LIBRE_PACKAGE = "com.mercadolibre"
    const val ID_PRODUCT = "idProduct"
    const val ID_IMAGE_VIEW = "imageView"
    const val BASE_URL = "https://api.mercadolibre.com/sites/MLA/"

    enum class ApiError(var error: String) {
        API_ERROR("Error al obtener los productos"),
        NETWORK_ERROR("Hubo un error de conexión a internet")
    }
}