package com.anywhere.dependency_injection_dagger_hilt.statistics.presentation

import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model.OrderItem
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model.User
import java.math.BigDecimal

data class StatisticsState(
    val isLoading: Boolean = true,
    val totalRevenue: BigDecimal = BigDecimal.ZERO,
    val mostExpensiveItem: OrderItem? = null,
    val uniqueProductIds: Set<String> = emptySet(),
    val productSalesCount: Map<String, Int> = emptyMap(),
    val userSpending: Map<String, BigDecimal> = emptyMap()
)
