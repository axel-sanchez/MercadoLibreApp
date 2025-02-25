package com.example.mercadolibreapp.navigation

import com.example.mercadolibreapp.helpers.Constants.ID_PRODUCT
import com.example.mercadolibreapp.helpers.Constants.QUERY

/**
 * @author Axel Sanchez
 */
sealed class Destinations(
    var route: String
){
    object HomeScreen: Destinations("homeScreen")
    object SearchScreen: Destinations("searchScreen/{$QUERY}"){
        fun createRoute(query: String) = "searchScreen/$query"
    }

    object DetailsScreen: Destinations("detailsScreen/{$ID_PRODUCT}"){
        fun createRoute(idProduct: String) = "detailsScreen/$idProduct"
    }
}
