package com.anywhere.dependency_injection_dagger_hilt.events.di


import com.anywhere.dependency_injection_dagger_hilt.events.data.remote.ApiService
import com.anywhere.dependency_injection_dagger_hilt.events.data.repository.EventRepositoryImpl
import com.anywhere.dependency_injection_dagger_hilt.events.domain.repository.EventRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = "https://jsonplaceholder.typicode.com/"

    @Provides
    @Singleton
    fun provideApiService(baseUrl: String): ApiService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService): EventRepository =
        EventRepositoryImpl(apiService)
}