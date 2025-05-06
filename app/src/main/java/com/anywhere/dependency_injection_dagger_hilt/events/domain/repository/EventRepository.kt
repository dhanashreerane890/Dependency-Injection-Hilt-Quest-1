package com.anywhere.dependency_injection_dagger_hilt.events.domain.repository

import com.anywhere.dependency_injection_dagger_hilt.events.domain.model.Event

interface EventRepository {
    suspend fun getEventList(): List<Event>
    suspend fun getEventDetailById(id: Int): Event
}