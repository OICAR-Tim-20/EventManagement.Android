package hr.algebra.eventmanagement.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.findNavController
import hr.algebra.eventmanagement.R
import hr.algebra.eventmanagement.adapters.EventAdapter
import hr.algebra.eventmanagement.data.JsonHelper
import hr.algebra.eventmanagement.databinding.FragmentEventListBinding

class EventListFragment : Fragment(R.layout.fragment_event_list) {
    private lateinit var adapter: EventAdapter
    private lateinit var binding: FragmentEventListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEventListBinding.bind(view)

        setupUI()
    }

    private fun setupUI() {
        adapter = EventAdapter(onTap = {
            val directions =
                EventListFragmentDirections.actionEventListFragmentToEventDetailsFragment(it)
            view?.findNavController()?.navigate(directions)
        })
        adapter.eventListData = JsonHelper.getEvents(requireContext())

        binding.rvEvents.adapter = adapter
    }


}