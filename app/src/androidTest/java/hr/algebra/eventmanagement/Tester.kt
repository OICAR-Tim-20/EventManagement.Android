package hr.algebra.eventmanagement

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import hr.algebra.eventmanagement.adapters.EventAdapter
import hr.algebra.eventmanagement.helpers.BetterScrollTo
import hr.algebra.eventmanagement.helpers.SetRating
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class EspressoAutomationTests {
    @get:Rule
    val activityRule = ActivityScenarioRule(EventManagementActivity::class.java)

    @Test
    fun leavingAComment() {
        Espresso.onView(withId(R.id.rvEvents)).perform(
            RecyclerViewActions.actionOnItemAtPosition<EventAdapter.EventHolder>(
                0,
                click()
            )
        )
        Espresso.onView(withId(R.id.mbtnComment)).perform(click())
        Espresso.onView(withId(R.id.etName)).perform(click())
            .perform(replaceText("Mario"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.etLeaveComment)).perform(click()).perform(
            replaceText("Jako Zabavno"), closeSoftKeyboard()
        )
        Espresso.onView(withId(R.id.rbComment)).perform(SetRating())
        //Espresso.onView(withId(R.id.mbtnSend)).perform(click())
    }


    @Test
    fun ticketBuying() {
        Espresso.onView(withId(R.id.rvEvents)).perform(
            RecyclerViewActions.actionOnItemAtPosition<EventAdapter.EventHolder>(
                0,
                click()
            )
        )
        Espresso.onView(withId(R.id.btnBuyTicket)).perform(click())
        Espresso.onView(withId(R.id.etName))
            .perform(replaceText("Ivo Petricevic"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.etEmail))
            .perform(replaceText("petricevic@gmail.com"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.etCreditCard))
            .perform(replaceText("4574111100009999"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.etDateOfExpiration))
            .perform(replaceText("0425"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.etCCV)).perform(replaceText("000"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.etCardHolderName))
            .perform(replaceText("Ivo"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.etCardHolderSurname))
            .perform(replaceText("Petricevic"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.etCardHolderAddress))
            .perform(BetterScrollTo(), replaceText("Trg Bana Josipa Jelacica"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.etCardHolderZipCode))
            .perform(BetterScrollTo(), replaceText("10000"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.etCardHolderCity))
            .perform(BetterScrollTo(), replaceText("Zagreb"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.etCardHolderCountry))
            .perform(BetterScrollTo(), replaceText("Hrvatska"), closeSoftKeyboard())
    }
}