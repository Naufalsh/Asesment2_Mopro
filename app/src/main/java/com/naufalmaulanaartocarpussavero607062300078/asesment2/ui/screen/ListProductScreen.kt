package com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.naufalmaulanaartocarpussavero607062300078.asesment2.R
import com.naufalmaulanaartocarpussavero607062300078.asesment2.model.Product
import com.naufalmaulanaartocarpussavero607062300078.asesment2.model.SettingsDataStore
import com.naufalmaulanaartocarpussavero607062300078.asesment2.navigation.Screen
import com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.components.BottomNavItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListProductScreen(navController: NavHostController) {
    val dataStore = SettingsDataStore(LocalContext.current)
    val showList by dataStore.layoutFlow.collectAsState(true)

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
                actions = {
                    IconButton(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStore.saveLayout(!showList)
                        }
                    }) {
                        Icon(
                            painter = painterResource(
                                if (showList) R.drawable.baseline_grid_view_24
                                else R.drawable.baseline_view_list_24
                            ),
                            contentDescription = stringResource(
                                if (showList) R.string.grid
                                else R.string.list
                        ),
                        tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBars(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddProduct.route)
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
        ScreenContents(showList, modifier = Modifier.padding(innerPadding), navController)
    }
}


@Composable
fun ScreenContents(showList: Boolean, modifier: Modifier = Modifier, navController: NavHostController) {
    val viewModel: ProductViewModel = viewModel()
    val data by viewModel.allProducts.collectAsState()


    if (data.isEmpty()) {
        Column (
            modifier = modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) { Text( text = stringResource(id = R.string.list_kosong)) }
    } else {
        if (showList) {
            LazyColumn(
                modifier = modifier.fillMaxSize()
            ) {
                items(data) {
                    ListItems(product = it) {
                        navController.navigate(Screen.ubahProduct.withId(it.id))
                    }
                    HorizontalDivider()
                }
            }
        }
        else {
            LazyVerticalStaggeredGrid(
                modifier = modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 84.dp)
            ) {
                items(data) {
                    GridItem(product = it) {
                        navController.navigate(Screen.ubahProduct.withId(it.id))
                    }
                }
            }
        }

    }
}

@Composable
fun BottomNavigationBars(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.DrawableIconItem(
            navName = "Penjualan",
            navRoute = Screen.Home.route,
            iconResId = R.drawable.baseline_sell_24 // Ganti dengan drawable Anda
        ),
        BottomNavItem.DrawableIconItem(
            navName = "Produk",
            navRoute = Screen.ListProduct.route,
            iconResId = R.drawable.baseline_shopping_bag_24
        )
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { item.Icon(isSelected = currentRoute == item.route) },
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
                    unselectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Composable
fun ListItems(product: Product, onClick: () -> Unit) {
    Column (
        modifier = Modifier.fillMaxWidth()
            .clickable{onClick()}
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = product.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = product.descProduct,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = product.price.toString())
    }
}

@Composable
fun GridItem(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable() { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, DividerDefaults.color)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(
                text = product.name,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = product.descProduct,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = product.price.toString())
        }
    }
}