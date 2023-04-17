package com.example.mercadolibreapp.helpers

import com.example.mercadolibreapp.data.models.ResponseDTO.*

object DummyProducts {
    val product4 = Product(
        id = "MLA1199944090",
        title = "Teclado Bluetooth Satechi Slim St-btsx1m Qwerty Inglés Us Color Gris",
        price = 31999
    )

    val product3 = Product(
        id = "MLA1114512613",
        price = 12595,
        title = "Kit De Teclado Y Mouse Inalámbrico Logitech Mk235 Español De Color Negro"
    )

    val product2 = Product(
        id = "MLA884927762",
        price = 12861,
        title = "Teclado Bluetooth Logitech K380 Qwerty Español Color Rosa"
    )

    val product1 = Product(
        id = "MLA1377613858",
        price = 91249,
        title = "Teclado Musical Casio Ctk-3500 61 Teclas Negro"
    )

    fun getListProducts(): Either<Constants.ApiError, List<Product?>> {
        val listProducts = arrayListOf<Product?>(product1, product2, product3, product4)
        return Either.Right(listProducts.toList())
    }

    const val TECLADO = "teclado"
}