package com.example.mercadolibreapp.di.component

import com.example.mercadolibreapp.di.module.ApplicationModule
import com.example.mercadolibreapp.presentation.DetailsFragment
import com.example.mercadolibreapp.presentation.MainActivity
import com.example.mercadolibreapp.presentation.SearchFragment
import dagger.Component
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent{
    fun inject(searchFragment: SearchFragment)
    fun inject(detailsFragment: DetailsFragment)
    fun inject(mainActivity: MainActivity)
}