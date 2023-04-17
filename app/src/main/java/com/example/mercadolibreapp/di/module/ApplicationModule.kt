package com.example.mercadolibreapp.di.module

import android.content.Context
import androidx.room.Room
import com.example.mercadolibreapp.data.repository.ProductRepositoryImpl
import com.example.mercadolibreapp.data.room.Database
import com.example.mercadolibreapp.data.service.ApiClient
import com.example.mercadolibreapp.data.service.ApiServiceProduct
import com.example.mercadolibreapp.data.source.ProductLocalSource
import com.example.mercadolibreapp.data.source.ProductLocalSourceImpl
import com.example.mercadolibreapp.data.source.ProductRemoteSource
import com.example.mercadolibreapp.data.source.ProductRemoteSourceImpl
import com.example.mercadolibreapp.domain.usecase.GetProductsBySearchUseCase
import com.example.mercadolibreapp.domain.usecase.GetProductsBySearchUseCaseImpl
import com.example.mercadolibreapp.domain.repository.ProductRepository
import com.example.mercadolibreapp.domain.usecase.GetProductUseCase
import com.example.mercadolibreapp.domain.usecase.GetProductUseCaseImpl
import com.example.mercadolibreapp.helpers.Constants.BASE_URL
import com.example.mercadolibreapp.helpers.NetworkHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Module
class ApplicationModule(private val context: Context){
    @Provides
    @Singleton
    fun provideProductRepository(productRepository: ProductRepositoryImpl): ProductRepository = productRepository

    @Provides
    @Singleton
    fun provideProductRemoteSource(productRemoteSource: ProductRemoteSourceImpl): ProductRemoteSource = productRemoteSource

    @Provides
    @Singleton
    fun provideGetAllProductsUseCase(getAllProductsUseCase: GetProductsBySearchUseCaseImpl): GetProductsBySearchUseCase = getAllProductsUseCase

    @Provides
    @Singleton
    fun provideGetProductUseCase(getProductUseCase: GetProductUseCaseImpl): GetProductUseCase = getProductUseCase

    @Provides
    @Singleton
    fun provideApiServiceProduct(): ApiServiceProduct {
        return ApiClient.Builder<ApiServiceProduct>()
            .setBaseUrl(BASE_URL)
            .setApiService(ApiServiceProduct::class.java)
            .build()
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database {
        return Room
            .databaseBuilder(context, Database::class.java, "MercadoLibreDB.db1")
            .build()
    }

    @Provides
    @Singleton
    fun provideProductLocalSource(database: Database): ProductLocalSource {
        return ProductLocalSourceImpl(database.productDao())
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(context: Context) = NetworkHelper(context)

    @Provides
    @Singleton
    fun provideContext(): Context = context
}