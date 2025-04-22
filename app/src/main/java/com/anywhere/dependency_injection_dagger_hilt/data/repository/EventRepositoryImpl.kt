package com.anywhere.dependency_injection_dagger_hilt.data.repository

import com.anywhere.dependency_injection_dagger_hilt.data.remote.ApiService
import com.anywhere.dependency_injection_dagger_hilt.domain.model.Event
import com.anywhere.dependency_injection_dagger_hilt.domain.repository.EventRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : EventRepository {

    override suspend fun getEventList(): List<Event> {
        return apiService.getEventList().map { it.toDomain() }
    }

    override suspend fun getEventDetailById(id: Int): Event {
        return apiService.getEventDetailById(id).toDomain()
    }
}
