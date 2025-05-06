package com.anywhere.dependency_injection_dagger_hilt.products.domain.usecase

import com.anywhere.dependency_injection_dagger_hilt.products.domain.model.Product
import com.anywhere.dependency_injection_dagger_hilt.products.domain.model.SearchParams
import com.anywhere.dependency_injection_dagger_hilt.products.domain.repository.ProductRepository
import com.anywhere.dependency_injection_dagger_hilt.products.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    fun getCategories(): Flow<List<String>> {
        return repository.getCategories()
    }

    operator fun invoke(searchParams: SearchParams): Flow<Resource<List<Product>>> {
        return repository.searchProducts(searchParams)
    }

    fun refresh(searchParams: SearchParams): Flow<Resource<List<Product>>> {
        return repository.refreshProducts(searchParams)
    }

}