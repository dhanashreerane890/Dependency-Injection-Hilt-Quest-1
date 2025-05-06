package com.anywhere.dependency_injection_dagger_hilt.products.domain.repository

import com.anywhere.dependency_injection_dagger_hilt.products.domain.model.Product
import com.anywhere.dependency_injection_dagger_hilt.products.domain.model.SearchParams
import com.anywhere.dependency_injection_dagger_hilt.products.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getCategories(): Flow<List<String>>

    fun searchProducts(searchParams: SearchParams): Flow<Resource<List<Product>>>

    fun refreshProducts(searchParams: SearchParams): Flow<Resource<List<Product>>>

}