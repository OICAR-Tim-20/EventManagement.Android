package hr.algebra.eventmanagement.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.eventmanagement.R
import hr.algebra.eventmanagement.databinding.ItemEventCommentBinding
import hr.algebra.eventmanagement.model.Comments

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentHolder>() {

    var commentList: List<Comments> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val commentView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_event_comment, parent, false)
        return CommentHolder(commentView)
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        val comment = commentList[position]
        holder.bind(comment)
    }

    fun update(comments: List<Comments>) {
        commentList = comments
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = commentList.size

    class CommentHolder(commentView: View) : RecyclerView.ViewHolder(commentView) {
        fun bind(comment: Comments) {
            val binding = ItemEventCommentBinding.bind(itemView)

            binding.tvDateComment.text = comment.datePosted.substring(0, 9)
            binding.tvComment.text = comment.text
            binding.tvAnonymous.text = comment.author
            binding.rbRating.rating = comment.rating
            binding.tvRatingNumbers.text = "${comment.rating}/5.0"
        }

    }

}