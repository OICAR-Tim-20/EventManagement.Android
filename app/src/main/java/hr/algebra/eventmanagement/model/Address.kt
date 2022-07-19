package hr.algebra.eventmanagement.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(

	@SerializedName("addressId") val addressId: Int,
	@SerializedName("city") val city: String,
	@SerializedName("zipCode") val zipCode: Int,
	@SerializedName("street") val street: String,
	@SerializedName("houseNumber") val houseNumber: String
): Parcelable