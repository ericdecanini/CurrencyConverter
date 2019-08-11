package com.ericdecanini.currencyconverter

import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.TypeTextAction
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.ericdecanini.currencyconverter.mvp.activitiy.CurrenciesActivity
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.CoreMatchers.not

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CurrenciesInstrumentedTest {
    @Rule
    @JvmField var activityRule: ActivityTestRule<CurrenciesActivity> = ActivityTestRule(CurrenciesActivity::class.java)

    private fun getActivity() = activityRule.activity

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.ericdecanini.currencyconverter", appContext.packageName)
    }

    @Test
    fun testRecyclerViewIsHidden_whenProgressBarIsVisible() {
        // This is a very simple test
        // And quite frankly, all I currently know of Espresso
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerview_currencies)).check(matches(not(isDisplayed())))
    }

}
