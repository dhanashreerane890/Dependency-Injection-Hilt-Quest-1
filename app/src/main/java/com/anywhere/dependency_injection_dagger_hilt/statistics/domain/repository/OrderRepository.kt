package com.anywhere.dependency_injection_dagger_hilt.statistics.domain.repository

import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model.Order
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model.User
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrders(): Flow<List<Order>>
}

interface UserRepository {
    fun getUsers(): Flow<List<User>>
}