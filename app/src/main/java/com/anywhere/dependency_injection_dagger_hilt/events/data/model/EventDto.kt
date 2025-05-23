package com.anywhere.dependency_injection_dagger_hilt.events.data.model

import com.anywhere.dependency_injection_dagger_hilt.events.domain.model.Event

data class EventDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
) {
     fun toDomain(): Event = Event(id, userId, title, body)
}
