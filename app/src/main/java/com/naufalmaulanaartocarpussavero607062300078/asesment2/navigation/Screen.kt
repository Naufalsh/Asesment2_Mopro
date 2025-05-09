package com.naufalmaulanaartocarpussavero607062300078.asesment2.navigation

import com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.screen.KEY_ID_PRODUCT

sealed class Screen (val route: String) {
    data object Home: Screen("mainScreen")
    data object ListProduct : Screen("listProductScreen")
    data object ProductList : Screen("produkListScreen")
    data object AddProduct : Screen("addProductScreen")
    data object ubahProduct : Screen("detailScreen/{$KEY_ID_PRODUCT}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
    data object AddSales : Screen("addSalesScreen")
}