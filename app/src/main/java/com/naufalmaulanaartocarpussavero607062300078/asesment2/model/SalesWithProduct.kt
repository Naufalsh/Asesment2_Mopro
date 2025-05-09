package com.naufalmaulanaartocarpussavero607062300078.asesment2.model

import androidx.room.Embedded
import androidx.room.Relation

data class SalesWithProduct(
    @Embedded val sales: Sales,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: Product
)
