package com.example.mercadolibreapp.presentation.navigation

/**
 * @author Axel Sanchez
 */
sealed class Destinations(
    var route: String
){
    object HomeScreen: Destinations("homeScreen")
    object SearchScreen: Destinations("searchScreen/{query}"){
        fun createRoute(query: String) = "searchScreen/$query"
    }
}
