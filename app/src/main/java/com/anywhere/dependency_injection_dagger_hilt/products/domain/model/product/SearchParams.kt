package com.anywhere.dependency_injection_dagger_hilt.products.domain.model.product


data class SearchParams(
    val query: String = "",
    val category: String? = null,
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val sortOrder: SortOrder = SortOrder.NONE
)

enum class SortOrder {
    NONE,
    TITLE_ASC,
    TITLE_DESC
}