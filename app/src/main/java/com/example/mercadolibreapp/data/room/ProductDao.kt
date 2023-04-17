package com.example.mercadolibreapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mercadolibreapp.data.models.ResponseDTO.*

/**
 * @author Axel Sanchez
 */
@Dao
interface ProductDao {
    @Query("SELECT * FROM Product WHERE search = :search")
    suspend fun getProductBySearch(search: String): List<Product?>

    @Query("SELECT * FROM Product where id = :idProduct")
    suspend fun getProduct(idProduct: String): Product?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product?): Long
}