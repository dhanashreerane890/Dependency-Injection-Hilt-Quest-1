package com.anywhere.dependency_injection_dagger_hilt.products.data.remote

import com.anywhere.dependency_injection_dagger_hilt.products.data.model.ProductResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiService {

    @GET("products/search")
    suspend fun searchProducts(
        @Query("q") query: String
    ): ProductResponseDto

    @GET("products/category")
    suspend fun getProductsByCategory(
        @Query("category") category: String
    ): ProductResponseDto
}