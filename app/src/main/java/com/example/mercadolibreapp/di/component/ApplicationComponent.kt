package com.example.mercadolibreapp.di.component

import com.example.mercadolibreapp.di.module.ApplicationModule
import com.example.mercadolibreapp.navigation.Destinations.*
import com.example.mercadolibreapp.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent{
    fun inject(searchScreen: SearchScreen)
    fun inject(detailsScreen: DetailsScreen)
    fun inject(mainActivity: MainActivity)
}