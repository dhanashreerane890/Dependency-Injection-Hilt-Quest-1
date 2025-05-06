package com.anywhere.dependency_injection_dagger_hilt.events.presentation.event_list

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
class EventListingViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    init {
        viewModelScope.launch {
            _events.value = repository.getEventList()
        }
    }
}