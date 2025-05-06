package com.anywhere.dependency_injection_dagger_hilt.products.data.repository

import android.util.Log
import com.anywhere.dependency_injection_dagger_hilt.products.data.local.ProductDao
import com.anywhere.dependency_injection_dagger_hilt.products.data.mapper.ProductDtoMapper
import com.anywhere.dependency_injection_dagger_hilt.products.data.remote.ProductApiService
import com.anywhere.dependency_injection_dagger_hilt.products.domain.model.Product
import com.anywhere.dependency_injection_dagger_hilt.products.domain.model.SearchParams
import com.anywhere.dependency_injection_dagger_hilt.products.domain.model.SortOrder
import com.anywhere.dependency_injection_dagger_hilt.products.domain.repository.ProductRepository
import com.anywhere.dependency_injection_dagger_hilt.products.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApiService: ProductApiService,
    private val productDao: ProductDao,
    private val dtoMapper: ProductDtoMapper,
) : ProductRepository {

    override fun getCategories(): Flow<List<String>> {
        return productDao.getCategories()
    }

    override fun searchProducts(searchParams: SearchParams): Flow<Resource<List<Product>>> {
        return flow {

            emit(Resource.Loading())

            try {
                val remoteProducts = if (searchParams.category != null) {
                    productApiService.getProductsByCategory(searchParams.category)
                } else {
                    productApiService.searchProducts(searchParams.query)
                }

                val productEntities = remoteProducts.products.map { product ->
                    dtoMapper.mapToEntity(product)
                }

                productDao.addProducts(productEntities)

                val products = remoteProducts.products.map { product ->
                    dtoMapper.map(product)
                }

                val sortedProducts = when (searchParams.sortOrder) {
                    SortOrder.TITLE_ASC -> products.sortedBy { it.title }
                    SortOrder.TITLE_DESC -> products.sortedByDescending { it.title }
                    SortOrder.NONE -> products
                }

                emit(Resource.Success(sortedProducts))

            } catch (e: Exception) {
                Log.e("TAG", "searchProducts error: ${e.message} ")
            }
        }
    }

    override fun refreshProducts(searchParams: SearchParams): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())

        try {
            val remoteProducts = if (searchParams.category != null) {
                productApiService.getProductsByCategory(searchParams.category)
            } else {
                productApiService.searchProducts(searchParams.query)
            }
            val productEntities = remoteProducts.products.map { product ->
                dtoMapper.mapToEntity(product)
            }
            productDao.addProducts(productEntities)

            val products = remoteProducts.products.map { product ->
                dtoMapper.map(product)
            }

            val sortedProducts = when (searchParams.sortOrder) {
                SortOrder.TITLE_ASC -> products.sortedBy { it.title }
                SortOrder.TITLE_DESC -> products.sortedByDescending { it.title }
                SortOrder.NONE -> products

            }
            emit(Resource.Success(sortedProducts))
        } catch (e: Exception) {
            emit(Resource.Error("Please Check your internet connection."))
        }
    }

}
