package com.example.mercadolibreapp.presentation

import android.support.test.espresso.intent.matcher.IntentMatchers.*
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mercadolibreapp.R
import com.example.mercadolibreapp.data.models.ResponseDTO.*
import com.example.mercadolibreapp.data.models.ResponseDTO.Product.*
import com.example.mercadolibreapp.data.repository.FakeRepository
import com.example.mercadolibreapp.data.repository.FakeRepository.Companion.TECLADO
import com.example.mercadolibreapp.presentation.adapter.ProductAdapter
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*

@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest {

    private val repository = FakeRepository()

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        onView(withId(R.id.etSearch)).perform(ViewActions.typeText(TECLADO), ViewActions.pressImeActionButton())
        onView(withId(R.id.rvProducts)).perform(RecyclerViewActions.scrollToPosition<ProductAdapter.ViewHolder>(0))
        onView(withId(R.id.rvProducts)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }

    @Test
    fun should_show_product_title_and_description() {
        onView(withId(R.id.tvTitle)).check(matches(withText(repository.productDetails.title)))
        onView(withId(R.id.tvDescription)).check(matches(withText(repository.productDetails.description?.text)))
    }

    @Ignore("Me da el siguiente error: Error performing 'single click - At Coordinates: 539, 1329 and precision: 16, 16'")
    @Test
    fun should_open_mercadolibre_app_when_click_buy_button() {
        onView(withId(R.id.btnBuy))
            .check(matches(isDisplayed()))
            .perform(scrollTo(), click())
        //intended(hasPackage("com.mercadolibre"))
        /*intended(
            allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(Uri.parse("https://www.mercadolibre.com.ar/teclado-bluetooth-satechi-slim-st-btsx1m-qwerty-ingles-us-color-gris/p/MLA18217917")),
                hasPackage("com.mercadolibre")
            )
        )*/
    }
}