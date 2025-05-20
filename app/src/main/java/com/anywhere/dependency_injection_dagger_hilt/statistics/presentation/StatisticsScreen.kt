package com.anywhere.dependency_injection_dagger_hilt.statistics.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Statistics",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        if (state.isLoading) {
            Text("Loading...")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 1. Total Revenue
                item {
                    Result(
                        title = "Total Revenue",
                        result = "Total: $${state.totalRevenue}"
                    )
                }

                // 2. Most Expensive Item
                item {
                    Result(
                        title = "Most Expensive Item",
                        result = state.mostExpensiveItem?.let { item ->
                            "Product: ${item.productId}, Price: $${item.totalPrice}"
                        } ?: "No items found"
                    )
                }

                // 3. Unique Product IDs
                item {
                    Result(
                        title = "Unique Product IDs",
                        result = "Count: ${state.uniqueProductIds.size}\nProducts: ${state.uniqueProductIds.joinToString(", ")}"
                    )
                }

                // 4. Product Sales Count
                item {
                    Result(
                        title = "Product Sales Count",
                        result = state.productSalesCount.entries.joinToString("\n") { (product, count) ->
                            "$product: $count units"
                        }
                    )
                }

                // 5. User Spending
                item {
                    Result(
                        title = "User Spending",
                        result = state.userSpending.entries.joinToString("\n") { (user, amount) ->
                            "$user: $${amount}"
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Result(
    title: String,
    result: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Text(
            text = result,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
        Divider(modifier = Modifier.padding(top = 8.dp))
    }
}