package com.example.mercadolibreapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * @author Axel Sanchez
 */
@Entity data class Description(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @SerializedName("plain_text")
    val text: String?
)
