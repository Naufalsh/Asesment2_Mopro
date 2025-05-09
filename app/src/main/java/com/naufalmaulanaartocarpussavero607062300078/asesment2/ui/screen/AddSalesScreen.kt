package com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.naufalmaulanaartocarpussavero607062300078.asesment2.R
import com.naufalmaulanaartocarpussavero607062300078.asesment2.model.Product
import com.naufalmaulanaartocarpussavero607062300078.asesment2.model.Sales

const val KEY_ID_SALES = "idSales"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSalesScreen(navController: NavHostController, id: Long? = null) {
    val viewModel: SalesViewModel = viewModel()

    var selectedProductId by remember { mutableStateOf<Long?>(null) }
    var quantity by remember { mutableStateOf("") }
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    // Load sales data if ID is not null (edit mode)
    LaunchedEffect(id) {
        if (id != null) {
            val salesWithProduct = viewModel.getSalesById(id)
            salesWithProduct?.let {
                selectedProductId = it.sales.productId
                quantity = it.sales.quantity.toString()
                productName = it.product.name
                productPrice = it.product.price.toString()
            }
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
                title = {
                    Text(
                        text = if (id == null)
                            stringResource(id = R.string.tambah_penjualan)
                        else
                            stringResource(id = R.string.edit_penjualan)
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(
                        onClick = {
                            selectedProductId?.let { productId ->
                                val quantityValue = quantity.toIntOrNull() ?: 0
                                val priceValue = productPrice.toDoubleOrNull() ?: 0.0
                                val totalPrice = priceValue * quantityValue

                                val sales = Sales(
                                    id = id ?: 0L,
                                    productId = productId,
                                    quantity = quantityValue,
                                    totalPrice = totalPrice
                                )

                                if (id == null) {
                                    viewModel.insertSales(sales)
                                } else {
                                    viewModel.updateSales(sales)
                                }
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(id = R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id!=null)
                        DeleteAction {
                            showDialog = true
                        }
                }
            )
        },
        content = { innerPadding ->
            FormTambahPenjualan(
                productName = productName,
                onProductSelected = { product ->
                    selectedProductId = product.id
                    productName = product.name
                    productPrice = product.price.toString()
                },
                quantity = quantity,
                onQuantityChange = { quantity = it },
                productPrice = productPrice,
                totalPrice = (productPrice.toDoubleOrNull() ?: 0.0) * (quantity.toIntOrNull() ?: 0),
                modifier = Modifier.padding(innerPadding)
            )
            if (id!=null && showDialog) {
                DisplayAlertDialog(
                    onDismissRequest = { showDialog = false }) {
                    showDialog = false
                    viewModel.deleteSales(id)
                    navController.popBackStack()
                }
            }
        }
    )
}

@Composable
fun FormTambahPenjualan(
    productName: String,
    onProductSelected: (Product) -> Unit,
    quantity: String,
    onQuantityChange: (String) -> Unit,
    productPrice: String,
    totalPrice: Double,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Product Selection (Dropdown)
        ProductDropdown(
            selectedProductName = productName,
            onProductSelected = onProductSelected
        )

        // Product Price (read-only)
        OutlinedTextField(
            value = productPrice,
            onValueChange = {},
            label = { Text(stringResource(R.string.harga_satuan)) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        // Quantity Input
        OutlinedTextField(
            value = quantity,
            onValueChange = { newValue ->
                if (newValue.isEmpty() || newValue.toIntOrNull() != null) {
                    onQuantityChange(newValue)
                }
            },
            label = { Text(stringResource(R.string.jumlah_produk_terjual)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Total Price (read-only)
        OutlinedTextField(
            value = "Rp ${"%.2f".format(totalPrice)}",
            onValueChange = {},
            label = { Text(stringResource(R.string.total_harga)) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
    }
}

// Product Dropdown Component
@Composable
fun ProductDropdown(
    selectedProductName: String,
    onProductSelected: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val viewModel: ProductViewModel = viewModel()
    val products by viewModel.allProducts.collectAsState(initial = emptyList())

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedProductName,
            onValueChange = {},
            label = { Text(stringResource(R.string.pilih_produk)) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = stringResource(R.string.pilih_produk)
                    )
                }
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            products.forEach { product ->
                DropdownMenuItem(
                    text = { Text(product.name) },
                    onClick = {
                        onProductSelected(product)
                        expanded = false
                    }
                )
            }
        }
    }
}