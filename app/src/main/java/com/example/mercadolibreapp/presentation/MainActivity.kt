package com.example.mercadolibreapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.mercadolibreapp.core.MyApplication
import com.example.mercadolibreapp.domain.usecase.GetProductsBySearchUseCase
import com.example.mercadolibreapp.navigation.NavigationHost
import com.example.mercadolibreapp.presentation.ui.theme.MercadoLibreAppTheme
import com.example.mercadolibreapp.presentation.viewmodel.SearchViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getProductsBySearchUseCase: GetProductsBySearchUseCase

    private val searchViewModel: SearchViewModel by viewModels(
        factoryProducer = { SearchViewModel.SearchViewModelFactory(getProductsBySearchUseCase) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).component.inject(this)
        setContent {
            MercadoLibreAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background){
                    NavigationHost(searchViewModel)
                }
            }
        }
    }
}