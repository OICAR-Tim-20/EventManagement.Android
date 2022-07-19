package hr.algebra.eventmanagement.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.algebra.eventmanagement.model.Event
import hr.algebra.eventmanagement.repositories.EventsRepository
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {
    private val _events = MutableLiveData<List<Event>>()
    private val errorMessage = MutableLiveData<String?>()
    private val eventsRepository = EventsRepository()
    val events: LiveData<List<Event>> = _events

    init {
        viewModelScope.launch {
            try {
                _events.postValue(eventsRepository.getAllEvents())
                errorMessage.postValue(null)
            } catch (e: Throwable) {
                errorMessage.postValue("Could not get Events!")
                Log.e("Error", e.message.orEmpty(), e)
            }
        }
    }
}