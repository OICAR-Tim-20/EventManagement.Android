package hr.algebra.eventmanagement.fragments

import hr.algebra.eventmanagement.model.Comments
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import hr.algebra.eventmanagement.EventManagementActivity
import hr.algebra.eventmanagement.R
import hr.algebra.eventmanagement.adapters.CommentAdapter
import hr.algebra.eventmanagement.adapters.ViewPagerAdapter
import hr.algebra.eventmanagement.databinding.FragmentEventDetailsBinding
import java.time.LocalDate

class EventDetailsFragment : Fragment(R.layout.fragment_event_details) {
    private val args: EventDetailsFragmentArgs by navArgs()
    private val adapter by lazy { CommentAdapter() }
    private val vpAdapter by lazy { ViewPagerAdapter() }
    private val comments by lazy { args.event.comments as MutableList<Comments> }
    private lateinit var binding: FragmentEventDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEventDetailsBinding.bind(view)

        setupUI()
        bind()
    }

    private fun bind() {
        binding.mbtnComment.setOnClickListener {
            showDialog()
        }
    }

    private fun setupUI() {
        adapter.commentList = comments
        val event = args.event


        binding.rvComments.adapter = adapter
        binding.vpEventDetailsImage.adapter = vpAdapter
        binding.diDots.attachTo(binding.vpEventDetailsImage)


        binding.tvEventDetailsName.text = event.title
        binding.tvDateDetails.text =
            "${event.startDate.substring(0, 9)} - ${event.endDate.substring(0, 9)}"
        binding.tvLocationDetails.text = "${event.location.venue}, ${event.location.address.city}"
    }

    private fun showDialog() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)
        var authorName: String?
        val rating = dialog.findViewById(R.id.rbComment) as RatingBar
        val name = dialog.findViewById(R.id.etName) as EditText
        val comment = dialog.findViewById(R.id.etLeaveComment) as EditText
        val yesBtn = dialog.findViewById(R.id.mbtnSend) as MaterialButton
        val noBtn = dialog.findViewById(R.id.mbtnCancel) as MaterialButton
        yesBtn.setOnClickListener {
            if(checkCommentText(comment)){
                dialog.dismiss()
                authorName = if (name.text.toString() == "") {
                    "Anonymous"
                } else {
                    name.text.toString()
                }
                comments.add(
                    Comments(
                        text = comment.text.toString(),
                        author = authorName,
                        rating = rating.rating,
                        datePosted = LocalDate.now().toString(),
                        eventId = args.event.eventID,
                        commentId = comment.id
                    )
                )
                adapter.update(comments)
            }
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun checkCommentText(comment: EditText): Boolean {
        return if (TextUtils.isEmpty(comment.text.toString())) {
            Toast.makeText(requireContext(), "Empty comment is not allowed!", Toast.LENGTH_LONG)
                .show()
            false
        } else {
            true
        }
    }

}
