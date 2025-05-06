package com.anywhere.dependency_injection_dagger_hilt.products.domain.model

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val thumbnail: String,
)
