package com.anywhere.dependency_injection_dagger_hilt.data.remote

import com.anywhere.dependency_injection_dagger_hilt.data.model.EventDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts")
    suspend fun getEventList(): List<EventDto>

    @GET("posts/{id}")
    suspend fun getEventDetailById(@Path("id") id: Int): EventDto
}