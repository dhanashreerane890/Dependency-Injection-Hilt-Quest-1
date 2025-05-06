package com.anywhere.dependency_injection_dagger_hilt.products.presentation.productSearch

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.draw.clip
import com.anywhere.dependency_injection_dagger_hilt.products.domain.model.Product
import com.anywhere.dependency_injection_dagger_hilt.products.domain.model.SortOrder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductSearchScreen(
    viewModel: SearchViewModel
) {
    val state by viewModel.productsState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state.error) {
        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Products") },
                actions = {
                    IconButton(onClick = { viewModel.onEvent(SearchEvent.Refresh) }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBar(
                query = state.searchParams.query,
                onQueryChange = { viewModel.onEvent(SearchEvent.QueryChanged(it)) }
            )

            FilterSection(
                sortOrder = state.searchParams.sortOrder,
                onSortOrderChanged = { viewModel.onEvent(SearchEvent.SortOrderChanged(it)) },
                minPrice = state.searchParams.minPrice,
                maxPrice = state.searchParams.maxPrice,
                onPriceRangeChanged = { min, max ->
                    viewModel.onEvent(SearchEvent.PriceRangeChanged(min, max))
                }
            )

            Box(modifier = Modifier.fillMaxSize()) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else if (state.products.isEmpty()) {
                    Text(
                        text = "No products found",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(state.products) { product ->
                            ProductItem(product = product)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        placeholder = { Text("Search products...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(24.dp)
    )
}

@Composable
fun FilterSection(
    sortOrder: SortOrder,
    onSortOrderChanged: (SortOrder) -> Unit,
    minPrice: Double?,
    maxPrice: Double?,
    onPriceRangeChanged: (Double?, Double?) -> Unit,
    modifier: Modifier = Modifier
) {
    var showPriceDialog by remember { mutableStateOf(false) }
    var tempMinPrice by remember { mutableStateOf(minPrice?.toString() ?: "") }
    var tempMaxPrice by remember { mutableStateOf(maxPrice?.toString() ?: "") }

    if (showPriceDialog) {
        AlertDialog(
            onDismissRequest = { showPriceDialog = false },
            title = { Text("Set Price Range") },
            text = {
                Column {
                    OutlinedTextField(
                        value = tempMinPrice,
                        onValueChange = { tempMinPrice = it },
                        label = { Text("Min Price") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = tempMaxPrice,
                        onValueChange = { tempMaxPrice = it },
                        label = { Text("Max Price") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    onPriceRangeChanged(
                        tempMinPrice.toDoubleOrNull(),
                        tempMaxPrice.toDoubleOrNull()
                    )
                    showPriceDialog = false
                }) {
                    Text("Apply")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPriceDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { showPriceDialog = true },
                modifier = Modifier.weight(1f)
            ) {
                Text("Price Filter")
            }

            Spacer(modifier = Modifier.width(16.dp))

            SortingSelector(
                sortOrder = sortOrder,
                onSortOrderChanged = onSortOrderChanged,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SortingSelector(
    sortOrder: SortOrder,
    onSortOrderChanged: (SortOrder) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Button(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sort ${getSortText(sortOrder)}")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                onSortOrderChanged(SortOrder.NONE)
                expanded = false
            }) {
                Text("Default")
            }
            DropdownMenuItem(onClick = {
                onSortOrderChanged(SortOrder.TITLE_ASC)
                expanded = false
            }) {
                Text("Title A-Z")
            }
            DropdownMenuItem(onClick = {
                onSortOrderChanged(SortOrder.TITLE_DESC)
                expanded = false
            }) {
                Text("Title Z-A")
            }
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = product.title,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$${product.price}",
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.description,
                maxLines = 3
            )
        }
    }
}

@Composable
fun DropdownMenuItem(
    onClick: () -> Unit,
    enabled: Boolean = true,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    val itemContentColor = if (enabled) contentColor else disabledContentColor

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .clickable(
                onClick = onClick,
                enabled = enabled
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(LocalContentColor provides itemContentColor) {
            content()
        }
    }
}

private fun getSortText(sortOrder: SortOrder): String {
    return when (sortOrder) {
        SortOrder.NONE -> ""
        SortOrder.TITLE_ASC -> "Title ↑"
        SortOrder.TITLE_DESC -> "Title ↓"

    }
}
