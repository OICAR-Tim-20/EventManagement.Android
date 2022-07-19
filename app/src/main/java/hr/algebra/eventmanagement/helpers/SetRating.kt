package hr.algebra.eventmanagement.helpers

import android.view.View
import android.widget.RatingBar
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher


class SetRating : ViewAction {
    override fun getConstraints(): Matcher<View> {
        return ViewMatchers.isAssignableFrom(RatingBar::class.java)
    }

    override fun getDescription(): String {
        return "Custom view action to set rating."
    }

    override fun perform(uiController: UiController?, view: View) {
        val ratingBar = view as RatingBar
        ratingBar.rating = 4f
    }
}