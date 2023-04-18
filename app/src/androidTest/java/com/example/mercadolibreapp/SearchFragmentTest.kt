package com.example.mercadolibreapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.mercadolibreapp.presentation.MainActivity
import com.example.mercadolibreapp.presentation.adapter.ProductAdapter
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class SearchFragmentTest {

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        onView(withId(R.id.etSearch)).perform(typeText("teclado"), pressImeActionButton())
    }

    @Test
    fun should_show_recyclerview_and_hide_progress_and_message() {
        onView(withId(R.id.rvProducts)).check(matches(isDisplayed()))
        onView(withId(R.id.cpiLoading)).check(matches(not(isDisplayed())))
        onView(withId(R.id.cvEmptyState)).check(matches(not(isDisplayed())))
    }

    @Test
    fun should_show_recyclerview_when_press_back_from_details_fragment() {
        onView(withId(R.id.rvProducts)).perform(RecyclerViewActions.actionOnItemAtPosition<ProductAdapter.ViewHolder>(0, click()))
        onView(withId(R.id.tvTitle)).check(matches(withText("Teclado Philips K334 Cable Usb Español Ñ Teclas Redondas Pc")))
        pressBack()
        onView(withId(R.id.rvProducts)).check(matches(isDisplayed()))
        onView(withId(R.id.cpiLoading)).check(matches(not(isDisplayed())))
        onView(withId(R.id.cvEmptyState)).check(matches(not(isDisplayed())))
    }
}