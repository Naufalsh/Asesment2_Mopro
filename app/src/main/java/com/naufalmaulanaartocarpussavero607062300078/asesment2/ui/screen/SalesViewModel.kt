package com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.naufalmaulanaartocarpussavero607062300078.asesment2.database.AppDatabase
import com.naufalmaulanaartocarpussavero607062300078.asesment2.model.Product
import com.naufalmaulanaartocarpussavero607062300078.asesment2.model.Sales
import com.naufalmaulanaartocarpussavero607062300078.asesment2.model.SalesWithProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SalesViewModel(application: Application) : AndroidViewModel(application) {

    private val salesDao = AppDatabase.getInstance(application).salesDao()
    private val productDao = AppDatabase.getInstance(application).productDao()

    private val _allSales = MutableStateFlow<List<SalesWithProduct>>(emptyList())
    val allSales: StateFlow<List<SalesWithProduct>> = _allSales.asStateFlow()

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts: StateFlow<List<Product>> = _allProducts.asStateFlow()

    init {
        loadAllSales()
        loadAllProducts()
    }

    private fun loadAllSales() {
        viewModelScope.launch(Dispatchers.IO) {
            salesDao.getAllSalesWithProduct()
                .collect { sales ->
                    _allSales.value = sales
                }
        }
    }

    private fun loadAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.getAllProducts()
                .collect { products ->
                    _allProducts.value = products
                }
        }
    }

    fun insertSales(sales: Sales) {
        viewModelScope.launch(Dispatchers.IO) {
            salesDao.insertSale(sales)
            loadAllSales()
        }
    }

    fun updateSales(sales: Sales) {
        viewModelScope.launch(Dispatchers.IO) {
            salesDao.updateSale(sales)
            loadAllSales()
        }
    }

    fun deleteSales(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            salesDao.deleteSale(id)
            loadAllSales()
        }
    }

    fun getSalesById(id: Long): SalesWithProduct? {
        return runBlocking {
            salesDao.getSalesById(id)
        }
    }
}
