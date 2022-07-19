package hr.algebra.eventmanagement.repositories

import hr.algebra.eventmanagement.data.EventManagementApi
import hr.algebra.eventmanagement.model.Ticket

class TicketRepository(private val eventManagementApi: EventManagementApi = EventManagementApi.getInstance()) {
    suspend fun getAllTickets(id: Int): List<Ticket> = eventManagementApi.getAllTickets(id)
}