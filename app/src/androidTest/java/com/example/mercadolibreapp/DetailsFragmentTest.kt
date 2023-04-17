package com.example.mercadolibreapp

import android.content.Intent
import android.net.Uri
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.mercadolibreapp.data.models.ResponseDTO.*
import com.example.mercadolibreapp.data.models.ResponseDTO.Product.*
import com.example.mercadolibreapp.presentation.MainActivity
import com.example.mercadolibreapp.presentation.adapter.ProductAdapter
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class DetailsFragmentTest{

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val product = Product(
        id = "MLA1199944090",
        title = "Teclado Bluetooth Satechi Slim St-btsx1m Qwerty Inglés Us Color Gris",
        price = 31999,
        seller = Seller(eshop = Seller.Eshop(nick_name = "ARGENWELL"))
    )

    @Before
    fun setUp() {
        onView(withId(R.id.etSearch)).perform(ViewActions.typeText("teclado"), ViewActions.pressImeActionButton())
        onView(withId(R.id.rvProducts)).perform(RecyclerViewActions.actionOnItemAtPosition<ProductAdapter.ViewHolder>(0, ViewActions.click()))
    }

    @Test
    fun should_show_product_title_and_description() {
        onView(withId(R.id.tvTitle)).check(matches(withText(product.title)))
        onView(withId(R.id.tvNameSeller)).check(matches(withText(product.seller?.eshop?.nick_name)))
    }

    /*@Test
    fun should_open_mercadolibre_app_when_click_buy_button(){
        onView(withId(R.id.btnBuy)).perform(click())
        //intended(hasPackage("com.mercadolibre"))
        Intents.intended(
            allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(Uri.parse("https://www.mercadolibre.com.ar/teclado-bluetooth-satechi-slim-st-btsx1m-qwerty-ingles-us-color-gris/p/MLA18217917")),
                hasPackage("com.mercadolibre")
            )
        )
    }*/
}