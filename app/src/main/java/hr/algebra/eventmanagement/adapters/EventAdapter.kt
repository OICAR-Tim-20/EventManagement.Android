package hr.algebra.eventmanagement.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hr.algebra.eventmanagement.R
import hr.algebra.eventmanagement.databinding.ItemEventBinding
import hr.algebra.eventmanagement.model.Event

class EventAdapter(private val onTap: (Event) -> Unit) :
    RecyclerView.Adapter<EventAdapter.EventHolder>() {

    var eventListData: List<Event> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.EventHolder {
        val eventView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventHolder(eventView)
    }


    override fun onBindViewHolder(holder: EventAdapter.EventHolder, position: Int) {
        val event = eventListData[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int = eventListData.size

    inner class EventHolder(private val eventView: View) : RecyclerView.ViewHolder(eventView) {
        fun bind(event: Event) {
            this.itemView.setOnClickListener {
                onTap(event)
            }
            val binding = ItemEventBinding.bind(itemView)

            Glide.with(eventView).load(event.picture).centerCrop().into(binding.ivEventImage)
            binding.tvEventName.text = event.title
            if (event.endDate == event.startDate) {
                binding.tvDate.text = event.startDate.substring(0, 10)
            } else {
                binding.tvDate.text =
                    "${event.startDate.substring(0, 10)} - ${event.endDate.substring(0, 10)}"
            }
            binding.tvTicketsAvailable.text = "Tickets available: ${event.ticketsAvailable}"
            binding.tvStartTime.text = "Start time: ${event.startDate.substring(11, 19)}"
            binding.tvLocation.text = "Location: ${event.location.venue}"
        }
    }

    fun updateList(eventList: List<Event>) {
        eventListData = eventList
        notifyDataSetChanged()
    }
}

