package hr.algebra.eventmanagement.fragments

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import hr.algebra.eventmanagement.R
import hr.algebra.eventmanagement.adapters.CommentAdapter
import hr.algebra.eventmanagement.adapters.ViewPagerAdapter
import hr.algebra.eventmanagement.databinding.FragmentEventDetailsBinding
import hr.algebra.eventmanagement.helpers.TicketType
import hr.algebra.eventmanagement.helpers.showDialog
import hr.algebra.eventmanagement.model.Ticket
import hr.algebra.eventmanagement.viewModels.CommentsViewModel
import hr.algebra.eventmanagement.viewModels.EventViewModel
import hr.algebra.eventmanagement.viewModels.TicketViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EventDetailsFragment : Fragment(R.layout.fragment_event_details) {
    private val args: EventDetailsFragmentArgs by navArgs()
    private val adapter by lazy { CommentAdapter() }
    private val vpAdapter by lazy { ViewPagerAdapter() }
    private val commentsViewModel: CommentsViewModel by viewModel { parametersOf(args.event.eventID) }
    private val eventViewModel: EventViewModel by viewModel {
        parametersOf(
            args.event.eventID,
            args.event
        )
    }
    private val ticketViewModel: TicketViewModel by viewModel { parametersOf(args.event.eventID) }
    private var chosenTickets: MutableList<Ticket> = emptyList<Ticket>().toMutableList()
    private lateinit var binding: FragmentEventDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEventDetailsBinding.bind(view)

        setupUI()
        bind()
    }

    private fun setupUI() {
        commentsViewModel.comments.observe(viewLifecycleOwner) { adapter.commentList = it }
        binding.rvComments.adapter = adapter
        binding.vpEventDetailsImage.adapter = vpAdapter
        binding.diDots.attachTo(binding.vpEventDetailsImage)


        eventViewModel.event.observe(viewLifecycleOwner) {
            binding.tvEventDetailsName.text = it.title
            binding.tvDateDetails.text =
                "${it.startDate.substring(0, 10)} - ${it.endDate.substring(0, 10)}"
            binding.tvLocationDetails.text =
                "${it.location.venue}, ${it.location.address.city}"
        }
    }

    private fun bind() {
        binding.mbtnComment.setOnClickListener {
            showDialog(requireActivity(), args.event.eventID)
            parentFragmentManager.beginTransaction().detach(this).attach(this).commit()
        }

        setArrayAdapter(
            requireActivity(),
            R.layout.item_spinner,
            R.array.ticketTypeArray,
            binding.spinnerTicketType
        )

        setArrayAdapter(
            requireActivity(),
            R.layout.item_spinner_number_of_tickets,
            R.array.ticketNumberArray,
            binding.spinnerNumberOfTickets
        )

        binding.btnBuyTicket.setOnClickListener {
            ticketViewModel.tickets.observe(viewLifecycleOwner) { tickets ->
                if (tickets.none {
                        it.ticketType == TicketType.valueOf(
                            binding.spinnerTicketType.selectedItem.toString().replace(" ", "")
                        ).value
                                && !it.purchased
                    }) {
                    Toast.makeText(
                        requireContext(),
                        "Sorry we are out of those type of tickets!",
                        Toast.LENGTH_LONG
                    )
                } else {
                    chosenTickets = tickets.filter {
                        it.ticketType == TicketType.valueOf(
                            binding.spinnerTicketType.selectedItem.toString().replace(" ", "")
                        ).value
                                && !it.purchased
                    } as MutableList<Ticket>
                    val toBeBoughtTickets: List<Ticket> = chosenTickets.subList(
                        0,
                        binding.spinnerNumberOfTickets.selectedItem.toString().toInt()
                    )
                    val directions =
                        EventDetailsFragmentDirections.actionEventDetailsFragmentToCheckoutFragment(
                            args.event,
                            toBeBoughtTickets.size,
                            binding.spinnerTicketType.selectedItem.toString(),
                            chosenTickets.first().price,
                            toBeBoughtTickets.toTypedArray()
                        )
                    findNavController().navigate(directions)
                }
            }
        }
    }

    private fun setArrayAdapter(activity: Activity, itemLayout: Int, array: Int, spinner: Spinner) {
        ArrayAdapter.createFromResource(
            activity,
            array,
            itemLayout
        ).also {
            it.setDropDownViewResource(itemLayout)
            spinner.adapter = it
        }
    }

}
