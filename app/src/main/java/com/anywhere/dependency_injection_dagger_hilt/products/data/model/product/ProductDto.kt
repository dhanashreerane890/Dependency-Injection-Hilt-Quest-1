package com.anywhere.dependency_injection_dagger_hilt.products.data.model.product

data class ProductDto(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val thumbnail: String
)
