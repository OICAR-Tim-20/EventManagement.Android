package hr.algebra.eventmanagement.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hr.algebra.eventmanagement.R
import hr.algebra.eventmanagement.databinding.ItemViewPagerBinding

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.ItemViewHolder>() {

    private var data: List<String> = listOf("https://media.istockphoto.com/photos/people-with-their-arms-in-air-at-music-festival-picture-id494896581",
    "https://media.istockphoto.com/photos/are-you-ready-to-party-picture-id1181169462",
    "https://media.istockphoto.com/photos/happy-people-dance-in-nightclub-party-concert-picture-id1201075450")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_view_pager, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemViewPagerBinding.bind(view)

        fun bind (url: String) {
            Glide.with(view).load(url).centerCrop().into(binding.ivPictures)
        }
    }
}
