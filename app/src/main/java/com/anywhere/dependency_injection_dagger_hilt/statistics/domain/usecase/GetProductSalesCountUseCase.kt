package com.anywhere.dependency_injection_dagger_hilt.statistics.domain.usecase

import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model.Order
import javax.inject.Inject

class GetProductSalesCountUseCase @Inject constructor() {
    operator fun invoke(orders: List<Order>): Map<String, Int> {
        return orders
            .flatMap { it.items }
            .fold(mutableMapOf()) { countMap, item ->
                countMap.apply {
                    put(item.productId, getOrDefault(item.productId, 0) + item.quantity)
                }
            }
    }
}

