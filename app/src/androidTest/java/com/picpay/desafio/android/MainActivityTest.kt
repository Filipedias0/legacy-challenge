package com.picpay.desafio.android

import android.os.SystemClock
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.picpay.desafio.android.ui.MainActivity
import com.picpay.desafio.android.utils.MockServerDispatcher
import com.picpay.desafio.android.utils.RecyclerViewItemCountAssertion
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    lateinit var mockWebServer: MockWebServer

    @get: Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MockServerDispatcher().RequestDispatcher()
        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }



    @Test
    fun shouldDisplayTitle() {
        Espresso.onView(withId(R.id.title))
            .check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun recyclerViewLoadsList(){

        SystemClock.sleep(1000)

        Espresso.onView(withId(R.id.recyclerView))
                .check(
                    matches(
                        RecyclerViewItemCountAssertion().recyclerViewItemCountAssertion(50)
                    )
                )
    }
}
