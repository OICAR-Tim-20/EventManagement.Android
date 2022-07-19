package hr.algebra.eventmanagement.helpers

import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ScrollToAction
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.anyOf
import org.hamcrest.Matcher

class BetterScrollTo(
    private val original: ScrollToAction = ScrollToAction()
) : ViewAction by original {

    override fun getConstraints(): Matcher<View> = anyOf(
        allOf(
            withEffectiveVisibility(Visibility.VISIBLE),
            isDescendantOfA(isAssignableFrom(NestedScrollView::class.java))
        ),
        original.constraints
    )
}