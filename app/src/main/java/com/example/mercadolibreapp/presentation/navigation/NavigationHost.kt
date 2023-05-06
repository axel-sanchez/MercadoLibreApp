package com.example.mercadolibreapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mercadolibreapp.helpers.Constants.QUERY
import com.example.mercadolibreapp.presentation.HomeScreen
import com.example.mercadolibreapp.presentation.SearchScreen
import com.example.mercadolibreapp.presentation.navigation.Destinations.*
import com.example.mercadolibreapp.presentation.viewmodel.SearchViewModel

/**
 * @author Axel Sanchez
 */

@Composable
fun NavigationHost(searchViewModel: SearchViewModel) {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = HomeScreen.route){
        composable(HomeScreen.route){
            HomeScreen(
                navigateSearchScreen = { query ->
                    navController.navigate(SearchScreen.createRoute(query))
                }
            )
        }

        composable(SearchScreen.route){ navBackStackEntry ->
            val query = navBackStackEntry.arguments?.getString(QUERY)?:""
            SearchScreen(query, searchViewModel)
        }

    }
}