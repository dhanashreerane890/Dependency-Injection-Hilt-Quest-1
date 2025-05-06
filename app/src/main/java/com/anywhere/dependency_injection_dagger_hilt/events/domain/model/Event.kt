package com.anywhere.dependency_injection_dagger_hilt.events.domain.model

data class Event(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)