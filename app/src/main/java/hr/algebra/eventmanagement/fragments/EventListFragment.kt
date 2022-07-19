package hr.algebra.eventmanagement.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.test.espresso.idling.CountingIdlingResource
import hr.algebra.eventmanagement.R
import hr.algebra.eventmanagement.adapters.EventAdapter
import hr.algebra.eventmanagement.databinding.FragmentEventListBinding
import hr.algebra.eventmanagement.helpers.toParcelableDate
import hr.algebra.eventmanagement.viewModels.EventsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class EventListFragment : Fragment(R.layout.fragment_event_list) {
    private val eventsViewModel: EventsViewModel by viewModel()
    private var countingIdlingResource: CountingIdlingResource =
        CountingIdlingResource("DATA_LOADER")
    private lateinit var adapter: EventAdapter
    private lateinit var binding: FragmentEventListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEventListBinding.bind(view)
        bind()
    }

    private fun bind() {
        adapter = EventAdapter(onTap = {
            val directions =
                EventListFragmentDirections.actionEventListFragmentToEventDetailsFragment(it)
            view?.findNavController()?.navigate(directions)
        })

        eventsViewModel.events.observe(viewLifecycleOwner) {
            adapter.eventListData = it
        }

        binding.rvEvents.adapter = adapter

        binding.imageButtonDeleteFilter.setOnClickListener {
            eventsViewModel.events.observe(viewLifecycleOwner) { events ->
                adapter.updateList(events)
            }
            it.visibility = View.GONE
            binding.btnDatePickerDialog.text = getString(R.string.pick_a_date)
        }

        binding.btnDatePickerDialog.setOnClickListener {
            val c = Calendar.getInstance()
            val day = c.get(Calendar.DAY_OF_MONTH)
            val currentMonth = c.get(Calendar.MONTH)
            val currentYear = c.get(Calendar.YEAR)
            DatePickerDialog(
                requireActivity(),
                { _, year, month, dayOfMonth ->
                    binding.imageButtonDeleteFilter.visibility = View.VISIBLE
                    eventsViewModel.events.observe(viewLifecycleOwner) { events ->
                        val filteredEvents = events.filter {
                            LocalDate.parse(
                                String.toParcelableDate(
                                    it.startDate.substring(0, 4),
                                    it.startDate.substring(6, 7),
                                    it.startDate.substring(8, 10)
                                ), DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            ) == LocalDate.parse(
                                String.toParcelableDate(
                                    year.toString(),
                                    (month + 1).toString(),
                                    dayOfMonth.toString()
                                ),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            )
                        }
                        adapter.updateList(filteredEvents)
                    }
                    binding.btnDatePickerDialog.text = "${dayOfMonth}.${month + 1}.${year}"
                },
                currentYear,
                currentMonth,
                day
            ).show()
        }
        Thread.sleep(2000)
    }
}

