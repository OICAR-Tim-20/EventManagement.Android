package hr.algebra.eventmanagement.repositories

import hr.algebra.eventmanagement.data.EventManagementApi

class CommentsRepository(private val eventManagementApi: EventManagementApi = EventManagementApi.getInstance()) {
    suspend fun getCommentsOfEvent(id: Int) = eventManagementApi.getComments(id)
}