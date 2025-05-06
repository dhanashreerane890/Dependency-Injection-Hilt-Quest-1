package com.anywhere.dependency_injection_dagger_hilt.products.di

import android.content.Context
import androidx.room.Room
import com.anywhere.dependency_injection_dagger_hilt.products.data.local.AppDatabase
import com.anywhere.dependency_injection_dagger_hilt.products.data.local.ProductDao
import com.anywhere.dependency_injection_dagger_hilt.products.data.mapper.ProductDtoMapper
//import com.anywhere.dependency_injection_dagger_hilt.data.remote.ApiService
import com.anywhere.dependency_injection_dagger_hilt.products.data.remote.ProductApiService
//import com.anywhere.dependency_injection_dagger_hilt.data.repository.EventRepositoryImpl
import com.anywhere.dependency_injection_dagger_hilt.products.data.repository.ProductRepositoryImpl
import com.anywhere.dependency_injection_dagger_hilt.products.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductModule {

    @Provides
    @Singleton
    fun provideProductApiService(): ProductApiService {
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        apiService: ProductApiService,
        dao: ProductDao,
        dtoMapper: ProductDtoMapper,
    ): ProductRepository {
        return ProductRepositoryImpl(apiService, dao, dtoMapper)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }
}