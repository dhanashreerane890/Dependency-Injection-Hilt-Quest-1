package com.anywhere.dependency_injection_dagger_hilt.statistics.domain.usecase

import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model.Order
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model.User
import java.math.BigDecimal
import javax.inject.Inject

class SummarizeUserSpendingUseCase @Inject constructor() {
    operator fun invoke(userList: List<User>, orderList: List<Order>): Map<String, BigDecimal> {

        val userIdToUser = userList.associateBy { it.userId }

        val userSpending = orderList.fold(mutableMapOf<String, BigDecimal>()) { spendingMap, order ->
            spendingMap.apply {
                val orderTotal = order.items.fold(BigDecimal.ZERO) { total, item ->
                    total + item.totalPrice
                }
                val currentSpending = getOrDefault(order.userId, BigDecimal.ZERO)
                put(order.userId, currentSpending + orderTotal)
            }
        }

        return userSpending.mapKeys { (userId, _) ->
            userIdToUser[userId]?.username ?: ""
        }
    }
}
