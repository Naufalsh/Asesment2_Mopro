package com.naufalmaulanaartocarpussavero607062300078.asesment2.navigation

sealed class Screen (val route: String) {
    data object Home: Screen("mainScreen")
    data object ListProduct : Screen("listProductScreen")
    data object ProductList : Screen("produkListScreen")
    data object AddProduct : Screen("addProductScreen")
}