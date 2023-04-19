package com.example.mercadolibreapp.presentation

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mercadolibreapp.R
import com.example.mercadolibreapp.data.models.ResponseDTO.*
import com.example.mercadolibreapp.data.repository.FakeRepository
import com.example.mercadolibreapp.data.repository.FakeRepository.Companion.TECLADO
import com.example.mercadolibreapp.helpers.RecyclerViewItemCountAssertion
import com.example.mercadolibreapp.presentation.adapter.ProductAdapter
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {

    private val repository = FakeRepository()

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        onView(withId(R.id.etSearch)).perform(typeText(TECLADO), pressImeActionButton())
    }

    @Test
    fun should_show_recyclerview_and_hide_progress_and_message() {
        onView(withId(R.id.rvProducts)).check(matches(isDisplayed()))
        onView(withId(R.id.cpiLoading)).check(matches(not(isDisplayed())))
        onView(withId(R.id.cvEmptyState)).check(matches(not(isDisplayed())))
    }

    @Test
    fun should_recyclerview_has_four_elements() {
        onView(withId(R.id.rvProducts)).check(matches(isDisplayed()))
        onView(withId(R.id.rvProducts)).perform(scrollToPosition<RecyclerView.ViewHolder>(3))
        onView(withId(R.id.rvProducts)).check(RecyclerViewItemCountAssertion(4))
    }

    @Test
    fun should_show_recyclerview_when_press_back_from_details_fragment() {
        onView(withId(R.id.rvProducts)).check(matches(isDisplayed()))
        onView(withId(R.id.rvProducts)).perform(scrollToPosition<ProductAdapter.ViewHolder>(0))
        onView(withId(R.id.rvProducts)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        pressBack()
        onView(withId(R.id.rvProducts)).check(matches(isDisplayed()))
        onView(withId(R.id.cpiLoading)).check(matches(not(isDisplayed())))
        onView(withId(R.id.cvEmptyState)).check(matches(not(isDisplayed())))
    }

    @Test
    fun should_verify_that_second_element_is_correct(){
        onView(withId(R.id.rvProducts)).check(matches(isDisplayed()))
        onView(withId(R.id.rvProducts)).perform(scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.rvProducts)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tvTitle)).check(matches(withText(repository.product1.title)))
    }
}