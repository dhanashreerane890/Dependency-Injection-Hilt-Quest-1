package com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model

import java.math.BigDecimal

data class OrderItem(
    val productId: String,
    val quantity: Int,
    val pricePerUnit: BigDecimal
) {
    val totalPrice: BigDecimal
        get() = pricePerUnit.multiply(BigDecimal(quantity))
}