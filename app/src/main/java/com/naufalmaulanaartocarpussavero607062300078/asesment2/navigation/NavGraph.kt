package com.naufalmaulanaartocarpussavero607062300078.asesment2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.screen.AddProductScreen
import com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.screen.AddSalesScreen
import com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.screen.KEY_ID_PRODUCT
import com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.screen.KEY_ID_SALES
import com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.screen.ListProductScreen
import com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.screen.MainScreen
import com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.screen.ProductListScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.ListProduct.route) {
            ListProductScreen(navController)
        }
        composable(route = Screen.AddProduct.route) {
            AddProductScreen(navController)
        }
        composable(
            route = Screen.ubahProduct.route,
            arguments = listOf(
                navArgument(KEY_ID_PRODUCT) { type = NavType.LongType }
            )
        ) {
            navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_PRODUCT)
            AddProductScreen(navController, id)
        }
        composable(route = Screen.AddSales.route) {
            AddSalesScreen(navController)
        }
        composable(
            route = Screen.ubahPenjualan.route,
            arguments = listOf(
                navArgument(KEY_ID_SALES) { type = NavType.LongType }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_SALES)
            AddSalesScreen(navController, id)
        }

    }
}