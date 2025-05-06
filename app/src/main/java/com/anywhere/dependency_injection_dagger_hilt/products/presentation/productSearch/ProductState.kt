package com.anywhere.dependency_injection_dagger_hilt.products.presentation.productSearch

import com.anywhere.dependency_injection_dagger_hilt.products.domain.model.Product
import com.anywhere.dependency_injection_dagger_hilt.products.domain.model.SearchParams
import com.anywhere.dependency_injection_dagger_hilt.products.domain.model.SortOrder


data class ProductsState(
    val products: List<Product> = emptyList(),
    val searchParams: SearchParams = SearchParams(),
    val categories: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class SearchEvent {
    data class QueryChanged(val query: String) : SearchEvent()
    data class CategoryToggled(val category: String?) : SearchEvent()
    data class PriceRangeChanged(val minPrice: Double?, val maxPrice: Double?) : SearchEvent()
    data class SortOrderChanged(val sortOrder: SortOrder) : SearchEvent()
    data object Refresh : SearchEvent()
}
