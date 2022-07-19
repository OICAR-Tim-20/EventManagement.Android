package hr.algebra.eventmanagement.fragments

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import hr.algebra.eventmanagement.R
import hr.algebra.eventmanagement.data.EventManagementApi
import hr.algebra.eventmanagement.databinding.FragmentCheckoutBinding
import hr.algebra.eventmanagement.helpers.*
import hr.algebra.eventmanagement.model.Ticket
import hr.algebra.eventmanagement.viewModels.TicketViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class CheckoutFragment : Fragment() {

    private val args: CheckoutFragmentArgs by navArgs()
    private val ticketViewModel: TicketViewModel by viewModel { parametersOf(args.event.eventID) }
    private var isEnabled = false
    private lateinit var nonPurchasedTicket: Ticket
    private lateinit var binding: FragmentCheckoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        bind()
    }

    private fun setupUI() {
        binding.tvTicketName.text =
            "${args.numberOfTickets}x ${args.event.title} ${args.event.eventType} - ${args.ticketType}"
        binding.tvTicketCost.text = "${args.numberOfTickets * args.ticketPrice}.00 kn"

        binding.tvTermsAndConditions.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    private fun bind() {
        binding.tvTermsAndConditions.setOnClickListener {
            termsDialog(requireActivity(), binding.cbAgreement, binding.btnFinishTicketPurchase)
            binding.tvTermsAndConditions.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.purple
                )
            )
        }

        ticketViewModel.tickets.observe(viewLifecycleOwner) { it ->
            nonPurchasedTicket = it.first { !it.purchased }
        }
        binding.etEmail.doAfterTextChanged {
            if (isEmailValid(it.toString())) {
                binding.ivCorrectOrWrong.setImageResource(R.drawable.ic_error)
            } else {
                binding.ivCorrectOrWrong.setImageResource(R.drawable.ic_checkmark_circle)
            }
        }

        binding.cbAgreement.setOnClickListener {
            if (!isEnabled) {
                binding.btnFinishTicketPurchase.isEnabled = true
                isEnabled = !isEnabled
            } else {
                binding.btnFinishTicketPurchase.isEnabled = false
                isEnabled = !isEnabled
            }
        }

        binding.etCreditCard.doAfterTextChanged {
            if (it != null) {
                formatCreditCardNumber(it)
            }
            creditCardLogo(it.toString(), binding.ivCardLogo)
        }

        binding.etDateOfExpiration.doAfterTextChanged {
            expirationDateFormatting(it)
        }

        binding.etName.doAfterTextChanged {
            fullNameValidation(it.toString(), binding.ivFullNameCorrectness)
        }

        binding.mtCheckout.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnFinishTicketPurchase.setOnClickListener {
            val jsonObject = JSONObject()

            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

            CoroutineScope(Dispatchers.IO).launch {
                for (ticket in args.ticketList) {
                    EventManagementApi.getInstance()
                        .ticketPurchased(
                            args.event.eventID,
                            TicketType.valueOf(args.ticketType.replace(" ", "")).value,
                            binding.etEmail.text.toString()
                        )
                }
            }
            findNavController().navigateUp()
        }
    }
}


