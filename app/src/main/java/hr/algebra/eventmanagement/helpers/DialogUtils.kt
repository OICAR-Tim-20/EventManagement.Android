package hr.algebra.eventmanagement.helpers

import android.app.Activity
import android.app.Dialog
import android.text.TextUtils
import android.view.Window
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import hr.algebra.eventmanagement.R
import hr.algebra.eventmanagement.data.EventManagementApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.time.LocalDateTime

fun showDialog(activity: Activity, eventId: Int) {
    val dialog = Dialog(activity)
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
        if (checkCommentText(comment, activity)) {
            dialog.dismiss()
            authorName = if (name.text.toString() == "") {
                "Anonymous"
            } else {
                name.text.toString()
            }
            val jsonObject = JSONObject()
            jsonObject.put("text", comment.text.toString())
            jsonObject.put("author", authorName)
            jsonObject.put(
                "rating", if (rating.rating == 0.0f) {
                    1
                } else {
                    rating.rating.toInt()
                }
            )
            jsonObject.put("datePosted", LocalDateTime.now())
            jsonObject.put("eventId", eventId)

            val requestBody =
                jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

            CoroutineScope(Dispatchers.IO).launch {
                EventManagementApi.getInstance().insertNewComment(requestBody)
            }
        }
    }
    noBtn.setOnClickListener { dialog.dismiss() }
    dialog.show()
}


private fun checkCommentText(comment: EditText, activity: Activity): Boolean {
    return if (TextUtils.isEmpty(comment.text.toString())) {
        Toast.makeText(activity, "Empty comment is not allowed!", Toast.LENGTH_LONG)
            .show()
        false
    } else {
        true
    }
}

fun termsDialog(activity: Activity, checkBox: CheckBox, materialButton: MaterialButton) {
    val dialog = Dialog(activity)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.custom_terms_and_conditions)
    val btnAgree = dialog.findViewById(R.id.btnIAgree) as MaterialButton
    val btnDisagree = dialog.findViewById(R.id.btnIDisagree) as MaterialButton

    btnAgree.setOnClickListener {
        checkBox.isChecked = true
        materialButton.isEnabled = true
        dialog.dismiss()
    }

    btnDisagree.setOnClickListener {
        dialog.dismiss()
    }
    dialog.show()
}