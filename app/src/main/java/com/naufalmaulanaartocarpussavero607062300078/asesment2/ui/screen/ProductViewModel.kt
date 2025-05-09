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

    // DAO untuk akses database
    private val productDao = AppDatabase.getInstance(application).productDao()

    // StateFlow untuk menyimpan daftar produk
    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts: StateFlow<List<Product>> = _allProducts.asStateFlow()

    init {
        // Memuat data produk saat ViewModel diinisialisasi
        loadProducts()
    }

    // Fungsi untuk mengambil semua produk dari database
    private fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.getAllProducts()
                .collect { products ->
                    _allProducts.value = products
                }
        }
    }

    // Fungsi untuk menambahkan produk ke dalam database
    fun insertProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.insertProduct(product)
            loadProducts() // Perbarui daftar produk setelah ditambahkan
        }
    }

    // Fungsi untuk menghapus produk dari database
    fun deleteProduct(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.deleteById(id)
            loadProducts() // Perbarui daftar produk setelah dihapus
        }
    }

    // Fungsi untuk memperbarui data produk di database
    fun updateProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.updateProduct(product)
            loadProducts() // Perbarui daftar produk setelah diubah
        }
    }

    // Fungsi untuk mendapatkan produk berdasarkan ID
    fun getProductById(id: Long): Product? {
        return runBlocking {
            productDao.getProductById(id)
        }
    }
}
