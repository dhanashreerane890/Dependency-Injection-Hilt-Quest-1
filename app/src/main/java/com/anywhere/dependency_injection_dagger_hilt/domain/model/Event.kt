package com.anywhere.dependency_injection_dagger_hilt.domain.model

data class Event(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)