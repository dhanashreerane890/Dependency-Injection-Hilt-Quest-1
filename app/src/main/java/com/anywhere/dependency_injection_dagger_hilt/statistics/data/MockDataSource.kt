package com.anywhere.dependency_injection_dagger_hilt.statistics.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model.Order
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model.OrderItem
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model.User
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.inject.Inject

class MockDataSource @Inject constructor() {

    val users = listOf(
        User("user1", "Riya"),
        User("user2", "Jiya"),
        User("user3", "Siya"),
        User("user4", "Priya"),
        User("user5", "Miya")
    )

    @RequiresApi(Build.VERSION_CODES.O)
    val orders = listOf(
        Order(
            "order1", "user1", listOf(
                OrderItem("Foundation", 2, BigDecimal("10.5")),
                OrderItem("Lipstick", 6, BigDecimal("5.0"))
            ), LocalDateTime.now().minusDays(5)
        ),

        Order(
            "order2", "user2", listOf(
                OrderItem("Mascara", 1, BigDecimal("10.5")),
                OrderItem("Eyeshadow Palette", 4, BigDecimal("7.25"))
            ), LocalDateTime.now().minusDays(3)
        ),

        Order(
            "order3", "user3", listOf(
                OrderItem("Blush", 2, BigDecimal("5.0")),
                OrderItem("Lip Gloss", 2, BigDecimal("7.25")),
                OrderItem("Bronzer", 1, BigDecimal("50.0")),
                OrderItem("Bronzer", 1, BigDecimal("50.0"))
            ), LocalDateTime.now().minusDays(1)
        ),

        Order(
            "order4", "user4", listOf(
                OrderItem("Highlighter", 7, BigDecimal("10.5")),
                OrderItem("Concealer", 2, BigDecimal("15.0")),
                OrderItem("Concealer", 2, BigDecimal("15.0"))
            ), LocalDateTime.now().minusDays(4)
        ),

        Order(
            "order5", "user5", listOf(
                OrderItem("Makeup Brush Set", 4, BigDecimal("8.5")),
                OrderItem("Makeup Brush Set", 4, BigDecimal("8.5")),
                OrderItem("Compact Powder", 6, BigDecimal("9.0"))
            ), LocalDateTime.now()
        )
    )
}