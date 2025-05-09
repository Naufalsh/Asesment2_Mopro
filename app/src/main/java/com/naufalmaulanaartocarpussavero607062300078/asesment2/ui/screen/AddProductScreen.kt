package com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.naufalmaulanaartocarpussavero607062300078.asesment2.R
import com.naufalmaulanaartocarpussavero607062300078.asesment2.model.Product

const val KEY_ID_PRODUCT = "idProduct"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(navController: NavHostController, id: Long? = null) {
    val viewModel: ProductViewModel = viewModel()

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    // Load product data if ID is not null (edit mode)
    LaunchedEffect(id) {
        if (id != null) {
            val product = viewModel.getProductById(id)
            product?.let {
                name = it.name
                description = it.descProduct
                price = it.price.toString()
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
                            stringResource(id = R.string.tambah_produk)
                        else
                            stringResource(id = R.string.edit_produk)
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(
                        onClick = {
                            val priceValue = price.toDoubleOrNull() ?: 0.0
                            val product = Product(
                                id = id ?: 0L,
                                name = name,
                                descProduct = description,
                                price = priceValue
                            )
                            if (id == null) {
                                viewModel.insertProduct(product)
                            } else {
                                viewModel.updateProduct(product)
                            }
                            navController.popBackStack()
                        }
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
            FormTambahProduk(
                namaProduk = name,
                onTitleChange = { name = it },
                descProduk = description,
                onDescChange = { description = it },
                harga = price,
                onPriceChange = { price = it },
                modifier = Modifier.padding(innerPadding)
            )
        }
    )
}

@Composable
fun FormTambahProduk(
    namaProduk: String,
    onTitleChange: (String) -> Unit,
    descProduk: String,
    onDescChange: (String) -> Unit,
    harga: String,
    onPriceChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = namaProduk,
            onValueChange = onTitleChange,
            label = { Text(text = stringResource(id = R.string.nama_produk)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = descProduk,
            onValueChange = onDescChange,
            label = { Text(text = stringResource(id = R.string.deskripsi_produk)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
            modifier = Modifier.fillMaxWidth(),
            minLines = 5
        )
        OutlinedTextField(
            value = harga,
            onValueChange = onPriceChange,
            label = { Text(text = stringResource(id = R.string.harga)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
