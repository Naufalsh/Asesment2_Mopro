package com.naufalmaulanaartocarpussavero607062300078.asesment2.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "sales",
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Sales(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val quantity: Int,
    val totalPrice: Double,
    val productId: Int
)
