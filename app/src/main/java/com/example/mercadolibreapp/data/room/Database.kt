package com.example.mercadolibreapp.data.room

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Database
import com.example.mercadolibreapp.data.models.Description
import com.example.mercadolibreapp.data.models.Picture
import com.example.mercadolibreapp.data.models.ProductDetails
import com.example.mercadolibreapp.data.models.ResponseDTO.*

/**
 * @author Axel Sanchez
 */
@Database(
    entities = [Product::class, ProductDetails::class, Picture::class, Description::class],
    version = 3
)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase() {
    abstract fun productDao(): ProductDao
}