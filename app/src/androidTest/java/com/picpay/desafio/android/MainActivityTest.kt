package com.picpay.desafio.android

import android.os.SystemClock
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.picpay.desafio.android.BaseUiTest.BaseUITest
import com.picpay.desafio.android.di.generateTestAppComponent
import com.picpay.desafio.android.ui.MainActivity
import com.picpay.desafio.android.utils.RecyclerViewItemCountAssertion
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import java.net.HttpURLConnection

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest : BaseUITest() {

    private lateinit var activityScenario: ActivityScenario<MainActivity>
    @Before
    fun start() {
        super.setUp()
        loadKoinModules(generateTestAppComponent(getMockWebServerUrl()).toMutableList())
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun recyclerViewLoadsList() {
        mockNetworkResponseWithFileContent("success_resp_list.json", HttpURLConnection.HTTP_OK)

        SystemClock.sleep(1000)

        Espresso.onView(withId(R.id.recyclerView))
            .check(
                matches(
                    RecyclerViewItemCountAssertion().recyclerViewItemCountAssertion(45)
                )
            )
    }
}
