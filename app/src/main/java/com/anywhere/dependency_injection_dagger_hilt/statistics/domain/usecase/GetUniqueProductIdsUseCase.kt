package com.anywhere.dependency_injection_dagger_hilt.statistics.domain.usecase

import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model.Order
import javax.inject.Inject

class GetUniqueProductIdsUseCase @Inject constructor() {
    operator fun invoke(orders: List<Order>): Set<String> {
        return orders
            .flatMap { it.items }
            .map { it.productId }
            .toSet()
    }
}