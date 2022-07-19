package hr.algebra.eventmanagement.repositories

import hr.algebra.eventmanagement.data.EventManagementApi
import hr.algebra.eventmanagement.model.Event

class EventRepository(private val eventManagementApi: EventManagementApi = EventManagementApi.getInstance()) {
    suspend fun getEventById(id: Int): Event = eventManagementApi.getEventById(id)
}