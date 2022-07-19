package hr.algebra.eventmanagement.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ticket(
    @SerializedName("ticketId") val ticketId: Int,
    @SerializedName("ticketType") val ticketType: Int,
    @SerializedName("price") val price: Int,
    @SerializedName("qrCode") val qrCode: String,
    @SerializedName("ticketOwner") val ticketOwner: TicketOwner,
    @SerializedName("printableToPdf") val printableToPdf: Boolean,
    @SerializedName("eventId") val eventId: Int,
    @SerializedName("contactId") val contactId: Int,
    @SerializedName("purchased") val purchased: Boolean
) : Parcelable