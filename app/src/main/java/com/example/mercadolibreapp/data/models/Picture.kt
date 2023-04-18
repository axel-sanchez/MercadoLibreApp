package com.example.mercadolibreapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity data class Picture(
    @PrimaryKey var id: String,
    var max_size: String? = null,
    var quality: String? = null,
    var secure_url: String? = null,
    var size: String? = null,
    var url: String? = null
)