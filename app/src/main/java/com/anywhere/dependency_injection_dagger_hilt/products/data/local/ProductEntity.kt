package com.anywhere.dependency_injection_dagger_hilt.products.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val thumbnail: String,
    val category: String,
    val timestamp: Long = System.currentTimeMillis()
)