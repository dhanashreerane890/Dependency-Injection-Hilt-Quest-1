package com.anywhere.dependency_injection_dagger_hilt.statistics.domain.usecase

import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model.Order
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model.OrderItem
import javax.inject.Inject

class FindMostExpensiveOrderItemUseCase @Inject constructor() {
    operator fun invoke(orderList: List<Order>): OrderItem {
        val orderItems = orderList.flatMap { it.items }

        return orderItems.reduce { maxItem, currentItem ->
            if (currentItem.totalPrice > maxItem.totalPrice) currentItem else maxItem
        }
    }
}
