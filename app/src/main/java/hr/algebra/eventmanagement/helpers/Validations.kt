package hr.algebra.eventmanagement.helpers

import android.text.Editable
import android.text.TextUtils
import android.widget.ImageView
import androidx.core.util.PatternsCompat.EMAIL_ADDRESS
import hr.algebra.eventmanagement.R
import java.util.regex.Pattern

const val SPACE = ' '

fun fullNameValidation(fullName: String, correctness: ImageView) {
    if (Pattern.matches(
            "([A-Z][a-z]{2,} )([A-Z][a-z]{2,} )?([A-Z][a-z]{2,})",
            fullName
        )
    ) {
        correctness.setImageResource(R.drawable.ic_checkmark_circle)
    } else {
        correctness.setImageResource(R.drawable.ic_error)
    }
}

fun expirationDateFormatting(editable: Editable?) {
    if (editable != null) {
        if (editable.isNotEmpty() && editable.length % 3 == 0) {
            val c: Char = editable[editable.length - 1]
            if ('/' == c) {
                editable.delete(editable.length - 1, editable.length)
            }
        }
    }
    if (editable != null) {
        if (editable.isNotEmpty() && editable.length % 3 == 0) {
            val c: Char = editable[editable.length - 1]
            if (Character.isDigit(c) && TextUtils.split(
                    editable.toString(),
                    "/"
                ).size <= 2
            ) {
                editable.insert(editable.length - 1, "/")
            }
        }
    }
}

fun creditCardLogo(text: String, logo: ImageView) {
    when (checkCardScheme(text)) {
        CreditCardUtils.CardScheme.UNKNOWN -> logo.setImageResource(R.drawable.ic_question_mark)
        CreditCardUtils.CardScheme.JCB -> logo.setImageResource(R.drawable.ic_pic_jcbcard)
        CreditCardUtils.CardScheme.AMEX -> logo.setImageResource(R.drawable.ic_american_express)
        CreditCardUtils.CardScheme.DINERS_CLUB -> logo.setImageResource(R.drawable.ic_diners_club_international_logo)
        CreditCardUtils.CardScheme.VISA -> logo.setImageResource(R.drawable.ic_visa)
        CreditCardUtils.CardScheme.MASTERCARD -> logo.setImageResource(R.drawable.ic_mastercard_logo_svg)
        CreditCardUtils.CardScheme.DISCOVER -> logo.setImageResource(R.drawable.ic_discover_logo)
        CreditCardUtils.CardScheme.MAESTRO -> logo.setImageResource(R.drawable.ic_maestro_logo)
    }
}

private fun checkCardScheme(target: CharSequence): CreditCardUtils.CardScheme {
    return CreditCardUtils.identifyCardScheme(target.toString())
}

fun isEmailValid(target: CharSequence): Boolean {
    return if (TextUtils.isEmpty(target)) {
        false
    } else {
        EMAIL_ADDRESS.matcher(target).matches()
    }
}

fun formatCreditCardNumber(editable: Editable) {
    if (editable.isNotEmpty() && editable.length % 5 == 0) {
        val c: Char = editable[editable.length - 1]
        if (SPACE == c) {
            editable.delete(editable.length - 1, editable.length)
        }
    }
    // Insert char where needed.
    if (editable.isNotEmpty() && editable.length % 5 == 0) {
        val c: Char = editable[editable.length - 1]
        // Only if its a digit where there should be a space we insert a space
        if (Character.isDigit(c) && TextUtils.split(
                editable.toString(),
                java.lang.String.valueOf(SPACE)
            ).size <= 3
        ) {
            editable.insert(editable.length - 1, java.lang.String.valueOf(" "))
        }
    }
}