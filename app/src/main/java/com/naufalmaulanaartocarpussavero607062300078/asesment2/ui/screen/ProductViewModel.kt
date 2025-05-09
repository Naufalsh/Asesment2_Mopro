package com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.naufalmaulanaartocarpussavero607062300078.asesment2.database.AppDatabase
import com.naufalmaulanaartocarpussavero607062300078.asesment2.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val productDao = AppDatabase.getInstance(application).productDao()

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts: StateFlow<List<Product>> = _allProducts.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.getAllProducts()
                .collect { products ->
                    _allProducts.value = products
                }
        }
    }

    fun insertProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.insertProduct(product)
            loadProducts()
        }
    }

    fun deleteProduct(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.deleteById(id)
            loadProducts()
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.updateProduct(product)
            loadProducts()
        }
    }

    fun getProductById(id: Long): Product? {
        return runBlocking {
            productDao.getProductById(id)
        }
    }
}
