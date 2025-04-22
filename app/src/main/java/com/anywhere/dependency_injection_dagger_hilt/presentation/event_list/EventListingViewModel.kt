package com.anywhere.dependency_injection_dagger_hilt.presentation.event_list

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