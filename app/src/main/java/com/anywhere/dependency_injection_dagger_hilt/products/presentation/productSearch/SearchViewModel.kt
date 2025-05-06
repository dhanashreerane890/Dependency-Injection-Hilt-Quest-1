package com.anywhere.dependency_injection_dagger_hilt.products.presentation.productSearch


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anywhere.dependency_injection_dagger_hilt.products.domain.model.SearchParams
import com.anywhere.dependency_injection_dagger_hilt.products.domain.usecase.SearchProductsUseCase
import com.anywhere.dependency_injection_dagger_hilt.products.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _searchParams = MutableStateFlow(SearchParams())
    private val _manualRefresh = MutableSharedFlow<Unit>()

    private val _productsState = MutableStateFlow(ProductsState())
    val productsState: StateFlow<ProductsState> = _productsState.asStateFlow()

    init {
        viewModelScope.launch {
            searchProductsUseCase.getCategories()
                .catch { e ->
                    _productsState.update {
                        it.copy(
                            categories = emptyList(),
                            isLoading = false,
                            error = e.message
                        )
                    }
                }.flowOn(Dispatchers.IO)
                .collect { categories ->
                    _productsState.update { it.copy(categories = categories) }
                }
        }

        val searchFlow = _searchParams
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { params ->
                searchProductsUseCase(params).catch {
                    _productsState.update {
                        it.copy(
                            products = emptyList(),
                            isLoading = false,
                            error = it.error
                        )
                    }
                }.flowOn(Dispatchers.IO)
            }

        val refreshFlow = _manualRefresh
            .flatMapLatest {
                searchProductsUseCase.refresh(_searchParams.value).catch {
                    _productsState.update {
                        it.copy(
                            products = emptyList(),
                            isLoading = false,
                            error = it.error
                        )
                    }
                }.flowOn(Dispatchers.IO)
            }

        viewModelScope.launch {
            merge(searchFlow, refreshFlow)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _productsState.update {
                                it.copy(
                                    products = result.data ?: emptyList(),
                                    isLoading = false,
                                    error = null
                                )
                            }
                        }

                        is Resource.Error -> {
                            _productsState.update {
                                it.copy(
                                    products = result.data ?: emptyList(),
                                    isLoading = false,
                                    error = result.message
                                )
                            }
                        }

                        is Resource.Loading -> {
                            _productsState.update {
                                it.copy(
                                    isLoading = true,
                                    error = null
                                )
                            }
                        }
                    }
                }
        }

        viewModelScope.launch {
            _manualRefresh.emit(Unit)
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.QueryChanged -> {
                _searchParams.update { it.copy(query = event.query) }
                updateState()
            }

            is SearchEvent.CategoryToggled -> {
                _searchParams.update { it.copy(category = event.category) }
                updateState()
            }

            is SearchEvent.PriceRangeChanged -> {
                _searchParams.update { it.copy(minPrice = event.minPrice, maxPrice = event.maxPrice) }
                updateState()
            }

            is SearchEvent.SortOrderChanged -> {
                _searchParams.update { it.copy(sortOrder = event.sortOrder) }
                updateState()
            }

            is SearchEvent.Refresh -> {
                viewModelScope.launch {
                    _manualRefresh.emit(Unit)
                }
            }
        }
    }

    private fun updateState() {
        _productsState.update { it.copy(searchParams = _searchParams.value) }
    }
}
