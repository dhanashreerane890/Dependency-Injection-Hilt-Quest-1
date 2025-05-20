package com.anywhere.dependency_injection_dagger_hilt.statistics.domain.usecase

import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model.Order
import java.math.BigDecimal
import javax.inject.Inject

class CalculateTotalRevenueUseCase @Inject constructor() {
    operator fun invoke(orders: List<Order>): BigDecimal {
        return orders.fold(BigDecimal.ZERO) { totalRevenue, order ->
            val orderTotal = order.items.fold(BigDecimal.ZERO) { sum, item ->
                sum + item.totalPrice
            }
            totalRevenue + orderTotal
        }
    }
}
