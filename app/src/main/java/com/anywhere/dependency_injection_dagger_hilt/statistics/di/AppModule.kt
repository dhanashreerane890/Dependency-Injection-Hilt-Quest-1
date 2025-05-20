package com.anywhere.dependency_injection_dagger_hilt.statistics.di

import com.anywhere.dependency_injection_dagger_hilt.statistics.data.MockDataSource
import com.anywhere.dependency_injection_dagger_hilt.statistics.data.OrderRepositoryImpl
import com.anywhere.dependency_injection_dagger_hilt.statistics.data.UserRepositoryImpl
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.repository.OrderRepository
import com.anywhere.dependency_injection_dagger_hilt.statistics.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMockDataSource(): MockDataSource {
        return MockDataSource()
    }

    @Provides
    @Singleton
    fun provideOrderRepository(mockDataSource: MockDataSource): OrderRepository {
        return OrderRepositoryImpl(mockDataSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(mockDataSource: MockDataSource): UserRepository {
        return UserRepositoryImpl(mockDataSource)
    }
}