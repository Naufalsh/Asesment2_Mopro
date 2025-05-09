package com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.naufalmaulanaartocarpussavero607062300078.asesment2.R
import com.naufalmaulanaartocarpussavero607062300078.asesment2.model.Product
import com.naufalmaulanaartocarpussavero607062300078.asesment2.model.Sales
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSalesScreen(navController: NavHostController) {
    val context = LocalContext.current
    val salesViewModel: SalesViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SalesViewModel(context.applicationContext as Application) as T
            }
        }
    )

    // State variables
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var soldQuantity by remember { mutableStateOf("") }
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }

    // Load products from database
    LaunchedEffect(Unit) {
        salesViewModel.allProducts.collect { productList ->
            products = productList
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = { Text(text = stringResource(id = R.string.tambah_penjualan)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(
                        onClick = {
                            selectedProduct?.let { product ->
                                val quantity = soldQuantity.toIntOrNull() ?: 0
                                if (quantity > 0) {
                                    val sales = Sales(
                                        productId = product.id,
                                        quantity = quantity,
                                        totalPrice = product.price * quantity
                                    )
                                    salesViewModel.insertSales(sales)
                                    navController.popBackStack()
                                }
                            }
                        },
                        enabled = selectedProduct != null && soldQuantity.toIntOrNull() ?: 0 > 0
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(id = R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Product Dropdown
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = selectedProduct?.name ?: "",
                        onValueChange = {},
                        label = { Text(stringResource(R.string.pilih_produk)) },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { isDropdownExpanded = true }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = stringResource(R.string.dropdown_produk)
                                )
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        products.forEach { product ->
                            DropdownMenuItem(
                                text = { Text(product.name) },
                                onClick = {
                                    selectedProduct = product
                                    isDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                // Auto-filled fields based on selected product
                OutlinedTextField(
                    value = selectedProduct?.descProduct ?: "",
                    onValueChange = {},
                    label = { Text(stringResource(R.string.deskripsi_produk)) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    minLines = 3
                )

                OutlinedTextField(
                    value = selectedProduct?.price?.let {
                        "Rp ${NumberFormat.getNumberInstance(Locale.getDefault()).format(it)}"
                    } ?: "",
                    onValueChange = {},
                    label = { Text(stringResource(R.string.harga_satuan)) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                // Quantity input field
                OutlinedTextField(
                    value = soldQuantity,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() || newValue.toIntOrNull() != null) {
                            soldQuantity = newValue
                        }
                    },
                    label = { Text(stringResource(R.string.jumlah_produk_terjual)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    isError = soldQuantity.isNotEmpty() && soldQuantity.toIntOrNull() == null
                )

                // Total Price (calculated)
                selectedProduct?.let { product ->
                    val quantity = soldQuantity.toIntOrNull() ?: 0
                    val total = product.price * quantity
                    OutlinedTextField(
                        value = "Rp ${NumberFormat.getNumberInstance(Locale.getDefault()).format(total)}",
                        onValueChange = {},
                        label = { Text(stringResource(R.string.total_harga)) },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true
                    )
                }
            }
        }
    )
}