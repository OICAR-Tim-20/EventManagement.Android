package hr.algebra.eventmanagement.repositories

import hr.algebra.eventmanagement.data.EventManagementApi
import hr.algebra.eventmanagement.model.Event

class EventsRepository(private val eventManagementApi: EventManagementApi = EventManagementApi.getInstance()) {
    suspend fun getAllEvents(): List<Event> = eventManagementApi.getAllEvents()
}