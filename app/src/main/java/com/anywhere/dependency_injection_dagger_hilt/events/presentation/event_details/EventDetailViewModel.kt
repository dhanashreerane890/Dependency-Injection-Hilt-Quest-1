package com.anywhere.dependency_injection_dagger_hilt.events.presentation.event_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anywhere.dependency_injection_dagger_hilt.events.domain.model.Event
import com.anywhere.dependency_injection_dagger_hilt.events.domain.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


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