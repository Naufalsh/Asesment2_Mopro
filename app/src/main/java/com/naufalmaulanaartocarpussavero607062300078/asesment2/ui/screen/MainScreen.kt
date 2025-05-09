package com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.collectAsState

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naufalmaulanaartocarpussavero607062300078.asesment2.R
import com.naufalmaulanaartocarpussavero607062300078.asesment2.model.Product
import com.naufalmaulanaartocarpussavero607062300078.asesment2.model.SalesWithProduct
import com.naufalmaulanaartocarpussavero607062300078.asesment2.navigation.Screen
import com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.components.BottomNavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        bottomBar = { BottomNavigationBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddSales.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.tambah_produk),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) { innerPadding ->
        ScreenContent(showList = true, modifier = Modifier.padding(innerPadding), navController)
    }
}

@Composable
fun ScreenContent(showList: Boolean, modifier: Modifier = Modifier, navController: NavHostController) {
    val viewModel: SalesViewModel = viewModel()
    val data by viewModel.allSales.collectAsState(initial = emptyList())



    if (data.isEmpty()) {
        Column (
            modifier = modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) { Text( text = stringResource(id = R.string.list_kosong)) }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(data) { saleWithProduct ->
                SaleItem(sale = saleWithProduct) {
                    navController.navigate(Screen.ubahProduct.withId(saleWithProduct.sales.id))
                }
                HorizontalDivider()
            }
        }

    }
}
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.BottomNavItem("Penjualan", Screen.Home.route, Icons.Filled.Warning),
        BottomNavItem.BottomNavItem("Produk", Screen.ListProduct.route, Icons.Filled.Info)
    )

    NavigationBar(
        containerColor =  MaterialTheme.colorScheme.primaryContainer,
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.name) },
                label = { Text(item.name) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor =  MaterialTheme.colorScheme.primary,
                    unselectedTextColor =  MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Composable
fun SaleItem(sale: SalesWithProduct, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Produk: ${sale.product.name}",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Jumlah: ${sale.sales.quantity}",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "Total Harga: Rp${sale.sales.totalPrice}",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//    MainScreen(navController = NavHostController())
//}
