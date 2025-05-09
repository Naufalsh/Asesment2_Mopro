package com.naufalmaulanaartocarpussavero607062300078.asesment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val descProduct: String,
    val price: Double
    )
