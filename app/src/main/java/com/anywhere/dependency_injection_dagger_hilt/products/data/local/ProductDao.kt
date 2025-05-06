package com.anywhere.dependency_injection_dagger_hilt.products.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products WHERE (title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%') AND (:category IS NULL OR category = :category) AND (:minPrice IS NULL OR price >= :minPrice) AND (:maxPrice IS NULL OR price <= :maxPrice)")
    fun searchAndFilterProducts(query: String, category: String?, minPrice: Double?, maxPrice: Double?): Flow<List<ProductEntity>>

    @Query("SELECT DISTINCT category FROM products")
    fun getCategories(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProducts(products: List<ProductEntity>)

}