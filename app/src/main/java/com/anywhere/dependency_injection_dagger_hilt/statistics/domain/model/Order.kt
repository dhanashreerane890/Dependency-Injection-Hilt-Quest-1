package com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model

import java.time.LocalDateTime

data class Order(
    val orderId: String,
    val userId: String,
    val items: List<OrderItem>,
    val timestamp: LocalDateTime
)