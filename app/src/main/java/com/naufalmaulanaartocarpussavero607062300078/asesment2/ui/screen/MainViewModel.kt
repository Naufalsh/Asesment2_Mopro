package com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.screen

import androidx.lifecycle.ViewModel
import com.naufalmaulanaartocarpussavero607062300078.asesment2.model.Product

class MainViewModel : ViewModel() {
    val data = listOf(
        Product(
            id = 1,
            name = "Ayam",
            descProduct = "Ayam Goreng Goreng Goreng Goreng Bebek Bebek Ayam",
            price = 100000.0
        ),
        Product(
            id = 2,
            name = "Bebek",
            descProduct = "Ayam Goreng Goreng Goreng Goreng Bebek Bebek Ayam",
            price = 100000.0
        ),
        Product(
            id = 3,
            name = "Ayam",
            descProduct = "Ayam Goreng Goreng Goreng Goreng Bebek Bebek Ayam",
            price = 100000.0
        ),
        Product(
            id = 4,
            name = "Ayam",
            descProduct = "Ayam Goreng Goreng Goreng Goreng Bebek Bebek Ayam",
            price = 100000.0
        ),
        Product(
            id = 5,
            name = "Ayam",
            descProduct = "Ayam Goreng Goreng Goreng Goreng Bebek Bebek Ayam",
            price = 100000.0
        ),
    )

    fun getProductById(id: Long): Product? {
        return data.find {it.id == id}
    }

}