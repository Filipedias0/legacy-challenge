package com.picpay.desafio.android

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.ui.MainActivity
import com.picpay.desafio.android.utils.MockServerDispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BaseFragmentTest {

    lateinit var mockWebServer: MockWebServer

    @get: Rule
    val rule = activityScenarioRule<MainActivity>()

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
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
