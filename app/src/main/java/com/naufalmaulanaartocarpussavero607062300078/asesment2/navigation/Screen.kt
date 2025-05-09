package com.naufalmaulanaartocarpussavero607062300078.asesment2.navigation

sealed class Screen (val route: String) {
    data object Home: Screen("mainScreen")
}