package com.example.mercadolibreapp.data.repository

import com.example.mercadolibreapp.data.models.*
import com.example.mercadolibreapp.domain.repository.ProductRepository

/**
 * @author Axel Sanchez
 */
class FakeRepository : ProductRepository {
    val product4 = ResponseDTO.Product(
        id = "MLA1199944090",
        title = "Teclado Bluetooth Satechi Slim St-btsx1m Qwerty Inglés Us Color Gris",
        price = 31999
    )

    val product3 = ResponseDTO.Product(
        id = "MLA1114512613",
        price = 12595,
        title = "Kit De Teclado Y Mouse Inalámbrico Logitech Mk235 Español De Color Negro"
    )

    val product2 = ResponseDTO.Product(
        id = "MLA884927762",
        price = 12861,
        title = "Teclado Bluetooth Logitech K380 Qwerty Español Color Rosa"
    )

    val product1 = ResponseDTO.Product(
        id = "MLA1377613858",
        price = 91249,
        title = "Teclado Musical Casio Ctk-3500 61 Teclas Negro"
    )

    val description = Description(1, "Esta es una descripcion de prueba")

    val productDetails = ProductDetails(
        id = product1.id,
        title = product1.title,
        description = description,
        pictures = listOf(
            Picture(
                "1",
                secure_url = "https://http2.mlstatic.com/D_NQ_NP_666867-MLA48161837344_112021-O.webp"
            ),
            Picture(
                "2",
                secure_url = "https://http2.mlstatic.com/D_NQ_NP_749370-MLA46440117464_062021-O.jpg"
            ),
            Picture(
                "3",
                secure_url = "https://http2.mlstatic.com/D_NQ_NP_988070-MLA52545987935_112022-O.jpg"
            )
        ),
        availableQuantity = 13,
        shipping = ResponseDTO.Product.Shipping(freeShipping = true),
        price = 10000
    )

    override suspend fun getProductsBySearch(query: String): DataProducts {
        return DataProducts(products = listOf(product1, product2, product3, product4))
    }

    override suspend fun getProductDetails(idProduct: String): ProductDetails {
        return productDetails
    }

    override suspend fun getLocalProducts(query: String): List<ResponseDTO.Product?> {
        return listOf()
    }

    override suspend fun getRemoteProducts(query: String): DataProducts {
        return DataProducts(products = listOf(product1, product2, product3, product4))
    }

    companion object {
        const val TECLADO = "teclado"
    }
}