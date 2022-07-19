package hr.algebra.eventmanagement.helpers

import android.annotation.SuppressLint
import hr.algebra.eventmanagement.viewModels.CommentsViewModel
import hr.algebra.eventmanagement.viewModels.EventViewModel
import hr.algebra.eventmanagement.viewModels.EventsViewModel
import hr.algebra.eventmanagement.viewModels.TicketViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@SuppressLint("VisibleForTests")
val applicationModule = module {
    viewModel { EventsViewModel() }
    viewModel { params -> EventViewModel(id = params.get(), event = params.get()) }
    viewModel { params -> TicketViewModel(eventId = params.get()) }
    viewModel { params -> CommentsViewModel(id = params.get()) }
}