package com.anywhere.dependency_injection_dagger_hilt.domain.repository

import com.anywhere.dependency_injection_dagger_hilt.domain.model.Event

interface EventRepository {
    suspend fun getEventList(): List<Event>
    suspend fun getEventDetailById(id: Int): Event
}