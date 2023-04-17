package com.example.mercadolibreapp.data.room

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Database
import com.example.mercadolibreapp.data.models.ResponseDTO.*

/**
 * @author Axel Sanchez
 */
@Database(
    entities = [Product::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase() {
    abstract fun productDao(): ProductDao
}