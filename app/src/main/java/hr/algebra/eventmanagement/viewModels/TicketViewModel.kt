package hr.algebra.eventmanagement.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.algebra.eventmanagement.model.Ticket
import hr.algebra.eventmanagement.repositories.TicketRepository
import kotlinx.coroutines.launch

class TicketViewModel(eventId: Int) : ViewModel() {
    private val _tickets = MutableLiveData<List<Ticket>>()
    private val errorMessage = MutableLiveData<String?>()
    private val ticketRepository = TicketRepository()
    val tickets: LiveData<List<Ticket>> = _tickets

    init {
        viewModelScope.launch {
            try {
                _tickets.postValue(ticketRepository.getAllTickets(eventId))
                errorMessage.postValue(null)
            } catch (e: Throwable) {
                errorMessage.postValue("Could not get Tickets!")
                Log.e("Error", e.message.orEmpty(), e)
            }
        }
    }
}