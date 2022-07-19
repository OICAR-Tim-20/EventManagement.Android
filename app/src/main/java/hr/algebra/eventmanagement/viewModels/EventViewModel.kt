package hr.algebra.eventmanagement.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.algebra.eventmanagement.model.Event
import hr.algebra.eventmanagement.repositories.EventRepository
import kotlinx.coroutines.launch

class EventViewModel(id: Int, event: Event?) : ViewModel() {
    private val _event = MutableLiveData<Event>()
    private val errorMessage = MutableLiveData<String?>()
    private val eventRepository = EventRepository()
    val event: LiveData<Event> = _event

    init {
        event?.let(_event::postValue)
        viewModelScope.launch {
            try {
                _event.postValue(eventRepository.getEventById(id))
                errorMessage.postValue(null)
            } catch (e: Throwable) {
                errorMessage.postValue("Could not get Event!")
                Log.e("Error", e.message.orEmpty(), e)
            }
        }
    }
}