package com.example.mercadolibreapp.data.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mercadolibreapp.data.models.DataProducts
import com.example.mercadolibreapp.data.models.Description
import com.example.mercadolibreapp.data.models.ProductDetails
import com.example.mercadolibreapp.data.service.ApiServiceProduct
import com.example.mercadolibreapp.helpers.Constants.ApiError.*
import com.example.mercadolibreapp.helpers.NetworkHelper
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface ProductRemoteSource {
    suspend fun getProducts(query: String): MutableLiveData<DataProducts>
    suspend fun getProductDetails(idProduct: String): MutableLiveData<ProductDetails>
    suspend fun getDescription(idProduct: String): MutableLiveData<Description?>
}

@Singleton
class ProductRemoteSourceImpl @Inject constructor(private val service: ApiServiceProduct,
                                                  private val networkHelper: NetworkHelper) : ProductRemoteSource {
    override suspend fun getProducts(query: String): MutableLiveData<DataProducts> {
        val mutableLiveData = MutableLiveData<DataProducts>()

        try {
            if (!networkHelper.isOnline()) {
                mutableLiveData.value = DataProducts(apiError = NETWORK_ERROR)
                return mutableLiveData
            }

            val response = service.getProducts(query)
            if (response.isSuccessful) {
                Log.i("Successful Response", response.body().toString())

                response.body()?.let { result ->
                    mutableLiveData.value = DataProducts(products = result.results?: listOf())
                } ?: kotlin.run {
                    mutableLiveData.value = DataProducts(apiError = GENERIC)
                }
            } else {
                Log.i("Error Response", response.errorBody().toString())
                val apiError = GENERIC
                apiError.error = response.message()
                mutableLiveData.value = DataProducts(apiError = apiError)
            }
        } catch (e: IOException) {
            mutableLiveData.value = DataProducts(apiError = GENERIC)
            Log.e(
                "ProductRemoteSourceImpl",
                e.message?:"Error al obtener los productos"
            )
            e.printStackTrace()
        }

        return mutableLiveData
    }

    override suspend fun getProductDetails(idProduct: String): MutableLiveData<ProductDetails> {
        val mutableLiveData = MutableLiveData<ProductDetails>()

        try {
            if (!networkHelper.isOnline()) {
                mutableLiveData.value = ProductDetails(id = "", apiError = NETWORK_ERROR)
                return mutableLiveData
            }

            val response = service.getDetailsById(idProduct)
            if (response.isSuccessful) {
                Log.i("Successful Response", response.body().toString())

                response.body()?.let { result ->
                    mutableLiveData.value = result
                } ?: kotlin.run {
                    mutableLiveData.value = ProductDetails(id = "", apiError = GENERIC_DETAILS)
                }
            } else {
                Log.i("Error Response", response.errorBody().toString())
                val apiError = GENERIC
                apiError.error = response.message()
                mutableLiveData.value = ProductDetails(id = "", apiError = apiError)
            }
        } catch (e: IOException) {
            mutableLiveData.value = ProductDetails(id = "", apiError = GENERIC_DETAILS)
            Log.e(
                "ProductRemoteSourceImpl",
                e.message?:"Error al obtener los detalles del producto"
            )
            e.printStackTrace()
        }

        return mutableLiveData
    }

    override suspend fun getDescription(idProduct: String): MutableLiveData<Description?> {
        val mutableLiveData = MutableLiveData<Description?>()
        try {
            val response = service.getDescription(idProduct)
            if (response.isSuccessful) {
                Log.i("Successful Response", response.body().toString())

                mutableLiveData.value = response.body()
            } else {
                Log.i("Error Response", response.errorBody().toString())
                mutableLiveData.value = response.body()
            }
        } catch (e: IOException) {
            mutableLiveData.value = null
            Log.e(
                "ProductRemoteSourceImpl",
                e.message?:"Error al obtener los detalles del producto"
            )
            e.printStackTrace()
        }

        return mutableLiveData
    }
}