package com.naufalmaulanaartocarpussavero607062300078.asesment2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.screen.MainScreen
import com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.screen.ProdukListScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.ProductList.route) {
            ProdukListScreen(navController)
        }
    }
}