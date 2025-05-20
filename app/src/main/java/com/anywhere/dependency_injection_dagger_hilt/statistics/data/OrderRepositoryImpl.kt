package com.anywhere.dependency_injection_dagger_hilt.statistics.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model.Order
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model.User
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.repository.OrderRepository
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val mockDataSource: MockDataSource
) : OrderRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getOrders(): Flow<List<Order>> = flow {
        emit(mockDataSource.orders)
    }
}

class UserRepositoryImpl @Inject constructor(
    private val mockDataSource: MockDataSource
) : UserRepository {
    override fun getUsers(): Flow<List<User>> = flow {
        emit(mockDataSource.users)
    }
}