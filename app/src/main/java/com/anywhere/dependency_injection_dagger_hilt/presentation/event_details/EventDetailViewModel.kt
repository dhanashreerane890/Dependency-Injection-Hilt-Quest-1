package com.anywhere.dependency_injection_dagger_hilt.presentation.event_details

import androidx.lifecycle.ViewModel
import com.anywhere.dependency_injection_dagger_hilt.domain.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.anywhere.dependency_injection_dagger_hilt.domain.repository.EventRepository
import kotlinx.coroutines.launch


@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {
    private val _event = MutableStateFlow<Event?>(null)
    val event: StateFlow<Event?> = _event

    fun getEventDetail(id: Int) {
        viewModelScope.launch {
            _event.value = repository.getEventDetailById(id)
        }
    }
}