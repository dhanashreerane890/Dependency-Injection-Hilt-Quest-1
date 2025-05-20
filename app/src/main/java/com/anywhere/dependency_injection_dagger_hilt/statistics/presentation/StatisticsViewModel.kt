package com.anywhere.dependency_injection_dagger_hilt.statistics.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model.Order
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.model.User
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.repository.OrderRepository
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.repository.UserRepository
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.usecase.CalculateTotalRevenueUseCase
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.usecase.FindMostExpensiveOrderItemUseCase
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.usecase.GetProductSalesCountUseCase
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.usecase.GetUniqueProductIdsUseCase
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.usecase.SummarizeUserSpendingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
    private val calculateTotalRevenueUseCase: CalculateTotalRevenueUseCase,
    private val findMostExpensiveOrderItemUseCase: FindMostExpensiveOrderItemUseCase,
    private val getUniqueProductIdsUseCase: GetUniqueProductIdsUseCase,
    private val getProductSalesCountUseCase: GetProductSalesCountUseCase,
    private val summarizeUserSpendingUseCase: SummarizeUserSpendingUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(StatisticsState())
    val state: StateFlow<StatisticsState> = _state.asStateFlow()

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    private val _users = MutableStateFlow<List<User>>(emptyList())

    init {
        getOrders()
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            userRepository.getUsers().collect { users ->
                _users.value = users
                updateStatistics()
            }
        }
    }

    private fun getOrders() {
        viewModelScope.launch {
            orderRepository.getOrders().collect { orders ->
                _orders.value = orders
                updateStatistics()
            }
        }
    }


    private  fun updateStatistics() {
        val orders = _orders.value
        val users = _users.value

        if (orders.isEmpty() || users.isEmpty()) return

        _state.value = StatisticsState(
            isLoading = false,
            totalRevenue = calculateTotalRevenueUseCase(orders),
            mostExpensiveItem = findMostExpensiveOrderItemUseCase(orders),
            uniqueProductIds = getUniqueProductIdsUseCase(orders),
            productSalesCount = getProductSalesCountUseCase(orders),
            userSpending = summarizeUserSpendingUseCase(users, orders)
        )
    }

}