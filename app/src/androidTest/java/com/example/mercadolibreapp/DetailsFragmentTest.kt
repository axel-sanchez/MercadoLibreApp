package com.example.mercadolibreapp

import android.content.Intent
import android.net.Uri
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.mercadolibreapp.data.models.Description
import com.example.mercadolibreapp.data.models.ProductDetails
import com.example.mercadolibreapp.data.models.ResponseDTO.*
import com.example.mercadolibreapp.data.models.ResponseDTO.Product.*
import com.example.mercadolibreapp.data.repository.ProductRepositoryImpl
import com.example.mercadolibreapp.data.source.ProductLocalSource
import com.example.mercadolibreapp.data.source.ProductRemoteSource
import com.example.mercadolibreapp.domain.repository.ProductRepository
import com.example.mercadolibreapp.helpers.Either
import com.example.mercadolibreapp.presentation.DetailsFragment
import com.example.mercadolibreapp.presentation.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.Mockito

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class DetailsFragmentTest {

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var scenario: FragmentScenario<DetailsFragment>

    private val product = ProductDetails(
        id = "MLA1199944090",
        title = "Teclado Bluetooth Satechi Slim St-btsx1m Qwerty Inglés Us Color Gris",
        price = 31999,
        description = Description(1,
            "Este teclado Satechi es el mejor complemento para hacer todo tipo de actividades. Es cómodo y práctico al momento de redactar documentos, navegar y hacer búsquedas por internet, ya sea en tu trabajo o en la comodidad del hogar.\n" +
                    "\n" +
                    "Distinción a todo color\n" +
                    "Su retroiluminación le da un toque diferente a tu equipo y resalta su composición cuando es utilizado en espacios poco iluminados.")
    )

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(bundleOf("idProduct" to "MLA1199944090"))
    }

    @Test
    fun should_show_product_title_and_description() {
        onView(withId(R.id.tvTitle)).check(matches(withText(product.title)))
        onView(withId(R.id.tvDescription)).check(matches(withText(product.description?.text)))
    }

    @Test
    fun should_show_product_title_and_description_when_fragment_is_recreate() {
        scenario.recreate()
        onView(withId(R.id.tvTitle)).check(matches(withText(product.title)))
        onView(withId(R.id.tvDescription)).check(matches(withText(product.description?.text)))
    }

    @Ignore("Me da el siguiente error: No instrumentation registered! Must run under a registering instrumentation.")
    @Test
    fun should_open_mercadolibre_app_when_click_buy_button(){
        onView(withId(R.id.btnBuy)).perform(scrollTo(), click())
        intended(hasPackage("com.mercadolibre"))
        /*intended(
            allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(Uri.parse("https://www.mercadolibre.com.ar/teclado-bluetooth-satechi-slim-st-btsx1m-qwerty-ingles-us-color-gris/p/MLA18217917")),
                hasPackage("com.mercadolibre")
            )
        )*/
    }
}