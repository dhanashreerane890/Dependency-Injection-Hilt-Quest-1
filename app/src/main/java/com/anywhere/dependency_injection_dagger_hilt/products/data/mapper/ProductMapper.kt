package com.anywhere.dependency_injection_dagger_hilt.products.data.mapper

import com.anywhere.dependency_injection_dagger_hilt.products.data.local.ProductEntity
import com.anywhere.dependency_injection_dagger_hilt.products.data.model.product.ProductDto
import com.anywhere.dependency_injection_dagger_hilt.products.domain.model.Product
import javax.inject.Inject


class ProductDtoMapper @Inject constructor() {
    fun map(dto: ProductDto): Product {
        return Product(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            price = dto.price,
            category = dto.category,
            thumbnail = dto.thumbnail
        )
    }

    fun mapToEntity(dto: ProductDto): ProductEntity {
        return ProductEntity(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            price = dto.price,
            category = dto.category,
            thumbnail = dto.thumbnail
        )
    }
}

