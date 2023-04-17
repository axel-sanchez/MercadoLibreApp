package com.example.mercadolibreapp.core

import android.app.Application
import com.example.mercadolibreapp.di.component.ApplicationComponent
import com.example.mercadolibreapp.di.component.DaggerApplicationComponent
import com.example.mercadolibreapp.di.module.ApplicationModule

/**
 * @author Axel Sanchez
 */
class MyApplication: Application() {
    val component: ApplicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(ApplicationModule(this))
        .build()
}